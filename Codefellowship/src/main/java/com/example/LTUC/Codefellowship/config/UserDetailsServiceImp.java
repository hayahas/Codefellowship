package com.example.LTUC.Codefellowship.config;

import com.example.LTUC.Codefellowship.models.ApplicationUser;
import com.example.LTUC.Codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        if(applicationUser == null){
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
        System.out.println("Found User: "+applicationUser.getUsername());
        return applicationUser;
    }
}
