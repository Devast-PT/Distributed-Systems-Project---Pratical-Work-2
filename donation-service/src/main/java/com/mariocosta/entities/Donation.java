package com.mariocosta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(
        name = "donation"
)
public class Donation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donation_sequence_id"
    )
    @SequenceGenerator(
            name = "donation_sequence_id",
            sequenceName = "donation_sequence_id"
    )
    @Column(
            name = "donation_id"
    )
    private Integer id;

    @Column(
            name = "donation_date",
            nullable = false
    )
    private LocalDateTime donationDate;

    @Column(
            name = "amount",
            nullable = false
    )
    private Double amount;

    @Column(
            name = "artist_id",
            nullable = false
    )
    private Integer artistId;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Integer userId;
}
