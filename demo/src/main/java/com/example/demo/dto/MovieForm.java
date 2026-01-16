package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieForm {
    private Integer id;
    @NotBlank(message ="Title required")
    private String title;

//    @NotBlank(message ="ReleaseDate required")
    private String releaseDate;

//    @NotBlank(message ="Duration required")
    private String duration;

    @NotBlank(message ="Genre required")
    private String genre;

}
