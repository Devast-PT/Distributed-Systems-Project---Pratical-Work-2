package com.mariocosta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "rating"
)
public class Rating{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sequence_rating"
    )
    @SequenceGenerator(
            name = "sequence_rating",
            sequenceName = "sequence_rating"
    )
    @Column(
            name = "rating_id"
    )
    private Integer id;

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

    @Column(
            name = "artist_rating",
            nullable = false
    )
    private Integer artistRating;
}
