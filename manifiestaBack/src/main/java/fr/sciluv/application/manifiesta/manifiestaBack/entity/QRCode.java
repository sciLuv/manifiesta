package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "qr_code")
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQRCode;

    private String qrCodeInfo;
    private Boolean isGlobal;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "idSession")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    // Constructeurs, getters et setters

    public QRCode(String qrCodeInfo, Session session, User user) {
        this.qrCodeInfo = qrCodeInfo;
        this.isGlobal = false;
        this.beginDate = LocalDateTime.now();
        this.endDate = null;
        this.session = session;
        this.user = user;
    }

    @Override
    public String toString() {
        return "QRCode{" +
                "idQRCode=" + idQRCode +
                ", qrCodeInfo='" + qrCodeInfo + '\'' +
                ", isGlobal=" + isGlobal +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", session=" + session +
                ", user=" + user +
                '}';
    }

    public String getQrCodeInfo() {
        return qrCodeInfo;
    }

    public Boolean getGlobal() {
        return isGlobal;
    }
}

