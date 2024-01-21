package com.mariocosta.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(
        name = "artist"
)
public class Artist {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
        generator = "artist_id_sequence"
    )
    @SequenceGenerator(
            name = "artist_id_sequence"
    )
    @Column(
            name = "artist_id"
    )
    private Integer id;

    @Column(
            name = "artist_name",
            nullable = false
    )
    private String name;

    @Column(
            name = "arte",
            nullable = false
    )
    private String arte;

    @Column(
            name = "aproved_state",
            nullable = false
    )
    private boolean aprovedState;
}
