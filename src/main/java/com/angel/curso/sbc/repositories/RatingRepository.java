package com.angel.curso.sbc.repositories;

import com.angel.curso.sbc.entities.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    @Query("select c From Rating c left join c.user lef join c.movie where c.user.id = :idUser and  c.movie.id = :idMovie")
    Optional<Rating> findByIdUserAndIdMovie(Long idUser, Long idMovie);
}
