package com.mariocosta.entities.mappers;


import com.mariocosta.clients.localization.dto.LocalizationDto;
import com.mariocosta.entities.Localization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(
        componentModel = "spring"
)
public interface LocalizationMapper {

    LocalizationDto LocalizationToLocalizationDto (Localization local);

    Localization LocalizationDtoToLocalization (LocalizationDto localDto);

    @Mapping(
            source = "id", target = "id"
    )
    Collection<LocalizationDto> CollectionLocalizationToCollectionLocalizationDto (Collection<Localization> collection);

    Collection<Localization> ColllectionLocalizationDtoToCollectionLocalization (Collection<LocalizationDto> collectionDto);

}
