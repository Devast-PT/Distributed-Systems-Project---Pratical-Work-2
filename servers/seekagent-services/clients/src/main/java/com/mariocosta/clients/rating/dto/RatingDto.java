package com.mariocosta.clients.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RatingDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("artistId")
    private Integer artistId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("artistRating")
    private Integer artistRating;

}
