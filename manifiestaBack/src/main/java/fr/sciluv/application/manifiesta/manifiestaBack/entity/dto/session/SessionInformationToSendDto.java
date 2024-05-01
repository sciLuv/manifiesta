package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionParticipantDto;

public class SessionInformationToSendDto {
    MusicListDto musics;
    MusicCurrentlyPlayedDto musicCurrentlyPlayed;
    JoinSessionDto joinSessionDto;
    SessionParticipantDto sessionParticipantDto;

    boolean isOwner;

    int numberParticipants;
    int numberSongs;

    public SessionInformationToSendDto(MusicListDto musics, MusicCurrentlyPlayedDto musicCurrentlyPlayed,
                                       JoinSessionDto joinSessionDto, SessionParticipantDto sessionParticipantDto,
                                       boolean isOwner, int numberParticipants, int numberSongs) {
        this.musics = musics;
        this.musicCurrentlyPlayed = musicCurrentlyPlayed;
        this.joinSessionDto = joinSessionDto;
        this.sessionParticipantDto = sessionParticipantDto;
        this.isOwner = isOwner;
        this.numberParticipants = numberParticipants;
        this.numberSongs = numberSongs;
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

    public SessionParticipantDto getSessionParticipant() {
        return sessionParticipantDto;
    }

    public int getNumberParticipants() {
        return numberParticipants;
    }

    public int getNumberSongs() {
        return numberSongs;
    }

    public boolean isOwner() {
        return isOwner;
    }
    @Override
    public String toString() {
        return "SessionInformationToSendDto{" +
                "musics=" + musics +
                ", musicCurrentlyPlayed=" + musicCurrentlyPlayed +
                ", joinSessionDto=" + joinSessionDto +
                ", sessionParticipantDto=" + sessionParticipantDto +
                '}';
    }
}
