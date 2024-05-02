package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "qrCode", fetch = FetchType.EAGER)
    private Set<Session> sessions;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    // Constructeurs, getters et setters

    public QRCode() {
    }

    public QRCode(String qrCodeInfo, User user) {
        this.qrCodeInfo = qrCodeInfo;
        this.isGlobal = false;
        this.beginDate = LocalDateTime.now();
        this.endDate = null;
        this.user = user;
    }

    public QRCode(String qrCodeInfo, User user, boolean isGlobal) {
        this.qrCodeInfo = qrCodeInfo;
        this.isGlobal = isGlobal;
        this.beginDate = LocalDateTime.now();
        this.endDate = null;
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
                ", sessions=" + sessions +
                ", user=" + user +
                '}';
    }

    public String getQrCodeInfo() {
        return qrCodeInfo;
    }

    public Boolean getGlobal() {
        return isGlobal;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public User getUser() {
        return user;
    }
}

