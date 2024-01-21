package com.mariocosta.entities.mappers;

import com.mariocosta.clients.donation.dto.DonationDto;
import com.mariocosta.entities.Donation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(
        componentModel = "spring"
)
public interface DonationMapper {

    @Mapping(source = "donationDate", target = "date")
    DonationDto DonationToDonationDto(Donation donation);
    @Mapping(source = "donationDate", target = "date")
    Collection<DonationDto> CollectionDonationToCollectionDonationDto(Collection<Donation> colletion);
}
