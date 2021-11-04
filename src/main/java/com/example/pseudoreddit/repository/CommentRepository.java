package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Comment;
import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> { // Long - primary key type
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
