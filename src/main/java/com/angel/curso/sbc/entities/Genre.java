package com.angel.curso.sbc.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "genres", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "genre")
    private Set<MovieGenre> movies;

    @Column(nullable = false)
    private String name;

    public Genre() {

    }

    public Genre(String name) {
        this.name = name;
    }

    public Set<MovieGenre> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieGenre> movies) {
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
