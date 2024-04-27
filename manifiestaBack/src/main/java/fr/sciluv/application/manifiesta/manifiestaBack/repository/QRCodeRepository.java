package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    Optional<QRCode> findByQrCodeInfo(String qrCodeInfo);

    QRCode findBySessions(Session session);
}
