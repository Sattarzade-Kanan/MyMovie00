package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieNotFoundException.class)
    public String handleMovieNotFound(MovieNotFoundException ex, Model model){
        model.addAttribute("message" , ex.getMessage());
        return "movies/error/not-found";
    }

    @ExceptionHandler(DirectorNotFoundException.class)
    public String handleDirectorNotFound(DirectorNotFoundException ex, Model model){
        model.addAttribute("message" , ex.getMessage());
        return "movies/error/not-found";
    }
                   // 403
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDenied(AccessDeniedException ex, Model model){
        ModelAndView mav  = new ModelAndView("error/access-denied");
        mav.addObject("message" , ex.getMessage());
        return mav;
    }

}
