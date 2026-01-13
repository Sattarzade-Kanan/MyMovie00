package com.example.demo.controller;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
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
    public String list(@RequestParam(required = false) String title, @RequestParam(required = false) String genre,Model model){
        model.addAttribute("movies" ,movieService.search(title, genre));
        model.addAttribute("title" ,title);
        model.addAttribute("genre" ,genre);

        return "movies/list";
    }
    @GetMapping("/new")
       public String form(Model model){
    model.addAttribute("movie" , new Movie());
    return "movies/new";
    }
    @PostMapping
      public String save(@Valid @ModelAttribute Movie movie, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "movies/new";
        }
        movieService.addMovie(movie);
        return "redirect:/movies";
    }
    @GetMapping("/{id}/edit")
    public String edit( @PathVariable Integer id ,Model model ){
        model.addAttribute("movie" ,movieService.getMovie(id));
        return "movies/edit";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}
