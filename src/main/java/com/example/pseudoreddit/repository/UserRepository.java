package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Long - primary key type

    Optional<User> findByUsername(String username);

}
