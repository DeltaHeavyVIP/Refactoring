package com.example.basic.repositories;

import com.example.basic.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepo extends JpaRepository<Genre, String> {

    Optional<Genre> findByGenre(String genre);

}
