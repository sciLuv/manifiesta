package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;

public class SessionInformationToSendDto {
    MusicListDto musics;
    MusicCurrentlyPlayedDto musicCurrentlyPlayed;

public SessionInformationToSendDto(MusicListDto musics, MusicCurrentlyPlayedDto musicCurrentlyPlayed) {
        this.musics = musics;
        this.musicCurrentlyPlayed = musicCurrentlyPlayed;
    }

    public MusicListDto getMusics() {
        return musics;
    }

    public MusicCurrentlyPlayedDto getMusicCurrentlyPlayed() {
        return musicCurrentlyPlayed;
    }

    @Override
    public String toString() {
        return "SessionInformationToSendDto{" +
                "musics=" + musics.toString() +
                ", musicCurrentlyPlayed=" + musicCurrentlyPlayed.toString() +
                '}';
    }
}
