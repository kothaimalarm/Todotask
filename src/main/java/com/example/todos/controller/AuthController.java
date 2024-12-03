package com.example.todos.controller;

import com.example.todos.entity.User;
import com.example.todos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String indexPage(Model model){
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model){
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if(existingUser.isPresent()){
            result.rejectValue("email",null,"There is an account registered with this email");
        }

        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "/register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
