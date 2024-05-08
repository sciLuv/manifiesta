package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationForHomePageDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantNameAndIsGuestDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantNameAndIsGuestListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SpotifyApiCallManager spotifyApiCallManager;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    UserService userService;
    @Autowired
    MusicService musicService;
    @Autowired
    SessionParticipantService sessionParticipantService;
    @Autowired
    PollTurnService pollTurnService;
    @Autowired
    QRCodeService qrCodeService;
    @Autowired
    MusicStreamingServiceInformationService musicStreamingServiceInformationService;
    @Autowired
    StreamingServiceService streamingServiceService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private RegularSpotifyApiCallForSessionUpdate regularSpotifyApiCallForSessionUpdate;



    public Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto, boolean isQRCodeGlobal) {
        User user = userService.getUser(userLoginDto.getUsername());
        QRCode code;
        System.out.println("est ce que le QRCode est global ? " + isQRCodeGlobal);
        if (isQRCodeGlobal) {
            code = qrCodeService.findQRCodeByUserAndIsGlobal(user, true);
        } else {
            code = qrCodeService.generateQRCode(userLoginDto);
        }

        Session newSession = new Session(
                -1,
                LocalDateTime.now(),
                null,
                sessionDto.getPassword(),
                sessionDto.getSongsNumber(),
                sessionDto.getMusicalStylesNumber(),
                user,
                code
        );
        return sessionRepository.save(newSession);
    }


    @Transactional
    public SessionInformationToSendDto joinSession(JoinSessionDto joinSessionDto, SessionParticipantDto sessionParticipant, String username){
        System.out.println("entrer dans joinSession de SessionServiceImpl");
        QRCode qrCode = qrCodeService.findQRCodeByInfo(joinSessionDto.getQrCodeInfo());
        if (qrCode != null) {
            System.out.println("qrCode different de null dans joinSession de SessionServiceImpl");
            List<Music> musics = new ArrayList<>();
            List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
            List<MusicDto> musicDtos = new ArrayList<>();
            StreamingService streamingService = streamingServiceService.findByName("Spotify");

            QRCode qrCode1 = qrCode;
            Session session = sessionRepository.findByQrCodeAndPassword(qrCode1, joinSessionDto.getPassword());
            boolean isSessionEnded = isSessionEnded(session);

            User user = userService.getUser(username);
            boolean isSessionLinkedToUser = sessionRepository.isSessionLinkedToUser(user, session);

            int participants = sessionParticipantService.numberOfParticipantsInSession(session);
            int pollTurns = pollTurnService.getPollTurnsBySession(session);

            PollTurn pollTurn = pollTurnService.findFirstBySessionOrderByNumberTurnDesc(session);
            List<SuggestedMusic> suggestedMusics = pollTurn.getSuggestedMusics();
            suggestedMusics.forEach(suggestedMusic -> {
               musics.add(musicService.findMusicBySuggestedMusic(suggestedMusic));
            });
            musics.forEach(music -> {
                musicStreamingServiceInformations.add(musicStreamingServiceInformationService.findByMusicAndStreamingService(music, streamingService));

            });
            for (int i = 0; i < musics.size(); i++) {
                musicDtos.add(new MusicDto(
                        musics.get(i).getName(),
                        musics.get(i).getArtist(),
                        musics.get(i).getAlbum(),
                        musicStreamingServiceInformations.get(i).getUrl_link(),
                        musicStreamingServiceInformations.get(i).getUrl_img(),
                        musicStreamingServiceInformations.get(i).getDuration(),
                        suggestedMusics.get(i).getIdSuggestMusic()

                ));
            }

             MusicListDto musicListDto = new MusicListDto(musicDtos);
             Token token = tokenService.findMostRecentNonRefreshToken(session.getUser());
             System.out.println(token.getToken());
             MusicCurrentlyPlayedDto musicCurrentlyPlayedDto =  musicService.musicCurrentlyPlayingToJSON(token);

            System.out.println(musicCurrentlyPlayedDto.toString());
            SessionInformationToSendDto sessionInformationToSendDto = new SessionInformationToSendDto(
                    musicListDto,
                    musicCurrentlyPlayedDto,
                    joinSessionDto,
                    sessionParticipant,
                    isSessionLinkedToUser,
                    participants,
                    pollTurns,
                    isSessionEnded
            );

             return sessionInformationToSendDto;
        }
        return null;
    }

    public Session addQrCodeToSession(Session session, QRCode qrCode) {
        session.setQrCode(qrCode);
        return sessionRepository.save(session);
    }

    public Session findSessionByQrCode(QRCode qrCode){
        Session session = sessionRepository.findByQrCodeAndHourOfEnd(qrCode);
        return session;
    }


    @Override
    public SessionInformationForHomePageDto findSessionInformationByQrCode(String qrCode) {
        return null;
    }

    @Override
    public void createParticipantForSessionOwner(User user, Session session) {
        sessionParticipantService.createParticipantForSessionOwner(user, session);
    }

    @Override
    public SessionParticipantDto createParticipantForSession(String username, String qrCode, String role) {
        return sessionParticipantService.createParticipantForSession(username, qrCode, role);
    }

    @Override
    public SessionInformationForHomePageDto findOwnAndNotEndSessionInformation(String username) {
        User user = userService.getUser(username);
        Session session = sessionRepository.findCurrentSessionByUser(user);
        if(session == null) return null;
        QRCode qrCode = session.getQrCode();
        int participants = sessionParticipantService.numberOfParticipantsInSession(session);
        int pollTurns = pollTurnService.getPollTurnsBySession(session);
        musicService.musicCurrentlyPlayingToJSON(tokenService.findMostRecentNonRefreshToken(user));

        return new SessionInformationForHomePageDto(qrCode.getQrCodeInfo(), session.getPassword(), participants, pollTurns, session.getUser().getUsername());
    }

    @Override
    public List<SessionInformationForHomePageDto> findParticipantNotEndSessionInformation(String username) {
        User user = userService.getUser(username);
        if (user == null) {
            return null; // Retourne null ou lance une exception si aucun utilisateur n'est trouvé
        }

        Set<SessionParticipant> sessionParticipants = sessionParticipantService.findAllSessionParticipantByUser(user);
        if (sessionParticipants == null || sessionParticipants.isEmpty()) {
            return null; // Retourne null si aucun participant n'est trouvé pour l'utilisateur
        }

        List<Session> sessions = new ArrayList<>();
        for (SessionParticipant participant : sessionParticipants) {
            Session session = participant.getSession();
            if (session != null) {
                Session activeSession = sessionRepository.findActiveSessionBySessionParticipant(participant, user);
                if (activeSession != null) {
                    sessions.add(activeSession);
                }
            }
        }

        if (sessions.isEmpty()) {
            return null; // Retourne null si aucune session active n'est trouvée
        }

        List<SessionInformationForHomePageDto> sessionInformationForHomePageDtos = new ArrayList<>();
        for (Session session : sessions) {
            QRCode qrCode = session.getQrCode();
            int participants = sessionParticipantService.numberOfParticipantsInSession(session);
            int pollTurns = pollTurnService.getPollTurnsBySession(session);
            // Supposons que vous souhaitez également inclure des informations sur la musique actuellement jouée
            // MusicCurrentlyPlayedDto currentlyPlayed = musicService.musicCurrentlyPlayingToJSON(tokenService.findMostRecentNonRefreshToken(user));
            String usernameSession = session.getUser() != null ? session.getUser().getUsername() : "Inconnu"; // Gestion si l'utilisateur de la session est null

            sessionInformationForHomePageDtos.add(new SessionInformationForHomePageDto(
                    qrCode != null ? qrCode.getQrCodeInfo() : "QRCode inconnu",
                    session.getPassword(),
                    participants,
                    pollTurns,
                    usernameSession
            ));
        }

        return sessionInformationForHomePageDtos.isEmpty() ? null : sessionInformationForHomePageDtos;
    }


    @Override
    public boolean isSessionLinkedToUser(User user, Session session) {
        return isSessionLinkedToUser(user, session);
    }

    @Override
    public void endSession(String username, String qrCodeInfo) {
        User user = userService.getUser(username);
        QRCode qrCode = qrCodeService.findQRCodeByInfo(qrCodeInfo);
        Session session = sessionRepository.findByQrCodeAndUser(qrCode, user);
        if (session != null) {
            session.setHourOfEnd();
            sessionRepository.save(session);
            Set<SessionParticipant> sessionParticipants = sessionParticipantService.findAllSessionParticipantBySession(session);
            for (SessionParticipant participant : sessionParticipants) {
                participant.setHourOfLeave(LocalDateTime.now());
                sessionParticipantService.saveSessionParticipant(participant);
            }
            spotifyApiCallManager.endApiCall(session.getIdSession());
        }
    }

    @Override
    public boolean isSessionEnded(Session session) {
        if (session != null) {
            return session.getHourOfEnd() != null;
        }
        return false;
    }

    @Override
    public String leaveSession(String username, String qrCodeInfo) {
        User user = userService.getUser(username);
        QRCode qrCode = qrCodeService.findQRCodeByInfo(qrCodeInfo);
        Session session = sessionRepository.findByQrCode(qrCode);
        if (session != null) {
            SessionParticipant participant = sessionParticipantService.isUserAlreadyParticipant(username, session);
            if (participant != null) {
                participant.setHourOfLeave(LocalDateTime.now());
                sessionParticipantService.saveSessionParticipant(participant);
                return "session leaved";
            }
        }
        return "problem with leaving session";
    }

    @Override
    public SessionParticipantNameAndIsGuestListDto getAllParticipantsOfSession(String qrCodeInfo) {
        QRCode qrCode = qrCodeService.findQRCodeByInfo(qrCodeInfo);
        Session session = findSessionByQrCode(qrCode);
        Set<SessionParticipant> sessionParticipants = sessionParticipantService.findAllSessionParticipantBySession(session);
        List<SessionParticipantNameAndIsGuestDto> sessionParticipantNameAndIsGuestListDto = new ArrayList<>();
        for (SessionParticipant participant : sessionParticipants) {
            if(participant.getGuest() == true){
                sessionParticipantNameAndIsGuestListDto.add(
                        new SessionParticipantNameAndIsGuestDto(
                                participant.getNameGuest(),
                                participant.getGuest()
                        )
                );
            }
            else{
                sessionParticipantNameAndIsGuestListDto.add(
                        new SessionParticipantNameAndIsGuestDto(
                                participant.getUser().getUsername(),
                                participant.getGuest()
                        )
                );
            }

        }
        SessionParticipantNameAndIsGuestListDto sessionParticipantNameAndIsGuestListDto1 = new SessionParticipantNameAndIsGuestListDto(sessionParticipantNameAndIsGuestListDto);
        return sessionParticipantNameAndIsGuestListDto1;
    }

}
