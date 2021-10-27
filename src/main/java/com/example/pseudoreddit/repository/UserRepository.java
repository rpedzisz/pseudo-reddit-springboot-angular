package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Long - primary key type
}
