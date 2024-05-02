package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant;

public class SessionParticipantNameAndIsGuestDto {
    String username;
    boolean isGuest;

    public SessionParticipantNameAndIsGuestDto(String username, boolean isGuest) {
        this.username = username;
        this.isGuest = isGuest;
    }

    public String getUsername() {
        return username;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
