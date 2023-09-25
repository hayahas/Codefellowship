package com.example.LTUC.Codefellowship.controllers;

import com.example.LTUC.Codefellowship.models.ApplicationUser;
import com.example.LTUC.Codefellowship.models.Posts;
import com.example.LTUC.Codefellowship.repositories.ApplicationUserRepository;
import com.example.LTUC.Codefellowship.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostsController {

    @Autowired
    PostsRepository postsRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    //user id
    @GetMapping("/myprofile")
    public String returnUserProfile(Principal p, Model m){

        if(p!=null){
            String username= p.getName();

            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
                List<Posts> posts = applicationUser.getPosts();
                m.addAttribute("p",posts);


            m.addAttribute("username",username);
            m.addAttribute("firstname",applicationUser.getFirstname());
            m.addAttribute("lastname",applicationUser.getLastname());
            m.addAttribute("dateOfBirth",applicationUser.getDateOfBirth());
            m.addAttribute("bio",applicationUser.getBio());


        }
        return "UserProfileInfo.html";
    }

    //create new post
    @PostMapping("/createpost")
    public RedirectView createPost(
            RedirectAttributes redir, Principal p, String body) {
        if (body == null) {
            redir.addFlashAttribute("Please add content for your post.");
        return new RedirectView("/myprofile");
        }

        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            Posts post = new Posts(applicationUser, LocalDate.now(), body);

            postsRepository.save(post);

        }
        return new RedirectView("/myprofile");
    }

    @PostMapping("/following/{id}")
      public RedirectView followUser(Principal p){

        return new RedirectView("");
      }
    @GetMapping("/following/{id}")
    public String followingProfile(Principal p,Model m, @PathVariable Long id){
        if(p!=null){
            String username= p.getName();
            ApplicationUser follower = applicationUserRepository.findByUsername(username);
        }

        ApplicationUser followeduser = applicationUserRepository.findById(id).orElseThrow();
        m.addAttribute("username",followeduser.getUsername());
        List<Posts> userPosts = followeduser.getPosts();
        m.addAttribute("p",userPosts);

        return "followingUserPosts.html";
    }


    @GetMapping("/feed")
    public String feed(Principal p,Model m){
        if(p!=null){
            String username= p.getName();
            ApplicationUser follower = applicationUserRepository.findByUsername(username);
            List<ApplicationUser> followingusers = follower.getFollowing();
            List<Posts> userPosts = new ArrayList<>();
            for (ApplicationUser followinguser:followingusers) {
                userPosts.addAll(followinguser.getPosts());
            }

            m.addAttribute("p",userPosts);
        }

        return "feedPage.html";
    }


    //handle errors
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException
    {
        ResourceNotFoundException(String message)
        {
            super(message);
        }
    }

    }


