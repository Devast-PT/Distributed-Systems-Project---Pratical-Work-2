package com.mariocosta.entities;


import jakarta.persistence.*;
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
@Entity(
        name = "localization"
)
public class Localization {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "artist_id_sequence"
    )
    @SequenceGenerator(
            name = "artist_id_sequence"
    )
    @Column(
            name = "local_id"
    )
    private Integer id;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Integer userId;

    @Column(
            name = "artist_id",
            nullable = false
    )
    private Integer artistId;

    @Column(
            name = "latitude",
            nullable = false
    )
    private Double lat;

    @Column(
            name = "longitude",
            nullable = false
    )
    private Double longi;

    @Column(
            name = "creation_date",
            nullable = false
    )
    private LocalDateTime creationDate;

    @Column(
            name = "performance_date",
            nullable = false
    )
    private LocalDate performanceDate;

}
