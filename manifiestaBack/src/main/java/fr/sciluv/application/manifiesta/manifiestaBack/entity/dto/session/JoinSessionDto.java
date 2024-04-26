package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session;

public class JoinSessionDto {

        private String qrCodeInfo;
        private String password;

        public JoinSessionDto() {
        }

        public JoinSessionDto(String qrCodeInfo, String password) {
            this.qrCodeInfo = qrCodeInfo;
            this.password = password;
        }

        public String getQrCodeInfo() {
            return qrCodeInfo;
        }

        public String getPassword() {
            return password;
        }
}
