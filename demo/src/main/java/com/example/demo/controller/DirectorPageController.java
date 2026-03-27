package com.example.demo.controller;

import com.example.demo.dto.DirectorDTO;
import com.example.demo.dto.DirectorForm;
import com.example.demo.entity.Director;
import com.example.demo.repository.DirectorRepository;
import com.example.demo.service.DirectorService;
import jakarta.validation.Valid;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/directors")
public class DirectorPageController {
    private final DirectorService directorService;
    private final DirectorRepository directorRepository;
    public DirectorPageController(DirectorService directorService, DirectorRepository directorRepository) {
        this.directorService = directorService;
        this.directorRepository = directorRepository;
    }

    @GetMapping
       public String list(
               @RequestParam(required = false) String name,
               @RequestParam(required = false) String surname,
               @RequestParam(defaultValue = "name") String sortBy,
               @RequestParam(defaultValue = "asc") String direction,
               @RequestParam(defaultValue = "0") int page,
               @RequestParam(defaultValue = "5") int size,
               Model model
    ){

        if (page < 0 ){
            page = 0;
        }

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        model.addAttribute("name" , name);
        model.addAttribute("surname" , surname);
        model.addAttribute("sortBy" , sortBy);
        model.addAttribute("direction" , direction);
        return "directors/list";
    }
    @PostMapping
    public String save(@Valid @ModelAttribute("directorForm")DirectorForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return form.getId() == null ? "directors/new" : "directors/edit";
        }

        Director director;
        if (form.getId() == null){
            director = new Director();
        } else{
            director = directorService.getDirector(form.getId());
        }


        director.setName(form.getName());
        director.setSurname(form.getSurname());
        director.setBirthday(form.getBirthday());
        director.setAge(form.getAge());

        directorService.newDirector(director);
        return "redirect:/directors";
    }

       @GetMapping("/new")
       public String form(Model model){
        model.addAttribute("directorForm" , new DirectorForm());
        model.addAttribute("directors" , directorRepository.findAll());
        return "directors/new";
       }

    @GetMapping("/edit/{id}")
    public String editDirector(@PathVariable Integer id,Model model) {
        Director director = directorService.getDirector(id);
        DirectorForm form = new DirectorForm();
        form.setId(director.getId());
        form.setName(director.getName());
        form.setSurname(director.getSurname());
        form.setBirthday(director.getBirthday());
        form.setAge(director.getAge());

        model.addAttribute("movieForm" , form);
        return "directors/edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteDirector(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            directorService.deleteDirector(id);
            redirectAttributes.addFlashAttribute("success" , "Director delete successfully");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error" , e.getMessage());
        }
        return "redirect:/directors";
    }
}
