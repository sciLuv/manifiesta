package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant;

import java.util.List;

public class SessionParticipantNameAndIsGuestListDto {
    List<SessionParticipantNameAndIsGuestDto> sessionParticipantNameAndIsGuestDtos;

    //constructeurs
    public SessionParticipantNameAndIsGuestListDto() {
    }

    public SessionParticipantNameAndIsGuestListDto(List<SessionParticipantNameAndIsGuestDto> sessionParticipantNameAndIsGuestDtos) {
        this.sessionParticipantNameAndIsGuestDtos = sessionParticipantNameAndIsGuestDtos;
    }

    // Getters
    public List<SessionParticipantNameAndIsGuestDto> getSessionParticipantNameAndIsGuestDtos() {
        return sessionParticipantNameAndIsGuestDtos;
    }
}
