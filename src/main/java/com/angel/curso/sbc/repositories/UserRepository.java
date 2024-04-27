package com.angel.curso.sbc.repositories;

import com.angel.curso.sbc.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();

    Optional<User> findByUsername(String username);

    @Query("select c from User c left join fetch c.ratings where c.id = :id")
    Optional<User> findByIdWithRating(Long id);
}
