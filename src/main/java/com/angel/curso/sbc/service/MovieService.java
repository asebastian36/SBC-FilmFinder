package com.angel.curso.sbc.service;

import com.angel.curso.sbc.entities.Movie;
import com.angel.curso.sbc.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Set<Movie> findAll() {
        return movieRepository.findAllWithSet();
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findByIdWithRatings(Long id) {
        return movieRepository.findByIdWithRatings(id);
    }

    public Optional<Movie> findByIdWithGenres(Long id) {
        return movieRepository.findByIdWithGenres(id);
    }

    public Optional<Movie> finOneAll(Long id) {
        return movieRepository.findOneAll(id);
    }

    public List<Movie> findByNameContains(String name) {
        return movieRepository.findByNameContains(name);
    }

    public Optional<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }


    public Double getMediaRatings(Long id) {
        Double mediaRatings = 0.0;

        Optional<Movie> optionalMovie = findByIdWithGenres(id);

        if (optionalMovie.isPresent() && optionalMovie.get().getRatings().size() > 0) {
            AtomicReference<Double> sumRatings = new AtomicReference<>(0.0);
            optionalMovie.get().getRatings().forEach(rating -> {
                sumRatings.updateAndGet(v -> v + rating.getRating());
            });

            mediaRatings = sumRatings.get() / optionalMovie.get().getRatings().size();
        }

        return mediaRatings;
    }
}
