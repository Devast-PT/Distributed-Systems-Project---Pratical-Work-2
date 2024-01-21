package com.mariocosta.servicies;


import com.mariocosta.clients.localization.dto.LocalizationDto;
import com.mariocosta.entities.Localization;
import com.mariocosta.entities.mappers.LocalizationMapper;
import com.mariocosta.repositories.LocalizationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class LocalizationService {

    private final LocalizationRepository repository;
    private final LocalizationMapper mapper;


    public Collection<LocalizationDto> getAllLocations() {
        return this.mapper.CollectionLocalizationToCollectionLocalizationDto(
                this.repository.findAll()
        );
    }

    public Collection<LocalizationDto> getAllLocationsByArtistId(Integer artistId) {
        return this.mapper.CollectionLocalizationToCollectionLocalizationDto(
                this.repository.findAllByArtistId(artistId)
        );
    }

    public boolean registerLocation(LocalizationDto localizationDto) {
        Localization location = Localization.builder()
                .artistId(localizationDto.getArtistId())
                .userId(localizationDto.getUserId())
                .lat(localizationDto.getLat())
                .longi(localizationDto.getLongi())
                .creationDate(localizationDto.getCreationDate())
                .performanceDate(localizationDto.getPerformanceDate())
                .build();
        this.repository.saveAndFlush(location);
        System.out.println(location);
        return location.getId() != null;
    }

    public Collection<LocalizationDto> allByLocation(LocalizationDto localizationDto) {
        log.info("Por localizaçao entrada {}", localizationDto);

            return this.mapper.CollectionLocalizationToCollectionLocalizationDto(this.repository.findAllByLatAndLongi(
                    localizationDto.getLat(), localizationDto.getLongi()
            )
        );

    }

    public Collection<LocalizationDto> allByPreviousDate(LocalizationDto localizationDto) {

        log.info("Request to find from artist {} all dates previous to {}", localizationDto.getArtistId(), localizationDto.getPerformanceDate());

        Collection<LocalizationDto> coll = this.mapper.CollectionLocalizationToCollectionLocalizationDto(this.repository.findAllByArtistIdAndPerformanceDateBefore(
                localizationDto.getArtistId(),
                localizationDto.getPerformanceDate()
        ));

        log.info("Found {}", coll);

        return coll;
    }

    public Collection<LocalizationDto> allByArtistNextShow(LocalizationDto localizationDto) {

        Collection<LocalizationDto> coll = this.mapper.CollectionLocalizationToCollectionLocalizationDto(this.repository.findAllByArtistIdAndPerformanceDateAfter(
                localizationDto.getArtistId(),
                localizationDto.getPerformanceDate()
        ));

        return coll;
    }
}
