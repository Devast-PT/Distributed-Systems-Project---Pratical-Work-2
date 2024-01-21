package com.mariocosta.clients.donation;

import com.mariocosta.clients.donation.dto.DonationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(value = "donation",
        path = "api/v1/donation")
public interface DonationClient {

    @GetMapping("/")
    ResponseEntity<Collection<DonationDto>> getAllDonations();

    @GetMapping("/{artistId}")
    ResponseEntity<Collection<DonationDto>> getDonationsByArtist(@PathVariable("artistId") int artistId);

    @GetMapping("/register")
    public ResponseEntity<String> registerDonation(@RequestParam("amount") double amount,
                                                   @RequestParam("artistId") int artistId,
                                                   @RequestParam("userId") int userId);

}
