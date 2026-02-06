package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies" , description = "Movie management API")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @Operation(summary = "Get All Movies")
       @GetMapping("/all")
 public List<Movie> getALL(){
       return movieService.getAllMovie();
     }
        @GetMapping("/{id}")
      public Movie getMovie(@PathVariable Integer id){
        return movieService.getMovie(id);
        }
        @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie){
        return movieService.addMovie(movie);
        }
        @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable Integer id, @RequestBody Movie updatedMovie){
        return movieService.updateMovie(id, updatedMovie);
        }
        @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Integer id){
        movieService.deleteMovie(id);
        }
        //FIND METHODS (QUERY)
    @GetMapping("/title/{title}")
    public List<Movie> getBYTitle(@PathVariable String title){
        return movieService.getAllMovieByTitle(title);
    }

    @GetMapping("/releaseDate/{releaseDate}")
    public List<Movie> getBYReleaseDate(@PathVariable String releaseDate){
        return movieService.getAllMovieByReleaseDate(releaseDate);
    }
    @GetMapping("/genre/{genre}")
    public List<Movie> getByGenre(@PathVariable String genre){
        return movieService.getAllMovieByGenre(genre);
    }
    @GetMapping("/pagination")
    public Page<Movie> getMoviesByPage(@RequestParam(defaultValue ="0" ) int page, @RequestParam(defaultValue = "5") int size){
        return movieService.getMovieByPage(page, size);
    }
}
