package com.example.LTUC.Codefellowship.controllers;

import com.example.LTUC.Codefellowship.models.ApplicationUser;
import com.example.LTUC.Codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class AuthenticationController {

@Autowired
    ApplicationUserRepository applicationUserRepository;

@GetMapping("/loginfromwelcome")
    public String loginFromWelcome(){
    return "LoginPage.html";
}

    @GetMapping("/signupfromwelcome")
    public String signupFromWelcome(){
        return "SignupPage.html";
    }


    @GetMapping("/loginfromsignup")
    public String loginFromSignup(){
        return "LoginPage.html";
    }

    @GetMapping("/signupfromlogin")
    public String signupFromlogin(){
        return "SignupPage.html";
    }

    @GetMapping("/logout")
    public String logout(){
    return "LoginPage.html";
    }

    @PostMapping("/signup")
 public RedirectView signup(String username, String password, String firstname, String lastname, Date dateOfBirth, String bio){
    String hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt(12));

     ApplicationUser applicationUser = new ApplicationUser(username,password,firstname, lastname,dateOfBirth,bio);
     applicationUserRepository.save(applicationUser);

return new RedirectView("/login");
 }


 @PostMapping("/login")
    public RedirectView login(HttpServletRequest request, String username,String password){

    ApplicationUser userFromDB = new ApplicationUser(username,password);

    if((userFromDB == null) || (BCrypt.checkpw(password,userFromDB.getPassword())))
        return new RedirectView("/login");

     HttpSession session = request.getSession();
     session.setAttribute("username",username);
     return new RedirectView("/home");

 }

 @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request){

    HttpSession session = request.getSession();
    session.invalidate();

    return new RedirectView("/login");
 }



}
