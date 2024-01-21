package com.mariocosta.controllers;


import com.mariocosta.Exception.ApiRequestException;
import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthTokenAcessRefreshResponse;
import com.mariocosta.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRegistrationRequest request){
        if (request.getUsername() == null)
            throw new ApiRequestException("Username cant be null");
        if (request.getUsername().length() < 6)
            throw new ApiRequestException("Username most have min 6 characters");
        if (request.getEmail() == null)
            throw new ApiRequestException("Email cant be null");
        // Validate email using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches())
            throw new ApiRequestException("Invalid email format");
        if (request.getPassword() == null)
            throw new ApiRequestException("Password cant be null");
        if (request.getPassword().length() < 6)
            throw new ApiRequestException("Most have more then 6 characters");

        return ResponseEntity.status(200).body(
                this.service.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenAcessRefreshResponse>  login (@RequestBody AuthRegistrationRequest request){
        if (request.getUsername() == null || request.getPassword() == null)
            throw new ApiRequestException("username and password cant be null");
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty())
            throw new ApiRequestException("username and password cant be empty");
        return ResponseEntity.status(200).body(
                this.service.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenAcessRefreshResponse> refresh (@RequestBody AuthTokenAcessRefreshResponse refreshToken){
        if (refreshToken == null)
            throw new RuntimeException("refreshToken can't be null");
        if (refreshToken.getRefreshToken() == null)
            throw new RuntimeException("refreshToken can't be null");
        if (refreshToken.getRefreshToken().isEmpty())
            throw new RuntimeException("refreshToken is empty");

        return ResponseEntity.ok().body(
                this.service.refresh(
                        refreshToken.getRefreshToken()
                )
        );
    }

    @GetMapping("/turnAdmin/{username}")
    public ResponseEntity<String> turnAdmin (@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable("username") String username){
        log.info("Trying to turn admin {}", username);
        if (username.isEmpty())
            return ResponseEntity.ok().body("username nao pode estar vazio");

        if (this.service.verifyToken(authorizationHeader)) return ResponseEntity.ok().body(
                this.service.turnAdmin(username)
        );

        throw new ApiRequestException("No authorization");
    }

    @GetMapping("/secured")
    public String testPlace()
    {
        return "Good job";
    }



}
