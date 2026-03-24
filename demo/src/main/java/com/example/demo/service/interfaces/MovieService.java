package com.example.demo.service.interfaces;

import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Director;
import com.example.demo.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface MovieService {

    List<Movie> getAllMovie();
    Director getDirector(Integer id);


    void saveForm(MovieForm movieForm);
    Movie getMovie(Integer id);


    Movie addMovie( Movie movie);
    Movie updateMovie( Integer id, Movie updatedMovie);


    void deleteMovie(Integer id);
    //FIND METHODS
    List<Movie> getAllMovieByTitle(String title);
    List<Movie> getAllMovieByReleaseDate(String releaseDate);


    List<Movie> getAllMovieByGenre(String genre);
    Page<Movie> getMovieByPage( int page,  int size);


    List<Movie> search(String title,  String genre,Sort sort);
    Page<MovieDTO> searchPaginated(String title, String genre, int page, int size, Sort sort);
}
