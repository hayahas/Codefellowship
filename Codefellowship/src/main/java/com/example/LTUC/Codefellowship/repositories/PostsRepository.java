package com.example.LTUC.Codefellowship.repositories;

import com.example.LTUC.Codefellowship.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    List<Posts> findByUserId(String userId);
}

