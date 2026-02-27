package com.example.demo.controller;

import com.example.demo.dto.RegisterForm;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
      public String loginPage(@RequestParam(required = false)String error , @RequestParam(required = false) String logout){
        return "security/login";
    }

          @GetMapping("/access-denied")
          public String denied() {
        return "error/access-denied";
          }

          @GetMapping("/register")
            public String registerPage(Model model){
        model.addAttribute("form" , new RegisterForm());
        return "security/register";
          }
          @PostMapping("/register")
                 public String register(@Valid @ModelAttribute("form") RegisterForm form , BindingResult bindingResult){

            if (bindingResult.hasErrors()){
                return "security/register";
            }

               userService.register(form);

                     return "redirect:/login";
             }
}
