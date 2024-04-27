package com.angel.curso.sbc.service;

import com.angel.curso.sbc.entities.*;
import com.angel.curso.sbc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieService movieService;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        users = new ArrayList<>(userRepository.findAll().stream().toList());
        return users;
    }

    public Optional<User> findByName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByIdWithRatings(Long id) {
        return userRepository.findByIdWithRating(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void save(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    //  obtener las peliculas vistas por el usuario
    public List<Movie> findAllMovies(Long id) {
        List<Movie> movies = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            optionalUser.get().getRatings().forEach(rating -> {
                movies.add(rating.getMovie());
            });
        }

        return movies;
    }

    //  obtener las opciones de recomendacion osea sin ver
    public List<Movie> findPosibleRecomendations(List<Movie> allMovies, Long id) {
        List<Movie> movies = new ArrayList<>();

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Movie> view = findAllMovies(user.get().getId());

            // Obtener las películas no vistas
            Set<Movie> filter = new HashSet<>(allMovies);
            filter.removeAll(view);
            movies = new ArrayList<>(filter);
        }

        return movies;
    }

    //  obtener puntuaciones de cada genero
    private Map<String, Double> scoresGenres(User user) {
        Map<String, Double> scores = new HashMap<>();

        // puntuar generos segun las peliculas
        user.getRatings().forEach(rating -> {
           Double calification = rating.getRating();

           rating.getMovie().getGenres().forEach(movieGenre -> {
               String genreActually = movieGenre.getGenre().getName();

               if (scores.containsKey(genreActually)) {
                   Double calificationActually = scores.get(movieGenre.getGenre().getName());
                   calificationActually += calification;
                   scores.put(movieGenre.getGenre().getName(), calificationActually);
               } else {
                   scores.put(genreActually, calification);
               }
           });
        });

        scores.forEach((genre, score) -> {
            //  normalizando valores
            Double sc = 100d;
            score = scores.get(genre) / 100;
            scores.put(genre, score);
        });

        scores.forEach( (genre, score) -> {
            System.out.println(genre + " " + score);
        });

        return scores;
    }

    //  obtener puntuaciones de peliculas para recomendarlas
    private Map<String, Double> scoresMovies(List<Movie> movies, Map<String, Double> scores) {
        Double score = 0d;

        Map<String, Double> rM = new HashMap<>();

        for(Movie movie : movies) {
            for (MovieGenre genre : movie.getGenres()) {
                String genreName = genre.getGenre().getName();
                if (scores.containsKey(genreName)) {
                    Double calification = scores.get(genreName);
                    score += calification;
                }
            }

            rM.put(movie.getName(), score);

            score = 0d;
        }

        rM.forEach( (name, sc) -> {
            System.out.println(name + " " + sc);
        });

        return rM;
    }

    public List<Movie> findRecomendations(User user) {
        //  aqui se guardara el resultado final
        List<Movie> result = new ArrayList<>();

        List<Movie> allMovies = (List<Movie>) movieService.findAll().stream().toList();

        //  separamos las peliculas sin ver de la lista
        List<Movie> posibleRecomendations = this.findPosibleRecomendations(allMovies, user.getId());

        //  ponderamos los generos de las peliculas vistas
        Map<String, Double> scoresGenres = this.scoresGenres(user);

        //  obtenemos las ponderaciones de las peliculas segun las ponderaciones de los generos
        Map<String, Double> preview = this.scoresMovies(posibleRecomendations, scoresGenres);

        //  lista para ordenar map
        LinkedHashMap<String, Double> sortedPreview = new LinkedHashMap<>();

        //  ordenar los resultados del mejor al peor rankeado
        preview.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(entry -> {
                    sortedPreview.put(entry.getKey(), entry.getValue());
                });

        sortedPreview.forEach( (name, score) -> {
            System.out.println(name + " " + score);

            //  agregamos las peliculas ya ordenadas a la lista resultado
            Optional<Movie> optionalMovie = movieService.findByName(name);
            optionalMovie.ifPresent(result::add);
        });

        return result;
    }
}
