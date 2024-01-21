package com.mariocosta.clients.donation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("artistId")
    private Integer artistId;

    @JsonProperty("userId")
    private Integer userId;
}
