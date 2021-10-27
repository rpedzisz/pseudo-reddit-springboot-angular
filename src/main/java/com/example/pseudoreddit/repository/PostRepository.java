package com.example.pseudoreddit.repository;

import com.example.pseudoreddit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> { // Long - primary key type
}
