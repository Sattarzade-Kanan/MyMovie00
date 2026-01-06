package com.example.demo.repository;

import com.example.demo.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "SELECT * FROM movie WHERE LOWER(title) LIKE LOWER(CONCAT('%':title '%' ) ) ",nativeQuery = true)
     List<Movie> findByTitle(String title);
    @Query(value = "SELECT * FROM movie WHERE LOWER(releaseDate) LIKE LOWER(CONCAT('%':releaseDate '%' ) ) ",nativeQuery = true)
     List<Movie> findByReleaseDate(String releaseDate);
    @Query(value = "SELECT * FROM movie WHERE LOWER(genre) LIKE LOWER(CONCAT('%':genre '%' ) ) ",nativeQuery = true)
    List<Movie> findByGenre(String genre);
List<Movie> findByTitleContainingIgnoreCase(String title);
    //Pagination
        Page<Movie> findAll(Pageable pageable);
}
