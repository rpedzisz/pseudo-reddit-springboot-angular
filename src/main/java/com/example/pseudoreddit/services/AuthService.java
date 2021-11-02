package com.example.pseudoreddit.services;


import com.example.pseudoreddit.classes.AuthenticationResponse;
import com.example.pseudoreddit.classes.LoginRequest;
import com.example.pseudoreddit.classes.RegisterRequest;
import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.NotificationEmail;
import com.example.pseudoreddit.models.Token;
import com.example.pseudoreddit.models.User;
import com.example.pseudoreddit.repository.TokenRepository;
import com.example.pseudoreddit.repository.UserRepository;
import com.example.pseudoreddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor

public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private  final TokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


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

        emailSenderService.sendMail(new NotificationEmail(
                "Pseudoreddit activation email",
                user.getEmail(),
                "Click on the link to activate your account: " +
                        "http://localhost:8080/api/auth/accountVerification/" + token


        ));

    }


    private String generateToken(User user) {

        String randomToken = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(randomToken);
        token.setUser(user);

        tokenRepository.save(token);

        return randomToken;
    }

    @Transactional
    public void fetchUserAndEnable(Token token){
        String username = token.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RedditException("Enabling username failed - user not found"));

            user.setEnabled(true);
            userRepository.save(user);

    }

    public void verifyAccount(String token){
       Optional<Token> verificationToken =  tokenRepository.findByToken(token);

       verificationToken.orElseThrow(() -> new RedditException("Email token error"));

        fetchUserAndEnable(verificationToken.get());


    }



    public AuthenticationResponse login(LoginRequest loginrequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getUsername(), loginrequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtProvider.generateToken(authentication);

      return new AuthenticationResponse(token, loginrequest.getUsername());
    }
}
