package com.mariocosta.repositories;

import com.mariocosta.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Collection<Artist> findAllByNameContainingIgnoreCase(String name);

    Collection<Artist> findByAprovedState(Boolean aprovedState);

    Collection<Artist> findAllByArteContains (String art);

    Collection<Artist> findAllByAprovedStateEquals (boolean aprovedState);
}
