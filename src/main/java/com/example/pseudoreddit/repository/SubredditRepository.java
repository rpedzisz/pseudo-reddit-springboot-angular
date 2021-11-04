package com.example.pseudoreddit.repository;



import com.example.pseudoreddit.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> { // Long - primary key type
    Optional<Subreddit> findByName(String name);
}
