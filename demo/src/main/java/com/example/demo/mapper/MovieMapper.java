package com.example.demo.mapper;


import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Director;
import com.example.demo.entity.Movie;
import org.springframework.stereotype.Component;
@Component//Помогает указать какой класс является бином(обьектом)
public class MovieMapper {//MovieMapper-Помогает общаться с MovieForm и MovieDTO
    //list.html
    public MovieDTO toDTO(Movie movie){
    MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setDuration(movie.getDuration());
        dto.setReleaseDate(movie.getReleaseDate());

        if (movie.getDirector() != null){
            dto.setDirectorId(movie.getDirector().getId());
            dto.setDirectorName(movie.getDirector().getName());
        }
        return dto;
    }
    //frt 7812
//edit and new
    public MovieForm toForm(Movie movie){
        MovieForm form = new MovieForm();
        form.setId(movie.getId());
        form.setTitle(movie.getTitle());
        form.setGenre(movie.getGenre());
        form.setDuration(movie.getDuration());
        form.setReleaseDate(movie.getReleaseDate());

        if (movie.getDirector() != null){
            form.setDirectorId(movie.getDirector().getId());
        }
        return form;
    }
//New and Edit Form ->Entity
    public void updatedEntityForm(MovieForm form, Movie movie, Director director){
        movie.setTitle(form.getTitle());
        movie.setGenre(form.getGenre());
        movie.setDuration(form.getDuration());
        movie.setReleaseDate(form.getReleaseDate());
        movie.setDirector(director);
    }
}
