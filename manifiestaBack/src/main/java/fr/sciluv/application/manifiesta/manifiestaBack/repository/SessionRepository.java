package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByQrCode(QRCode qrCode);

    Session findByQrCodeAndUser(QRCode qrCode, User user);

    Session findByQrCodeAndPassword(QRCode qrCode, String password);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.hourOfEnd is null")
    Session findCurrentSessionByUser(User user);

    @Query("SELECT s FROM Session s WHERE s.id = :id AND s.hourOfEnd is null AND s.user != :user")
    Session findNotOwnCurrentSessionByIdAndEndHour(int id, User user);

    @Query("SELECT sp.session FROM SessionParticipant sp WHERE sp = :sessionParticipant AND sp.session.hourOfEnd is null AND sp.hourOfLeave is null AND sp.session.user != :user")
    Session findActiveSessionBySessionParticipant(SessionParticipant sessionParticipant, User user);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Session s WHERE s.user = :user AND s = :session")
    boolean isSessionLinkedToUser(User user, Session session);



}
