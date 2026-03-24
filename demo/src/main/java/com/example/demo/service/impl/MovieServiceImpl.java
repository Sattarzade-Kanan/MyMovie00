package com.example.demo.service.impl;

import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Director;
import com.example.demo.entity.Movie;
import com.example.demo.exception.DirectorNotFoundException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.DirectorRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.interfaces.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorRepository directorRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;

        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> getAllMovie(){
        return movieRepository.findAll();
    }

    @Override
    public Director getDirector(Integer id)  {
        return directorRepository.findById(id).orElseThrow(() -> new DirectorNotFoundException("Error"));
    }


    @Override
    public void saveForm(MovieForm movieForm) {
        Movie movie;
        if (movieForm.getId() == null){
            movie = new Movie();
        }else {
            movie = getMovie(movieForm.getId());
        }
        Director director = getDirector(movieForm.getDirectorId());
        movieMapper.updatedEntityForm(movieForm, movie,director);
        movieRepository.save(movie);
    }

    @Override
    public Movie getMovie(Integer id) {
        return movieRepository.findById(id).orElseThrow( () -> new MovieNotFoundException("Error"));
    }

    @Override
    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Integer id, Movie updatedMovie){
        return movieRepository.findById(id).map(exsisting ->{
            exsisting.setTitle(updatedMovie.getTitle());
            exsisting.setReleaseDate(updatedMovie.getReleaseDate());
            exsisting.setDuration(updatedMovie.getDuration());
            exsisting.setGenre(updatedMovie.getGenre());
            exsisting.setDirector(updatedMovie.getDirector());
            return movieRepository.save(exsisting);
        }).orElseThrow( ()-> new MovieNotFoundException("Error") );

    }
    @Override
    public void deleteMovie(Integer id){
        if (!movieRepository.existsById(id)){
            throw new MovieNotFoundException(
                    "No such a movie with id" + ":" + id
            );
        }
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> getAllMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public List<Movie> getAllMovieByReleaseDate(String releaseDate){
        return movieRepository.findByReleaseDate(releaseDate);
    }

    @Override
    public List<Movie> getAllMovieByGenre(String genre){
        return movieRepository.findByGenre(genre);
    }

    @Override
    public Page<Movie> getMovieByPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(pageable);
    }

    @Override
    public List<Movie> search(String title, String genre, Sort sort){
        if (title !=null &&  !title.isBlank()){
            return movieRepository.findByTitleContainingIgnoreCase(title);
        }

        if (genre !=null &&  !genre.isBlank()){
            return movieRepository.findByGenreContainingIgnoreCase(genre);
        }

        return movieRepository.findAll(sort);
    }

    @Override
    public Page<MovieDTO> searchPaginated(String title, String genre, int page, int size, Sort sort){
        Pageable pageable = PageRequest.of(page,size,sort);
        String safeTitle = (title == null) ? "" : title;
        String safeGenre = (genre == null) ? "" : genre;

        Page<Movie>  moviePage  =
                movieRepository.findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase
                        (safeTitle,safeGenre,pageable);
        return moviePage.map(movieMapper::toDTO);
    }
}
