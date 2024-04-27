package com.angel.curso.sbc.service;

import com.angel.curso.sbc.entities.Rating;
import com.angel.curso.sbc.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public Set<Rating> findAll() {
        return (Set<Rating>) ratingRepository.findAll();
    }

    public Optional<Rating> findById(Long id) {
        return ratingRepository.findById(id);
    }

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    public void deleteById(Long id) {
        ratingRepository.deleteById(id);
    }

    public Optional<Rating> findByIdUserAndIdMovie(Long idUser, Long idMovie) {
        return ratingRepository.findByIdUserAndIdMovie(idUser, idMovie);
    }
}
