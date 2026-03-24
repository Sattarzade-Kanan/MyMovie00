package com.example.demo.controller;

import com.example.demo.dto.DirectorForm;
import com.example.demo.entity.Director;
import com.example.demo.service.DirectorService;
import jakarta.validation.Valid;
import org.junit.jupiter.params.provider.ValueSource;
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

    public DirectorPageController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
       public String list(
               @RequestParam(required = false) String name,
               @RequestParam(required = false) String surName,
               @RequestParam(defaultValue = "name") String sortBy,
               @RequestParam(defaultValue = "asc") String direction,
               Model model
    ){
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        model.addAttribute("name" , name);
        model.addAttribute("surName" , surName);
        model.addAttribute("sortBy" , sortBy);
        model.addAttribute("direction" , direction);
        return "directors/director-list";
    }
    @PostMapping
    public String save(@Valid @ModelAttribute("directorForm")DirectorForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return form.getId() == null ? "directors/director-new" : "directors/director-edit";
        }

        Director director;
        if (form.getId() == null){
            director = new Director();
        } else{
            director = directorService.getDirector(form.getId());
        }


        director.setName(form.getName());
        director.setSur_name(form.getSur_name());
        director.setBirthday(form.getBirthday());
        director.setAge(form.getAge());

        directorService.newDirector(director);
        return "redirect:/directors";
    }

    @GetMapping("/director-edit/{id}")
    public String editDirector(@PathVariable Integer id,Model model) {
        Director director = directorService.getDirector(id);
        DirectorForm form = new DirectorForm();
        form.setId(director.getId());
        form.setName(director.getName());
        form.setSur_name(director.getSur_name());
        form.setBirthday(director.getBirthday());
        form.setAge(director.getAge());

        model.addAttribute("movieForm" , form);
        return "directors/director-edit";
    }

    @PostMapping("/director-delete/{id}")
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
