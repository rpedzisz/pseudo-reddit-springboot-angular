package com.example.pseudoreddit.services;


import com.example.pseudoreddit.classes.AuthenticationResponse;
import com.example.pseudoreddit.classes.LoginRequest;
import com.example.pseudoreddit.classes.RefreshTokenRequest;
import com.example.pseudoreddit.classes.RegisterRequest;
import com.example.pseudoreddit.exceptions.RedditException;
import com.example.pseudoreddit.models.NotificationEmail;
import com.example.pseudoreddit.models.Token;
import com.example.pseudoreddit.models.User;
import com.example.pseudoreddit.repository.TokenRepository;
import com.example.pseudoreddit.repository.UserRepository;
import com.example.pseudoreddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private  final TokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


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

    @Transactional
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String usrname = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        return userRepository.findByUsername(usrname)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + usrname));
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

      return AuthenticationResponse.builder()
              .authenticationToken(token)
              .refreshToken(refreshTokenService.generateRefreshTokenForUser(loginrequest.getUsername()).getToken())
              .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
              .username(loginrequest.getUsername())
              .build();



    }


    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        refreshTokenService.validateRefreshTokenForUser(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUsername());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();

    }
}
