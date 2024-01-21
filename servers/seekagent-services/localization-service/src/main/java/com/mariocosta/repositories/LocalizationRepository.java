package com.mariocosta.repositories;

import com.mariocosta.entities.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Integer> {

    Collection<Localization> findAllByArtistId(Integer artistId);

    Collection<Localization> findAllByLatAndLongi(Double lat, Double longi);

    Collection<Localization> findAllByArtistIdAndPerformanceDateBefore (Integer artistId, LocalDate performanceDate);

    Collection<Localization> findAllByArtistIdAndPerformanceDateAfter (Integer artistId, LocalDate performanceDate);
}
