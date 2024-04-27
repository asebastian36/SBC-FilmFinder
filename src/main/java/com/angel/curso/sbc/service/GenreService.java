package com.angel.curso.sbc.service;

import com.angel.curso.sbc.entities.Genre;
import com.angel.curso.sbc.entities.Movie;
import com.angel.curso.sbc.entities.MovieGenre;
import com.angel.curso.sbc.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieGenreService movieGenreService;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    public Optional<Genre> findByIdWithMovies(Long id) {
        return genreRepository.findByIdWithMovies(id);
    }

    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }

    public List< List<Movie> > MoviesByGenre() {
        List< List<Movie> > result = new ArrayList<>();

        //  se obtienen la lista de generos
        List<Genre> genres = genreRepository.findAll();

        //  se obtienen las peliculas que tiene ese genero
        for (Genre genre : genres) {
            List<MovieGenre> results = movieGenreService.findByGenre(genre.getName());
            List<Movie> movies = new ArrayList<>();

            //  poblamos la lista de peliculas de ese genero
            for (MovieGenre movieGenre : results) movies.add(movieGenre.getMovie());

            //  guardamos esa lista de peliculas en la lista de listas
            result.add(movies);
        }

        return result;
    }
}
