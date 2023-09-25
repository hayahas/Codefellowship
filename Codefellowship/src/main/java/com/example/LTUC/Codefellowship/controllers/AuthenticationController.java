package com.example.LTUC.Codefellowship.controllers;

import com.example.LTUC.Codefellowship.models.ApplicationUser;
import com.example.LTUC.Codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    HttpServletRequest request;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    //user sign up an acc
    @PostMapping("/signup")
    public RedirectView signupAnAccount( RedirectAttributes redir,String username, String password, String firstname, String lastname, String dateOfBirth, String bio){

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            redir.addFlashAttribute("errorMessage", "Please fill in both username and password fields.");
            return new RedirectView("/signup");
        }

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        String hashedPassword= passwordEncoder.encode(password);
        applicationUser.setPassword(hashedPassword);
        applicationUser.setFirstname(firstname);
        applicationUser.setLastname(lastname);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);
        applicationUserRepository.save(applicationUser);
        authWithHttpServletRequest(username, password);



        return new RedirectView("/");
    }

    //to not use the default login page from spring security
    public void authWithHttpServletRequest(String username, String password){

        try {
            request.login(username, password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
    //return signup page
    @GetMapping("/signup")
    public String returnSignup(){
        return "SignupPage.html";
    }

    //return login page
    @GetMapping("/login")
    public String returnLogin(){
        System.out.println("*****************************");
        System.out.println( SecurityContextHolder.getContext().getAuthentication().getName());
        return "LoginPage.html";
    }

    //user login
//    @PostMapping("/login")
//    public RedirectView homeFromLogin() {
//        return new RedirectView("/myprofile");
//    }

    //to return user profile on click
    @GetMapping("/user/{id}")
    public String returnUserProfileById(Principal p, Model m, @PathVariable Long id){

        if(p!=null){
            String username= p.getName();
            ApplicationUser applicationBrowsingUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("username",username);
            m.addAttribute("firstname",applicationBrowsingUser.getFirstname());
            m.addAttribute("lastname",applicationBrowsingUser.getLastname());
            m.addAttribute("dateOfBirth",applicationBrowsingUser.getDateOfBirth());
            m.addAttribute("bio",applicationBrowsingUser.getBio());

        }

        ApplicationUser applicationUser = applicationUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user with specified Id"));
        m.addAttribute("username",applicationUser.getUsername());
        m.addAttribute("firstname",applicationUser.getFirstname());
        m.addAttribute("lastname",applicationUser.getLastname());
        m.addAttribute("dateOfBirth",applicationUser.getDateOfBirth());
        m.addAttribute("bio",applicationUser.getBio());


        return "UserProfileById.html";
    }

//    putmapping
//    public RedirectView updateInfo(Principal p,Model m,String username,String firstname,String lastname,String dateOfBirth, String bio){
//
//        if (p != null ){
//            m.se
//        }
//
//    }

    //welcome page
    @GetMapping("/")
    public String homePage(Principal p, Model m){
        String username = null;
        if(p != null){
            username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("id",applicationUser.getId());

        }else{
            List<ApplicationUser> allUsers = applicationUserRepository.findAll();
            m.addAttribute("allUsers", allUsers);
        }
        return "HomePage.html";
    }

    //logout
    @GetMapping("/logout")
    public String logout(){
    return "LoginPage.html";
    }

//    //logout
//    @PostMapping("/logout")
//    public RedirectView logout(HttpServletRequest request){
//
//    HttpSession session = request.getSession();
//    session.invalidate();
//
//    return new RedirectView("/login");
//    }

    //to handle errors
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException
    {
        ResourceNotFoundException(String message)
        {
            super(message);
        }
    }

}
