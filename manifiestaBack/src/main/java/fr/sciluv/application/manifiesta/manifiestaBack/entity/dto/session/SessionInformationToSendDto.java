package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;

public class SessionInformationToSendDto {
    MusicListDto musics;
    MusicCurrentlyPlayedDto musicCurrentlyPlayed;
    JoinSessionDto joinSessionDto;
    SessionParticipant sessionParticipant;

public SessionInformationToSendDto(MusicListDto musics, MusicCurrentlyPlayedDto musicCurrentlyPlayed, JoinSessionDto joinSessionDto, SessionParticipant sessionParticipant) {
        this.musics = musics;
        this.musicCurrentlyPlayed = musicCurrentlyPlayed;
        this.joinSessionDto = joinSessionDto;
        this.sessionParticipant = sessionParticipant;
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

    public SessionParticipant getSessionParticipant() {
        return sessionParticipant;
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
