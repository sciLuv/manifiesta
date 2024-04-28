package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByNameAndArtistAndAlbum(String name, String artist, String album);

    Music findBySuggestedMusics(SuggestedMusic suggestedMusic);

}
