package com.angel.curso.sbc.repositories;

import com.angel.curso.sbc.entities.MovieGenre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface MovieGenreRepository extends CrudRepository<MovieGenre, Long> {
    Set<MovieGenre> findAll();
    @Query("select c From MovieGenre c left join fetch c.genre  where c.genre.name = :genre")
    Set<MovieGenre> findByGenre(String genre);

    @Query("select c From MovieGenre c left join fetch c.genre  where c.genre.id = :id")
    Set<MovieGenre> findIdGenre(Long id);

    @Query("select c from MovieGenre c left join fetch c.genre left join fetch c.movie")
    Set<MovieGenre> findAllWithMovieAndGenre();

    @Query("select c from MovieGenre c left join fetch c.genre left join fetch c.movie where c.genre.id = :id")
    Set<MovieGenre> findAllWithMovieAndGenreByIdGenre(Long id);

    @Query("select c from MovieGenre c left join fetch c.genre left join fetch c.movie where c.genre.name = :name")
    Set<MovieGenre> findAllWithMovieAndGenreByNameGenre(String name);
}
