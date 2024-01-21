package com.mariocosta.clients.artist;


import com.mariocosta.clients.artist.dto.ArtistDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(
        value = "artist",
        path = "api/v1/artist"
)
public interface ArtistClient {

    @GetMapping("/")
    Collection<ArtistDto> getAllArtist();


    @PostMapping("/register/")
    public ResponseEntity<String> registerArtist(@RequestBody ArtistDto artistDto);

    @PostMapping("/")
    public Collection<ArtistDto> getArtistByName(@RequestBody ArtistDto artist);

    @GetMapping("/exists/{artistId}")
    public Boolean artistExists(@PathVariable("artistId") Integer artistId);

    @GetMapping("/aprove/{artistId}")
    public ResponseEntity<String> artistAprove(@PathVariable("artistId") Integer artistid);

}
