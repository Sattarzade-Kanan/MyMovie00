package com.example.demo.service;

import com.example.demo.dto.RegisterForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
      public void register(RegisterForm form){
        if (userRepository.existsByUsername(form.getUsername())){
               throw  new RuntimeException("This name already exists!");
        }


          User user = new User();

           user.setUsername(form.getUsername());
           user.setPassword(passwordEncoder.encode(form.getPassword()));
           user.setRole("ROLE_USER");
           userRepository.save(user);
      }
}
