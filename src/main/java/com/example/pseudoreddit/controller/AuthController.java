package com.example.pseudoreddit.controller;


import com.example.pseudoreddit.classes.AuthenticationResponse;
import com.example.pseudoreddit.classes.LoginRequest;
import com.example.pseudoreddit.classes.RefreshTokenRequest;
import com.example.pseudoreddit.classes.RegisterRequest;
import com.example.pseudoreddit.services.AuthService;
import com.example.pseudoreddit.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);

        return new ResponseEntity<>("Registration completed successfully", HttpStatus.OK);


    }


    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account verified", HttpStatus.OK);

    }


    @PostMapping("/login")
public AuthenticationResponse login(@RequestBody LoginRequest loginrequest){
       return authService.login(loginrequest);


    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){

    return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshTokenForUser(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted");
    }






}
