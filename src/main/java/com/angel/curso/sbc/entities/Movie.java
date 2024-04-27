package com.angel.curso.sbc.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "movies", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private Set<Rating> ratings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private Set<MovieGenre> genres;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long year;

    @Column(nullable = false)
    private Long runtime;

    @Column(nullable = false)
    private String trailer;



    public Movie() {
        this.genres = new HashSet<>();
        this.ratings = new HashSet<>();
    }

    public Movie(String name, String image, String poster, String description, Long year, Long runtime, String trailer) {
        this();
        this.name = name;
        this.image = image;
        this.poster = poster;
        this.description = description;
        this.year = year;
        this.runtime = runtime;
        this.trailer = trailer;
    }

    public void setGenres(Set<MovieGenre> genres) {
        this.genres = genres;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MovieGenre> getGenres() {
        return genres;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
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
