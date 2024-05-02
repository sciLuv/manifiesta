package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant;

public class SessionParticipantDto {
    private String username;

    //constructeurs
    public SessionParticipantDto() {
    }

    public SessionParticipantDto(String username) {
        this.username = username;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

}
