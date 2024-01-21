package com.mariocosta.repositories;

import com.mariocosta.entities.Rating;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;

public interface RatingRepository  extends ListCrudRepository<Rating,Integer> {

    Collection<Rating> findByUserId(Integer userId);

    Collection<Rating> findByArtistId(Integer artistId);


}
