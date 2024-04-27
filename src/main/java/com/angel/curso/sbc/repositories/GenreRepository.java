package com.angel.curso.sbc.repositories;

import com.angel.curso.sbc.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    @Query("select c from Genre c left join fetch c.movies where c.id = :id")
    Optional<Genre> findByIdWithMovies(Long id);

    List<Genre> findAll();
    Optional<Genre> findByName(String name);
}
