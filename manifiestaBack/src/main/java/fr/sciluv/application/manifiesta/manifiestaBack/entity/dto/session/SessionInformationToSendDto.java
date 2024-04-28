package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;

public class SessionInformationToSendDto {
    MusicListDto musics;
    MusicCurrentlyPlayedDto musicCurrentlyPlayed;
    JoinSessionDto joinSessionDto;

public SessionInformationToSendDto(MusicListDto musics, MusicCurrentlyPlayedDto musicCurrentlyPlayed, JoinSessionDto joinSessionDto) {
        this.musics = musics;
        this.musicCurrentlyPlayed = musicCurrentlyPlayed;
        this.joinSessionDto = joinSessionDto;
    }

    public MusicListDto getMusics() {
        return musics;
    }

    public MusicCurrentlyPlayedDto getMusicCurrentlyPlayed() {
        return musicCurrentlyPlayed;
    }

    public JoinSessionDto getJoinSessionDto() {
        return joinSessionDto;
    }

    @Override
    public String toString() {
        return "SessionInformationToSendDto{" +
                "musics=" + musics +
                ", musicCurrentlyPlayed=" + musicCurrentlyPlayed +
                ", joinSessionDto=" + joinSessionDto +
                '}';
    }
}
