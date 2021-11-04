package com.example.pseudoreddit.repository;

import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.Subreddit;
import com.example.pseudoreddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);


}
