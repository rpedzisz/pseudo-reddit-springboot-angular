package com.example.pseudoreddit.repository;


import com.example.pseudoreddit.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> { // Long - primary key type

    Optional<Token> findByToken(String token);


}
