package com.example.demo.controller;

import com.example.demo.entity.Director;
import com.example.demo.service.DirectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

      @GetMapping("/all")
    public List<Director> getAllDirectors(){
      return  directorService.getAllDirectors();
      }

      @GetMapping("/{id}")
    public Director getDirector(@PathVariable Integer id){
        return directorService.getDirector(id);
      }

      @PostMapping("/new")
    public Director addDirector(@RequestBody Director director) {
        return directorService.newDirector(director);
      }

      @PutMapping("/update/{id}")
    public Director updateDirector(@PathVariable Integer id ,@RequestBody Director updatedDirector){
        return directorService.updateDirector(id, updatedDirector);
      }
      @DeleteMapping("/delete/{id}")
    public void deleteDirector(@PathVariable Integer id){
         directorService.deleteDirector(id);
      }
}
