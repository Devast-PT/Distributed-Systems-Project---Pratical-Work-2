package com.mariocosta.controllers;

import com.mariocosta.clients.artist.ArtistClient;
import com.mariocosta.clients.donation.dto.DonationDto;
import com.mariocosta.entities.mappers.DonationMapper;
import com.mariocosta.services.DonationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/donation")
@Slf4j
public class DonationController {

    private final DonationService service;
    private final DonationMapper mapper;
    private final ArtistClient artistClient;

    @GetMapping("/")
    public ResponseEntity<Collection<DonationDto>> getAllDonations(){
        log.info("Got a request for alldonations");
        return ResponseEntity.ok().body(this.mapper.CollectionDonationToCollectionDonationDto(this.service.allDonations()));
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<Collection<DonationDto>> getDonationsByArtist(@PathVariable("artistId") int artistId){
        log.info("Got a request for artist with id {}", artistId);
        return ResponseEntity.ok().body(this.mapper.CollectionDonationToCollectionDonationDto(
                this.service.getDonationsByArtist(artistId)
        ));
    }


    @GetMapping("/register/{userId}/{artistId}/{amount}")
    public ResponseEntity<String> registerDonation(@PathVariable("amount") double amount,
                                                   @PathVariable("artistId") int artistId,
                                                   @PathVariable("userId") int userId) {
        // TODO: 17/01/2024 Check for userid in security context
        log.info(
                "Got a request to register a donation with amount = {} + artistId = {} + userId = {}",
                amount,
                artistId,
                userId
        );

        // Checking for artistId
        if (!artistClient.artistExists(artistId))
            return ResponseEntity.ok().body("Not a valid artistId");
        if (amount < 5)
            return ResponseEntity.ok().body("Donation most be higher then 5€");

        if (this.service.registerDonation(amount, artistId, userId))
            return ResponseEntity.ok().body("Donation Received");
        else
            return ResponseEntity.badRequest().body("Donation Failed");

    }
}
