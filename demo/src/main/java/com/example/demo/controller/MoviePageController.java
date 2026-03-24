package com.example.demo.controller;

import com.example.demo.dto.MovieDTO;
import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Director;

import com.example.demo.entity.Movie;
import com.example.demo.repository.DirectorRepository;
import com.example.demo.service.interfaces.MovieService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/movies")
public class MoviePageController {
    private final MovieService movieService;
    private  final DirectorRepository directorRepository;

    public MoviePageController(MovieService movieService, DirectorRepository directorRepository) {
        this.movieService = movieService;
        this.directorRepository = directorRepository;
    }

       @GetMapping
        public String list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc")String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model, Authentication authentication){

             boolean isAdmin = authentication.getAuthorities()
             .stream()
             .anyMatch( a -> a.getAuthority().equals("ROLE_ADMIN"));

             //Stream - наблюдает anyMatch совпадает ли получается вместе наблюдает  совпадает ли?
             if (page < 0 ){
                page = 0;
            }

              Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
              Page<MovieDTO> moviePage = movieService.searchPaginated(title,genre,page,size,sort);

              if (page >= moviePage.getTotalPages() && moviePage.getTotalPages() > 0){
                  page = moviePage.getTotalPages() - 1;
                  moviePage = movieService.searchPaginated(title,genre,page,size,sort);
              }


        //|
        //UI
        model.addAttribute("movies" , moviePage.getContent());
        model.addAttribute("currentPage" , page);
        model.addAttribute("totalPages" , moviePage.getTotalPages());

        model.addAttribute("size" , size);
        model.addAttribute("title" ,title);
        model.addAttribute("genre" ,genre);

        model.addAttribute("sortBy" , sortBy);
        model.addAttribute("direction" , direction);
        model.addAttribute("isAdmin" , isAdmin);
        return "movies/list";
    }
        @GetMapping("/new")
         public String form(Model model){
         model.addAttribute("movieForm" , new MovieForm());
         model.addAttribute("directors" , directorRepository.findAll());
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
               } else {
                  movie =  movieService.getMovie(form.getId());
           }
        movie.setTitle(form.getTitle());
        movie.setGenre(form.getGenre());
        movie.setReleaseDate(form.getReleaseDate());
        movie.setDuration(form.getDuration());

        Director director = directorRepository.findById(form.getDirectorId())
                .orElseThrow(null);
        movie.setDirector(director);
        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String edit( @PathVariable Integer id ,Model model ){
        Movie movie = movieService.getMovie(id);
        MovieForm form = new MovieForm();
        form.setId(movie.getId());
        form.setTitle(movie.getTitle());
        form.setReleaseDate(movie.getReleaseDate());
        form.setDuration(movie.getDuration());
        form.setGenre(movie.getGenre());
        if (movie.getDirector() !=null){
            form.setDirectorId(movie.getDirector().getId());
       }

        model.addAttribute("movieForm" , form);
        model.addAttribute("directors" , directorRepository.findAll());
        return "movies/edit";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes){
        try{
            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("success", "Movie  delete successfully");

        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error" ,e.getMessage());
        }
        return "redirect:/movies";
    }

}
