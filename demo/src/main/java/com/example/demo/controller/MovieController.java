package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.interfaces.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movie Management -> REST CONTROLLER")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

        @GetMapping
        @Operation(summary = "Get All Movies")
        public List<Movie> getAllMovies(){
           return movieService.getAllMovie();
        }


       @GetMapping("/{id}")
       @Operation(summary = "Get Movie")
       public Movie getMovie(@PathVariable Integer id){
          return movieService.getMovie(id);
       }


       @PostMapping
       @Operation(summary = "Add Movie")
       public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
       }
}
