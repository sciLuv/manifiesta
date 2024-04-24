package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto;

public class CreateSessionRequestDto {
    private SessionDto sessionDto;
    private UserLoginDto userLoginDto;
    private TokenDto tokenDto;

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
}
