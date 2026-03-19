package com.example.demo.controller;

import com.example.demo.service.DirectorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
