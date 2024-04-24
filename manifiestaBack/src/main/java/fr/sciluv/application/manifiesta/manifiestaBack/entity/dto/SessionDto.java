package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto;

import java.util.Date;

public class SessionDto {
    private Integer idSession;

    private String password;

    private int songsNumber;

    private int musicalStylesNumber;

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSongsNumber() {
        return songsNumber;
    }

    public void setSongsNumber(int songsNumber) {
        this.songsNumber = songsNumber;
    }

    public int getMusicalStylesNumber() {
        return musicalStylesNumber;
    }

    public void setMusicalStylesNumber(int musicalStylesNumber) {
        this.musicalStylesNumber = musicalStylesNumber;
    }
}
