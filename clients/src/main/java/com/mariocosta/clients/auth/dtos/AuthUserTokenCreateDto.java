package com.mariocosta.clients.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserTokenCreateDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("role")
    private String role;
}
