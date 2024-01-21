package com.mariocosta.services;

import com.mariocosta.clients.artist.ArtistClient;
import com.mariocosta.clients.artist.dto.ArtistDto;
import com.mariocosta.entities.Rating;
import com.mariocosta.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository repository;
    private final ArtistClient artistClient;
    public Collection<Rating> getAllRatings() {
        return this.repository.findAll();
    }


    public Collection<Rating> getAllRatingsByUserId(Integer userId) {
        return this.repository.findByUserId(userId);
    }


    public Collection<Rating> getAllRatingsByArtistId(Integer artistId) {
        return this.repository.findByArtistId(artistId);
    }

    
    public Double getFinalRatingFromArtistId(Integer artistId){
        // TODO: 17/01/2024 Calculate the median from all ratings of artist 
        return 0D;
    }

    public String registerRating(Integer userId, String artistName, Integer rating) {
        ArrayList<ArtistDto> list = (ArrayList<ArtistDto>) this.artistClient.getArtistByName(
                ArtistDto.builder()
                        .name(artistName)
                        .build()
        );

        log.info("Going to register a rating from user {} into artist {} with the value {}",
                userId,
                artistName,
                rating);
        if (list.size() != 1)
            return "Found more then one user with that name";

        ArtistDto dto = list.get(0);



        this.repository.save(
                Rating.builder()
                        .userId(userId)
                        .artistId(dto.getId())
                        .artistRating(rating)
                        .build()
        );
        return "Thanks for rating our artist!";
    }

    public String getAllRatingsByArtistName(String artistName) {
        ArrayList<ArtistDto> list = (ArrayList<ArtistDto>) this.artistClient.getArtistByName(
                ArtistDto.builder()
                        .name(artistName)
                        .build()
        );

        log.info("List all ratings from artist {}", artistName);
        if (list.size() != 1)
            return "Try again with another name";


        ArtistDto dto = list.get(0);

        ArrayList<Integer> listOfRatings = new ArrayList<>();

        ArrayList<Rating> listRatings = (ArrayList<Rating>) this.repository.findByArtistId(
                dto.getId()
        );

        if (listRatings == null || listRatings.isEmpty())
            return "Sorry that artist have no rating";

        for (Rating r : listRatings)
            listOfRatings.add(
                    r.getArtistRating()
            );
        int soma = 0;
        for ( Integer i : listOfRatings){
            soma += i;
        }
        double media = (double) soma / listOfRatings.size();

        return listOfRatings + " com media de " + String.valueOf(media);
    }
}
