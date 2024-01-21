package com.mariocosta.services;

import com.mariocosta.clients.artist.dto.ArtistDto;
import com.mariocosta.clients.artist.dto.ArtistRegisterRequest;
import com.mariocosta.clients.kafka.KakfaClient;
import com.mariocosta.clients.kafka.dto.MessageRequest;
import com.mariocosta.clients.localization.LocalizationClient;
import com.mariocosta.clients.localization.dto.LocalizationDto;
import com.mariocosta.entities.Artist;

import com.mariocosta.entities.mappers.ArtistMapper;
import com.mariocosta.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {

    private final ArtistRepository repository;
    private final LocalizationClient LocalizationClient;
    private final ArtistMapper artistMapper;
    private final KakfaClient kakfaClient;

    public ResponseEntity<String> saveArtist(ArtistRegisterRequest request){



        ArrayList<Artist> artists = (ArrayList<Artist>) this.repository.findAllByNameContainingIgnoreCase(request.getName());
        log.info("Found : {}", artists);
        log.info("Checker: {} | {}", artists.size(), artists.isEmpty());

        if (artists.isEmpty()){

            Artist artist = Artist.builder()
                    .name(request.getName())
                    .arte(request.getArt())
                    .aprovedState(Boolean.FALSE)
                    .build();
            repository.saveAndFlush(
                    artist
            );

            LocalizationDto localizationDto = LocalizationDto.builder()
                    .userId(request.getId())
                    .artistId(artist.getId())
                    .longi(request.getLog())
                    .lat(request.getLat())
                    .creationDate(LocalDateTime.now())
                    .performanceDate(request.getDate())
                    .build();

            log.info("Antes Enviar localizaçao no registo 0 {}", localizationDto);

            return this.LocalizationClient.registerLocation(localizationDto);

        } else if (artists.size() == 1){

            Artist artist = artists.remove(0);
            LocalizationDto localizationDto = LocalizationDto.builder()
                    .userId(request.getId())
                    .artistId(artist.getId())
                    .longi(request.getLog())
                    .lat(request.getLat())
                    .creationDate(LocalDateTime.now())
                    .performanceDate(request.getDate())
                    .build();
            log.info("Antes Enviar localizaçao no registo 1 {}", localizationDto);

            return this.LocalizationClient.registerLocation(localizationDto);

        } else {
            return ResponseEntity.ok().body("You need to be more specific about the artist there are more then 1 which that name");
        }
    }

    public Collection<Artist> findArtistByName(String name) {
        return repository.findAllByNameContainingIgnoreCase(name);
    }

    public Boolean artistExistsById(Integer artistId) {
        return repository.findById(artistId).isPresent();
    }

    public Collection<Artist> findAllArtist() {
        return repository.findAll();
    }


    public ResponseEntity<String> aproveArtist(Integer artistid) {
        Artist artist = this.repository.findById(artistid).get();
        if (!artist.isAprovedState()){
            artist.setAprovedState(true);
            this.repository.saveAndFlush(artist);
            MessageRequest request = new MessageRequest("The artist " + artist.getName() + " was aproved!!!!");
            this.kakfaClient.publicar(request);
            return ResponseEntity.ok().body("Artist Aproved");
        } else {
            return ResponseEntity.badRequest().body("Artist already aproved or doesnt exists");
        }
    }

    public String findAllByLocations(LocalizationDto dto) {
        log.info("A enviar informação {}", dto);
        Collection<LocalizationDto> list = this.LocalizationClient.byLocations(
                dto
        );

        log.info("Recebi a lista com por lat e log {}", list);


        if (list.isEmpty()){
            return "Sorry no artist on that location";
        } else {
            ArrayList<ArtistDto> listFinal = new ArrayList<>();

            for(LocalizationDto x : list){
                Artist artist = this.repository.findById(x.getArtistId()).get();

                listFinal.add(
                        this.artistMapper.ArtistToArtistDto(artist)
                );
            }
            return listFinal.toString();
        }




    }

    public String findByArt(String art) {

        if (art == null || art.isEmpty())
            return "Sorry art is empty";
        Collection<Artist> list = this.repository.findAllByArteContains(art);
        return list.toString();
    }

    public String findPreviousDate(String name, LocalDate date) {
        if (name == null || date == null)
            return "You most specify name and date";

        if (name == "")
            return "Name cant be empty";

        ArrayList<Artist> listArtist = (ArrayList<Artist>) this.repository.findAllByNameContainingIgnoreCase(name);

        log.info("Artists Found {}", listArtist);

        if (listArtist.size() != 1)
            return "Your artist is not on our db or you need to be more specific";

        Artist artist = listArtist.remove(0);

        Collection<LocalizationDto> dto = this.LocalizationClient.byPreviousDate(
                LocalizationDto.builder()
                        .artistId(artist.getId())
                        .performanceDate(date).build()
        );

        return dto.toString();
    }

    public String findByNextDate(String name, LocalDate date) {
        if (name == null || date == null)
            return "You most specify name and date";

        if (name == "")
            return "Name cant be empty";

        ArrayList<Artist> listArtist = (ArrayList<Artist>) this.repository.findAllByNameContainingIgnoreCase(name);

        log.info("Artists Found {}", listArtist);

        if (listArtist.size() != 1)
            return "Your artist is not on our db or you need to be more specific";

        Artist artist = listArtist.remove(0);

        Collection<LocalizationDto> dto = this.LocalizationClient.byNextDate(
                LocalizationDto.builder()
                        .artistId(artist.getId())
                        .performanceDate(date).build()
        );

        return dto.toString();
    }

    public String byAproval(Boolean aprovalState) {

        return this.repository.findAllByAprovedStateEquals(aprovalState).toString();
    }

    public String changeInformationArtist(String id, String name, String art) {
        Artist artist = this.repository.findById(Integer.parseInt(id)).get();

        log.info("{}", artist);

        if ( !(name == null || name.isEmpty()))
            artist.setName(name);
        if (!(art == null || art.isEmpty()))
            artist.setArte(art);

        this.repository.saveAndFlush(artist);
        return artist.toString();

    }
}
