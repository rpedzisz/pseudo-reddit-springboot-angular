package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> { // Long - primary key type
}
