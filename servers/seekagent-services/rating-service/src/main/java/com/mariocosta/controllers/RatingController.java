package com.mariocosta.controllers;

import com.mariocosta.clients.artist.ArtistClient;
import com.mariocosta.clients.artist.dto.ArtistDto;
import com.mariocosta.clients.rating.dto.RatingDto;
import com.mariocosta.entities.mappers.RatingMapper;
import com.mariocosta.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/rating")
@RequiredArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService service;
    private final RatingMapper mapper;
    private final ArtistClient artistClient;


    @GetMapping("/")
    private Collection<RatingDto> ratings(){
        log.info("Request for all ratings");

        return this.mapper.CollectionRatingToCollectionRatingDto(
                this.service.getAllRatings()
        );
    }

    @GetMapping("/userid/{userId}")
    private Collection<RatingDto> ratingsByUsername(@PathVariable("userId") Integer userId){
        log.info("Request incoming for all ratings by user with id = {}",
                userId);
        return this.mapper.CollectionRatingToCollectionRatingDto(
                this.service.getAllRatingsByUserId(userId)
        );
    }

    @GetMapping("/getRatings/{artistName}")
    private ResponseEntity<String> ratingsByArtistName(@PathVariable("artistName") String artistName){



        return ResponseEntity.ok().body(
                this.service.getAllRatingsByArtistName(artistName)
        );
    }

    @GetMapping("/register/{userId}/{artistName}/{rating}")
    private ResponseEntity<String> registerRating(@PathVariable("userId") Integer userId,
                                                  @PathVariable("artistName") String artistName,
                                                  @PathVariable("rating") Integer rating){



        return ResponseEntity.ok().body(
                this.service.registerRating(
                        userId,
                        artistName,
                        rating
                )
        );
    }
}
