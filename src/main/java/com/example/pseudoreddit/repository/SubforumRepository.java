package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Subforum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubforumRepository extends JpaRepository<Subforum, Long> { // Long - primary key type
}
