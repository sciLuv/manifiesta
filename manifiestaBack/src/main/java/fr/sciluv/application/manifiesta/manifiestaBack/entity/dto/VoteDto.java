package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto;

public class VoteDto {
    private int suggestedMusicId;
    private String qrCode;

    private String password;

    public VoteDto( int suggestedMusicId, String qrCode, String password) {
        this.suggestedMusicId = suggestedMusicId;
        this.qrCode = qrCode;
        this.password = password;
    }

    public int getSuggestedMusicId() {
        return suggestedMusicId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getPassword() {
        return password;
    }
}
