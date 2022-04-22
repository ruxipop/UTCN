package com.bianca.controller;

import com.bianca.dao.*;
import com.bianca.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/mylogin")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("cont", authentication.getName());
        return "logout";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") User user){
        return "signup";
    }

    @PostMapping("/process-signup")
    public String processSignup(User user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.insertUser(user);

        return "redirect:/mylogin";
    }
}
