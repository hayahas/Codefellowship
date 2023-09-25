package com.example.LTUC.Codefellowship.repositories;

import com.example.LTUC.Codefellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
     ApplicationUser findByUsername(String username) ;


}
