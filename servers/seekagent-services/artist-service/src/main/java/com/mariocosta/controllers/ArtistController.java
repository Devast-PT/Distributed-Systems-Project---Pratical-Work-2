package com.mariocosta.controllers;


import com.mariocosta.clients.artist.dto.ArtistDto;
import com.mariocosta.clients.artist.dto.ArtistLocationRequest;
import com.mariocosta.clients.artist.dto.ArtistRegisterRequest;
import com.mariocosta.clients.auth.dtos.AuthChangeRequest;
import com.mariocosta.clients.localization.dto.LocalizationDto;
import com.mariocosta.entities.mappers.ArtistMapper;
import com.mariocosta.services.ArtistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/artist")
@AllArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper mapper;

    @GetMapping("/")
    public Collection<ArtistDto> getAllArtist(){
        log.info(
                "Request to get all artists"
        );
        return this.mapper.CollectionArtistToCollectionArtistDto(
                this.artistService.findAllArtist()
        );
    }

    @PostMapping("/register/")
    public ResponseEntity<String> registerArtist(@RequestBody ArtistRegisterRequest request){
        log.info(
                "Request to register an artist = {}",
                request
        );
        return artistService.saveArtist(
                request
        );
    }

    @PostMapping("/")
    public Collection<ArtistDto> getArtistByName(@RequestBody ArtistDto artist){
        log.info(
                "Request to get an artist by name = {}",
                artist
        );
        return this.mapper.CollectionArtistToCollectionArtistDto(artistService.findArtistByName(artist.getName()));
    }

    @GetMapping("/byArt/{art}")
    public ResponseEntity<String> byArt(@PathVariable("art") String art){
        return ResponseEntity.ok().body(
                this.artistService.findByArt(art)
        );
    }

    @GetMapping("/exists/{artistId}")
    public Boolean artistExists(@PathVariable("artistId") Integer artistId){
        log.info(
                "Request to check if user exists by id = {}",
                artistId
        );
        return artistService.artistExistsById(artistId);
    }

    @GetMapping("/aprove/{artistId}")
    public ResponseEntity<String> artistAprove(@PathVariable("artistId") Integer artistid){
        log.info(
                "Request to aprove an artist with id = {}",
                artistid
        );
        return artistService.aproveArtist(artistid);
    }

    @PostMapping("/byLocation")
    public ResponseEntity<String> artistByLocationPerformance(@RequestBody ArtistLocationRequest request){

        LocalizationDto dto = LocalizationDto.builder()
                .lat(request.getLat())
                .longi(request.getLog())
                .build();

        return ResponseEntity.ok().body(
                this.artistService.findAllByLocations(dto)
        );
    }

    @PostMapping("/previousdate/")
    public ResponseEntity<String> artistShowsPreviousToDate ( @RequestBody ArtistRegisterRequest request){
        String name = request.getName();
        LocalDate date = request.getDate();

        return ResponseEntity.ok().body(
                this.artistService.findPreviousDate(name, date)
        );
    }

    @PostMapping("/nextDate/")
    public ResponseEntity<String> artistShowsNextToDate ( @RequestBody ArtistRegisterRequest request){
        String name = request.getName();
        LocalDate date = request.getDate();

        return ResponseEntity.ok().body(
                this.artistService.findByNextDate(name, date)
        );
    }

    @GetMapping("/seeall/{status}")
    public ResponseEntity<String> seeALl (@PathVariable("status") Boolean status){

        return ResponseEntity.ok().body(
                this.artistService.byAproval(status)
        );
    }

    @PostMapping("/change/")
    public ResponseEntity<String> changeArtist(@RequestBody AuthChangeRequest authChangeRequest)
    {
        log.info("{}", authChangeRequest);

        if (this.artistService.artistExistsById(Integer.parseInt(authChangeRequest.getId()))){
            return ResponseEntity.ok().body(
                    this.artistService.changeInformationArtist(
                            authChangeRequest.getId(),
                            authChangeRequest.getName(),
                            authChangeRequest.getArt()
                    )
            );
        } else {
            return ResponseEntity.ok().body("Artist id not found");
        }
    }
}
