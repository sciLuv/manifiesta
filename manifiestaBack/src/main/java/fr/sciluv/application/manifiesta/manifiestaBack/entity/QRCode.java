package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qr_code")
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQRCode;

    private String qrCodeInfo;
    private Boolean isGlobal;
    private Date beginDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "idSession")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    // Constructeurs, getters et setters
}

