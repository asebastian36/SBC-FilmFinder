package com.angel.curso.sbc.service;

import com.angel.curso.sbc.entities.MovieGenre;
import com.angel.curso.sbc.repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

    public List<MovieGenre> findAll() {
        return (List<MovieGenre>) movieGenreRepository.findAll().stream().toList();
    }

    public Optional<MovieGenre> findById(Long id) {
        return movieGenreRepository.findById(id);
    }

    public List<MovieGenre> findByGenre(String genre) {
        return (List<MovieGenre>) movieGenreRepository.findByGenre(genre).stream().toList();
    }

    public List<MovieGenre> findIdGenre(Long id) {
        return (List<MovieGenre>) movieGenreRepository.findIdGenre(id).stream().toList();
    }

    public void save(MovieGenre movieGenre) {
        movieGenreRepository.save(movieGenre);
    }

    public void delete(MovieGenre movieGenre) {
        movieGenreRepository.delete(movieGenre);
    }

    public void deleteById(Long id) {
        movieGenreRepository.deleteById(id);
    }

    public List<MovieGenre> findAllWithMovieAndGenre() {
        return (List<MovieGenre>) movieGenreRepository.findAllWithMovieAndGenre().stream().toList();
    }

    List<MovieGenre> findAllWithMovieAndGenreByNameGenre(String name) {
        return (List<MovieGenre>) movieGenreRepository.findAllWithMovieAndGenreByNameGenre(name).stream().toList();
    }

    List<MovieGenre> findAllWithMovieAndGenreByIdGenre(Long id) {
        return (List<MovieGenre>) movieGenreRepository.findAllWithMovieAndGenreByIdGenre(id).stream().toList();
    }
}
