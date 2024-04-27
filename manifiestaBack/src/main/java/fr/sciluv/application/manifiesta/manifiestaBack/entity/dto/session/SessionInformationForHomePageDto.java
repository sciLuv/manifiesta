package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

public class SessionInformationForHomePageDto {
    String QRCode;
    String password;
    int numberOfParticipants;
    int numberOfPollturns;

    public SessionInformationForHomePageDto(String QRCode, String password, int numberOfParticipants, int numberOfPollturns) {
        this.QRCode = QRCode;
        this.password = password;
        this.numberOfParticipants = numberOfParticipants;
        this.numberOfPollturns = numberOfPollturns;
    }

    public String getQRCode() {
        return QRCode;
    }
    public String getPassword() {
        return password;
    }
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }
    public int getNumberOfPollturns() {
        return numberOfPollturns;
    }
}
