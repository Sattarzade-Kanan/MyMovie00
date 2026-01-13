package com.example.demo.service;

import com.example.demo.entity.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

public List<Movie> getAllMovie(){
        return movieRepository.findAll();
    }
  public Movie getMovie(@PathVariable Integer id){
        return movieRepository.findById(id).orElseThrow( () -> new RuntimeException("Error"));
    }
    public Movie addMovie(@RequestBody Movie movie){
        return movieRepository.save(movie);
    }
    public Movie updateMovie(@PathVariable Integer id, @RequestBody Movie updatedMovie){
        return movieRepository.findById(id).map(exsisting ->{
            exsisting.setTitle(updatedMovie.getTitle());
            exsisting.setReleaseDate(updatedMovie.getReleaseDate());
            exsisting.setDuration(updatedMovie.getDuration());
            exsisting.setGenre(updatedMovie.getGenre());
            return movieRepository.save(updatedMovie);
        }).orElseThrow( ()-> new RuntimeException("Error") );


    }
    public String deleteMovie(@PathVariable Integer id){
        if (movieRepository.existsById(id)){
             movieRepository.deleteById(id);
             return "Movie deleted!";
        }
        return "Error";
    }
    //FIND METHODS
      public List<Movie> getAllMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }
    public List<Movie> getAllMovieByReleaseDate(String releaseDate){
        return movieRepository.findByReleaseDate(releaseDate);
    }
    public List<Movie> getAllMovieByGenre(String genre){
        return movieRepository.findByGenre(genre);
    }
    public Page<Movie> getMovieByPage(@PathVariable int page, @PathVariable int size){
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(pageable);
    }

    public List<Movie> search(@PathVariable String title, @PathVariable String genre){
        if (title !=null &&  !title.isBlank()){
            return movieRepository.findByTitleContainingIgnoreCase(title);
        }

        if (genre !=null &&  !genre.isBlank()){
            return movieRepository.findByGenreContainingIgnoreCase(genre);
        }

        return movieRepository.findAll();
    }
}
