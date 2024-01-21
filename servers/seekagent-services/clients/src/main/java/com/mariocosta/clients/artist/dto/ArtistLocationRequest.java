package com.mariocosta.clients.artist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistLocationRequest {

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("log")
    private Double log;

}
