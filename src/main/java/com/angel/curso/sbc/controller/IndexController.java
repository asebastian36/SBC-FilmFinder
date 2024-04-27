package com.angel.curso.sbc.controller;

import com.angel.curso.sbc.entities.*;
import com.angel.curso.sbc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/filmfinder/v1")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/loged/{id}")
    public String loged(@PathVariable String id, Model model) {
        Long userId = Long.parseLong(id);
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            //  obtenemos el catalogo completo
            List<Movie> complete = (List<Movie>) movieService.findAll().stream().toList();

            //  enviamos al usuario a la vista
            model.addAttribute("user", user.get());

            if (user.get().getRatings() != null) {
                //  ahora se obtienen las recomendaciones
                List<Movie> movies = userService.findRecomendations(user.get());
                model.addAttribute("movies", movies);
            } else {
                //  sino tiene recomendaciones se mandan todas las peliculas
                model.addAttribute("movies", complete);
            }

            return "index";
        }

        return "login";
    }

    @GetMapping(value = "/")
    public String index() {
        return "login";
    }

    @PostMapping(value = "/save")
    public String newRegister(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println(username);
        System.out.println(password);
        userService.save(username, password);

        if(userService.findByName(username).isPresent()) model.addAttribute(userService.findByName(username).get());

        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userService.findByName(username);
        if(user.isPresent() && user.get().getPassword().equals(password)) {
            model.addAttribute("user", user.get());

            if (user.get().getRatings() != null) {
                List<Movie> movies = userService.findRecomendations(user.get());
                model.addAttribute("movies", movies);
            } else {
                model.addAttribute("movies", movieService.findAll());
            }

            return "index";
        }

        return "login";
    }

    @GetMapping("/id/{idUser}/movie/{movieId}")
    public String findById(@PathVariable String idUser, @PathVariable String movieId, Model model) {
        //  metodo para mandar la informacion completa de una pelicula y sus generos
        Long userId = Long.parseLong(idUser);
        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {

            Long idMovie = Long.parseLong(movieId);
            Optional<Movie> movie = movieService.findByIdWithGenres(idMovie);
            Optional<Rating> optionalRating = ratingService.findByIdUserAndIdMovie(user.get().getId(), movie.get().getId());

            model.addAttribute("user", user.get());

            //  se envia la resena si hay
            optionalRating.ifPresent(rating -> model.addAttribute("rating", rating));
            if (optionalRating.isPresent() && !optionalRating.isEmpty()) model.addAttribute("media", movieService.getMediaRatings(idMovie));

            //  sino hay resena
            model.addAttribute("movie", movie.get());

            return "play";
        }

        return "login";
    }

    @GetMapping("/reviewed/{id}")
    public String reviewed(@PathVariable String id, Model model) {
        Long idUser = Long.parseLong(id);
        Optional<User> user = userService.findById(idUser);

        user.ifPresent(u -> {
            model.addAttribute("user", u);

            if (u.getRatings() != null) {
                //  mandar solo las peliculas rankeadas
                List<Movie> movies = userService.findAllMovies(u.getId());
                model.addAttribute("movies", movies);
            } else {
                //  sino tiene peliculas rankeadas mandamos una lista vacia
                model.addAttribute("movies", new ArrayList<Movie>());
            }
        });

        return "reviewed";
    }

    @GetMapping("/id/{id}/explore")
    public String explore(@PathVariable String id, Model model) {
        //  mandar lista de listas con la clasificacion de peliculas por genero
        Long idUser = Long.parseLong(id);
        Optional<User> user = userService.findById(idUser);

        if (user.isPresent()) {
            model.addAttribute("listMovies", genreService.MoviesByGenre());
            model.addAttribute("user", user.get());
            model.addAttribute("genres", genreService.findAll());
            return "explore";
        }

        return "login";
    }

    @PostMapping("/search/id/{idUser}")
    public String search(@PathVariable String idUser, @RequestParam String search, Model model) {
        Long idU = Long.parseLong(idUser);
        Optional<User> user = userService.findById(idU);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());

            //  empieza la busqueda
            List<Movie> list = movieService.findByNameContains(search);
            model.addAttribute("movies", list);
            model.addAttribute("user", user.get());
            return "search";
        }

        return "login";
    }

    @GetMapping("/edit/id/{idUser}/movie/{idMovie}")
    public String edit(@PathVariable String idUser, @PathVariable String idMovie, Model model) {
        System.out.println(idUser);
        Long idU = Long.parseLong(idUser);
        Long idM = Long.parseLong(idMovie);

        Optional<User> user = userService.findById(idU);
        Optional<Movie> movie = movieService.findById(idM);

        if (user.isPresent() && movie.isPresent()) {
            Optional<Rating> optionalRating = ratingService.findByIdUserAndIdMovie(idU, idM);

            optionalRating.ifPresent(r -> {
                model.addAttribute("user", user.get());
                model.addAttribute("rating", r);
                model.addAttribute("movie", movie.get());
            });

            return "edit";
        }

        return "login";
    }

    @GetMapping("/new/id/{idUser}/movie/{idMovie}")
    public String newRating(@PathVariable String idUser, @PathVariable String idMovie, Model model) {
        Long idU = Long.parseLong(idUser);
        Long idM = Long.parseLong(idMovie);

        Optional<User> user = userService.findById(idU);
        Optional<Movie> movie = movieService.findById(idM);

        if (user.isPresent() && movie.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("movie", movie.get());
            return "new";
        }

        return "login";
    }

    @PostMapping("/rating/newRatingSave/id/{idUser}/movie/{idMovie}")
    public String saveNewRating(@PathVariable String idUser, @PathVariable String idMovie, @RequestParam("rating") String rating, Model model) {
        Long idU = Long.parseLong(idUser);
        long idM = Long.parseLong(idMovie);
        Double score = Double.parseDouble(rating);

        Optional<User> user = userService.findById(idU);
        Optional<Movie> movie = movieService.findById(idM);

        if (user.isPresent() && movie.isPresent()) {
            Rating newRating = new Rating();
            newRating.setMovie(movie.get());
            newRating.setUser(user.get());
            newRating.setRating(score);
            ratingService.save(newRating);
            return "redirect:/filmfinder/v1/id/" + idU.toString() + "/movie/" + idMovie;
        }

        return "login";
    }

    @PostMapping("/rating/save/id/{idUser}/movie/{idMovie}")
    public String saveRating(@PathVariable String idUser, @PathVariable String idMovie, @RequestParam("rating") String rating) {
        System.out.println(rating);
        Long idM = Long.parseLong(idMovie);
        Long idU = Long.parseLong(idUser);
        Double r = Double.parseDouble(rating);
        Optional<User> optionalUser = userService.findById(idU);

        if (optionalUser.isPresent()) {
            Double score = Double.parseDouble(rating);
            Optional<Rating> optionalRating = ratingService.findByIdUserAndIdMovie(optionalUser.get().getId(), movieService.findById(idM).get().getId());

            if (optionalRating.isPresent()) {
                optionalRating.get().setRating(r);
                ratingService.save(optionalRating.get());
            }
            return "redirect:/filmfinder/v1/id/" + idU.toString() + "/movie/" + idMovie;
        } else {
            return "login";
        }
    }
}