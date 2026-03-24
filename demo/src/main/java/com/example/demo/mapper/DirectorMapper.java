package com.example.demo.mapper;

import com.example.demo.dto.DirectorDTO;

import com.example.demo.dto.DirectorForm;
import com.example.demo.dto.MovieForm;
import com.example.demo.entity.Director;
import org.springframework.stereotype.Component;

@Component
public class DirectorMapper {
    //MovieMapper-Помогает общаться с MovieForm и MovieDTO
    //list.html
    public DirectorDTO toDTO(Director director){
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setName(director.getName());
        dto.setSur_name(director.getSur_name());
        dto.setBirthday(director.getBirthday());
        dto.setAge(director.getAge());
        return dto;
    }
    //edit and new
    public DirectorForm toForm(Director director){
        DirectorForm form = new DirectorForm();
        form.setId(director.getId());
        form.setName(director.getName());
        form.setSur_name(director.getSur_name());
        form.setBirthday(director.getBirthday());
        form.setAge(director.getAge());
        return form;
    }
    //New and Edit Form ->Entity
    public void updatedEntityForm(DirectorForm form , Director director){
        director.setName(form.getName());
        director.setSur_name(form.getSur_name());
        director.setBirthday(form.getBirthday());
        director.setAge(form.getAge());
    }
}
