package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> { // Long - primary key type
}
