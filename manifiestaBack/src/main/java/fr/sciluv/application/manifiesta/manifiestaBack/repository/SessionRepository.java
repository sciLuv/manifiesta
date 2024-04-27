package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByQrCode(QRCode qrCode);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.hourOfEnd is null")
    Session findCurrentSessionByUser(User user);


}
