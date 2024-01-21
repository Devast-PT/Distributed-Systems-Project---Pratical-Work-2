package com.mariocosta.clients.auth;

import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthTokenAcessRefreshResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth",
        path = "api/v1/auth")
public interface AuthClient {

    @PostMapping("/register")
    ResponseEntity<AuthTokenAcessRefreshResponse> register(@RequestBody AuthRegistrationRequest request);
}
