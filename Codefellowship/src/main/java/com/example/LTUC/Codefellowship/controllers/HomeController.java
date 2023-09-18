package com.example.LTUC.Codefellowship.controllers;

import com.example.LTUC.Codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/home")
    public String homePage(){
        return "HomePage.html";
    }


    @PostMapping("/home")
    public RedirectView home(){
        return new RedirectView("/home");
    }
}
