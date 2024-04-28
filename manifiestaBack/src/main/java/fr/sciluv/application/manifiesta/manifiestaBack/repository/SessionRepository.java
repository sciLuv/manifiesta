package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByQrCode(QRCode qrCode);

    Session findByQrCodeAndPassword(QRCode qrCode, String password);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.hourOfEnd is null")
    Session findCurrentSessionByUser(User user);

    @Query("SELECT s FROM Session s WHERE s.id = :id AND s.hourOfEnd is null AND s.user != :user")
    Session findNotOwnCurrentSessionByIdAndEndHour(int id, User user);



}
