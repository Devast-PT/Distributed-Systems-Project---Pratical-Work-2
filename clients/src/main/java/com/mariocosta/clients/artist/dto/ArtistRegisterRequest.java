package com.mariocosta.clients.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRegisterRequest {
    @JsonProperty("userId")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("art")
    private String art;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("log")
    private Double log;

    @JsonProperty("date")
    private LocalDate date;
}
