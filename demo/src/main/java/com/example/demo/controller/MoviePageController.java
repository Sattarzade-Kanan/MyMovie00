package com.example.demo.controller;

import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviePageController {
    private final MovieService movieService;

    public MoviePageController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String title,
                       @RequestParam(required = false) String genre,@RequestParam(defaultValue = "title") String sortBy,@RequestParam(defaultValue = "asc")String direction,
                       Model model){

        Sort sort = direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<Movie> movies = movieService.getAllMovie();
        model.addAttribute("movies" ,movieService.search(title, genre, sort));
        model.addAttribute("title" ,title);
        model.addAttribute("genre" ,genre);
model.addAttribute("sortBy" , sortBy);
model.addAttribute("direction" , direction);

        return "movies/list";
    }
    @GetMapping("/new")
       public String form(Model model){
    model.addAttribute("movieForm" , new MovieForm());
    return "movies/new";
    }
    @PostMapping
      public String save(@Valid @ModelAttribute("movieForm") MovieForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return form.getId() == null ?"movies/new" : "movies/edit";
        }

        Movie movie;
        if (form.getId() == null){
         movie = new Movie();
        } else{
           movie =  movieService.getMovie(form.getId());
        }
        movie.setTitle(form.getTitle());
       movie.setGenre(form.getGenre());
       movie.setReleaseDate(form.getReleaseDate());
       movie.setDuration(form.getDuration());
        movieService.addMovie(movie);
        return "redirect:/movies";
    }
    @GetMapping("/{id}/edit")
    public String edit( @PathVariable Integer id ,Model model ){
        Movie movie = movieService.getMovie(id);
        MovieForm form = new MovieForm();
        form.setId(movie.getId());
        form.setTitle(movie.getTitle());
        form.setReleaseDate(movie.getReleaseDate());
        form.setDuration(movie.getDuration());
        form.setGenre(movie.getGenre());
        model.addAttribute("movieForm" ,form);//Title Release Date Duration Genre
        return "movies/edit";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

}
