package com.mariocosta.repositories;

import com.mariocosta.entities.Donation;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DonationRepository extends ListCrudRepository<Donation, Integer> {

    Collection<Donation> findAllByArtistId(Integer artistId);
}
