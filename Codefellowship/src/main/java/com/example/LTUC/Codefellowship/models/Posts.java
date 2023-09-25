package com.example.LTUC.Codefellowship.models;

import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @ManyToOne
    private ApplicationUser userId;
    private LocalDate timeCreated;
    
    private String body;

    public Posts() {
    }

    public Posts(ApplicationUser userId, LocalDate timeCreated, String body) {
        this.userId = userId;
        this.timeCreated = timeCreated;
        this.body = body;
    }

    public ApplicationUser getUserId() {
        return userId;
    }

    public void setUserId(ApplicationUser userId) {
        this.userId = userId;
    }

    public LocalDate getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDate timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
