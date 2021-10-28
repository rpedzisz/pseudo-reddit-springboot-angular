package com.example.pseudoreddit.services;


import com.example.pseudoreddit.classes.RegisterRequest;
import com.example.pseudoreddit.models.Token;
import com.example.pseudoreddit.models.User;
import com.example.pseudoreddit.repository.TokenRepository;
import com.example.pseudoreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor

public class AuthService {


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private  final TokenRepository tokenRepository;
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();

        user.setUsername(registerRequest.getUsername());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setEmail(registerRequest.getEmail());
        user.setCreationDate(Instant.now());
        user.setEnabled(false);
        user.setAdmin(false);
        userRepository.save(user);
        String token = generateToken(user);



    }


    private String generateToken(User user) {

        String randomToken = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(randomToken);
        token.setUser(user);

        tokenRepository.save(token);

        return randomToken;
    }






}
