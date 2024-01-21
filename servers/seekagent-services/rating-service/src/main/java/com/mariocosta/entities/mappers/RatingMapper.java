package com.mariocosta.entities.mappers;

import com.mariocosta.clients.rating.dto.RatingDto;
import com.mariocosta.entities.Rating;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(
        componentModel = "spring"
)
public interface RatingMapper {


    RatingDto RatingToRatingDto (Rating rating);

    Rating RatingDtoToRating (RatingDto ratingdto);

    Collection<RatingDto> CollectionRatingToCollectionRatingDto (Collection<Rating> collection);

}
