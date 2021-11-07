package com.example.pseudoreddit.services;

import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.RefreshToken;
import com.example.pseudoreddit.repository.refreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final refreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshTokenForUser(String username){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreationDate(Instant.now());
        refreshToken.setUsername(username);

       return refreshTokenRepository.save(refreshToken);

    }


    void validateRefreshTokenForUser(String token, String username){

        refreshTokenRepository.findByTokenAndUsername(token,username)
                .orElseThrow(() -> new RedditException("Couldnt find token with that username"));
    }

    public void deleteRefreshTokenForUser(String token, String username){
        refreshTokenRepository.deleteByTokenAndUsername(token, username);
    }






}
