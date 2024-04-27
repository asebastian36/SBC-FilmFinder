package com.angel.curso.sbc.repositories;

import com.angel.curso.sbc.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Query("select c from Movie c left join fetch c.ratings where c.id = :id")
    Optional<Movie> findByIdWithRatings(Long id);

    @Query("select c from Movie c left join fetch c.genres where c.id = :id")
    Optional<Movie> findByIdWithGenres(Long id);

    @Query("select c from Movie c left join fetch c.genres left join fetch c.ratings where c.id = :id")
    Optional<Movie> findOneAll(Long id);

    Optional<Movie> findByName(String name);

    @Query("select c from Movie c where lower(c.name) like lower(concat('%', :name, '%'))")
    List<Movie> findByNameContains(String name);

    @Query("select c from Movie c")
    Set<Movie> findAllWithSet();
}
