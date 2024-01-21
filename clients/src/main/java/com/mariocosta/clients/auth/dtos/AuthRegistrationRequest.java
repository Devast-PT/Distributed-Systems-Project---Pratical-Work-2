package com.mariocosta.clients.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthRegistrationRequest {
    @JsonProperty("username")
    private String username;


    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
