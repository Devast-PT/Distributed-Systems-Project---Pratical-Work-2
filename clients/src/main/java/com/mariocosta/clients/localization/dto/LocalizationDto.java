package com.mariocosta.clients.localization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalizationDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("artistId")
    private Integer artistId;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("long")
    private Double longi;

    @JsonProperty("creationDate")
    private LocalDateTime creationDate;

    @JsonProperty("performanceDate")
    private LocalDate performanceDate;

}
