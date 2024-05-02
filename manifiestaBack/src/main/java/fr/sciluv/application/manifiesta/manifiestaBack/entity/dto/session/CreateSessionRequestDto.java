package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;

public class CreateSessionRequestDto {
    private SessionDto sessionDto;
    private UserLoginDto userLoginDto;
    private TokenDto tokenDto;
    private boolean qrCodeGlobal;

    // Getters et Setters
    public SessionDto getSessionDto() {
        return sessionDto;
    }

    public void setSessionDto(SessionDto sessionDto) {
        this.sessionDto = sessionDto;
    }

    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }

    public void setUserLoginDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
    }

    public TokenDto getTokenDto() {
        return tokenDto;
    }

    public void setTokenDto(TokenDto tokenDto) {
        this.tokenDto = tokenDto;
    }

    public boolean isQrCodeGlobal() {
        return qrCodeGlobal;
    }

    public void setQRCodeGlobal(boolean qrCodeGlobal) {
        qrCodeGlobal = qrCodeGlobal;
    }

    @Override
    public String toString() {
        return "CreateSessionRequestDto{" +
                "sessionDto=" + sessionDto +
                ", userLoginDto=" + userLoginDto +
                ", tokenDto=" + tokenDto +
                ", isQRCodeGlobal=" + qrCodeGlobal +
                '}';
    }
}
