package com.mariocosta.entities.mappers;

import com.mariocosta.clients.artist.dto.ArtistDto;
import com.mariocosta.entities.Artist;

import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(
        componentModel = "spring"
)
public interface ArtistMapper {
    ArtistDto ArtistToArtistDto(Artist artist);
    Artist ArtistDtoToArtist(ArtistDto artist);
    Collection<ArtistDto> CollectionArtistToCollectionArtistDto(Collection<Artist> collection);
}
