package com.mariocosta.controllers;


import com.mariocosta.Exception.ApiRequestException;
import com.mariocosta.clients.artist.ArtistClient;
import com.mariocosta.clients.localization.dto.LocalizationDto;
import com.mariocosta.servicies.LocalizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/localization")
@AllArgsConstructor
@Slf4j
public class LocalizationController {
    private final LocalizationService service;
    private final ArtistClient artistClient;

    @GetMapping("/")
    public Collection<LocalizationDto> getAllLocations(){
        // TODO: 17/01/2024 Add user info to log
        log.info("Request to return all locations.");
        return this.service.getAllLocations();
    }

    @GetMapping("/artistlocations/{artistId}")
    public Collection<LocalizationDto> getAllLocationsByArtist(@PathVariable("artistId") Integer artistId){
        // TODO: 17/01/2024 Add user info to log
        log.info("Request to return all locations by artistId = {}",
                artistId);

        if (artistClient.artistExists(artistId))
            return this.service.getAllLocationsByArtistId(artistId);

        throw new ApiRequestException("Invalid Artist Id");
    }

    @PostMapping("/bylocation/")
    public Collection<LocalizationDto> byLocations(@RequestBody LocalizationDto localizationDto){

        return this.service.allByLocation(
                localizationDto
        );
    }

    @PostMapping("/byPreviousDate")
    Collection<LocalizationDto> byPreviousDate(@RequestBody LocalizationDto localizationDto){

        return this.service.allByPreviousDate( localizationDto );
    }

    @PostMapping("/byNextDate")
    Collection<LocalizationDto> byNextDate(@RequestBody LocalizationDto localizationDto){

        return this.service.allByArtistNextShow( localizationDto );
    }

    @PostMapping("/register/")
    public ResponseEntity<String> registerLocation(@RequestBody LocalizationDto localizationDto){

        this.service.registerLocation(localizationDto);
        return ResponseEntity.ok().body("Register Sucess");


    }
}



