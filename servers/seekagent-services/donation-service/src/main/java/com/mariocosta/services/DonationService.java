package com.mariocosta.services;

import com.mariocosta.entities.Donation;
import com.mariocosta.repositories.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository repository;

    public Collection<Donation> getDonationsByArtist(Integer artistId) {
        return repository.findAllByArtistId(artistId);
    }

    public boolean registerDonation(Double amount, Integer artistId, Integer userId) {
        Donation donation = Donation.builder()
                .amount(amount)
                .artistId(artistId)
                .userId(userId)
                .donationDate(LocalDateTime.now())
                .build();

        this.repository.save(donation);

        return donation.getId() != null;
    }

    public Collection<Donation> allDonations() {
        return this.repository.findAll();
    }
}
