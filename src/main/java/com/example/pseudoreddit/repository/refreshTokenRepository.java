package com.example.pseudoreddit.repository;

import com.example.pseudoreddit.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface refreshTokenRepository extends JpaRepository<RefreshToken, Long> { // Long - primary key type
    Optional<RefreshToken> findByTokenAndUsername(String token, String username);
    void deleteByTokenAndUsername(String token, String username);
}
