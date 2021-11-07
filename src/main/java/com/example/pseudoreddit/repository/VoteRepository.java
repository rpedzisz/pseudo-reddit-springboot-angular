package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Post;
import com.example.pseudoreddit.models.User;
import com.example.pseudoreddit.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> { // Long - primary key type

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
