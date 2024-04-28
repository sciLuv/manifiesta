package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationForHomePageDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SessionServiceImpl implements SessionService {

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



    public Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto) {
// generate QRCode
        QRCode code = qrCodeService.generateQRCode(userLoginDto);
        User user = userService.getUser(userLoginDto.getUsername());

        Session newSession = new Session(
                -1,
                LocalDateTime.now(),
                null,
                sessionDto.getPassword(),
                sessionDto.getSongsNumber(),
                sessionDto.getMusicalStylesNumber(),
                user,
                code);
        // Set properties for the session
        return sessionRepository.save(newSession);
    }

    @Transactional
    public SessionInformationToSendDto joinSession(JoinSessionDto joinSessionDto) {
        QRCode qrCode = qrCodeService.findQRCodeByInfo(joinSessionDto.getQrCodeInfo());
        if (qrCode != null) {

            List<Music> musics = new ArrayList<>();
            List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
            List<MusicDto> musicDtos = new ArrayList<>();
            StreamingService streamingService = streamingServiceService.findByName("Spotify");

            QRCode qrCode1 = qrCode;
            Session session = sessionRepository.findByQrCodeAndPassword(qrCode1, joinSessionDto.getPassword());
            PollTurn pollTurn = pollTurnService.findPollTurnBySession(session);
            Set<SuggestedMusic> suggestedMusics = pollTurn.getSuggestedMusics();
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
                        musicStreamingServiceInformations.get(i).getDuration()

                ));
            }

             MusicListDto musicListDto = new MusicListDto(musicDtos);
             Token token = tokenService.findMostRecentNonRefreshToken(session.getUser());
             System.out.println(token.getToken());
             MusicCurrentlyPlayedDto musicCurrentlyPlayedDto =  musicService.musicCurrentlyPlayingToJSON(token);

            System.out.println(musicCurrentlyPlayedDto.toString());
            SessionInformationToSendDto sessionInformationToSendDto = new SessionInformationToSendDto(
                    musicListDto,
                    musicCurrentlyPlayedDto
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
        return sessionRepository.findByQrCode(qrCode);
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
    public void createParticipantForSession(String username, String qrCode, String role) {
        sessionParticipantService.createParticipantForSession(username, qrCode, role);
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
        Set<SessionParticipant> sessionParticipant = sessionParticipantService.findAllSessionParticipantByUser(user);
        List<Session> sessions = new ArrayList<>();
        sessionParticipant.forEach(participant -> {
            int sessionId = participant.getSession().getIdSession();
            Session session = sessionRepository.findNotOwnCurrentSessionByIdAndEndHour(sessionId, user);
            if(session != null) sessions.add(session);
        });
        if(sessions.size() == 0) return null;


        List <SessionInformationForHomePageDto> sessionInformationForHomePageDtos = new ArrayList<>();
        sessions.forEach(session -> {
            QRCode qrCode = session.getQrCode();
            int participants = sessionParticipantService.numberOfParticipantsInSession(session);
            int pollTurns = pollTurnService.getPollTurnsBySession(session);
//            musicService.musicCurrentlyPlayingToJSON(tokenService.findMostRecentNonRefreshToken(user));
            sessionInformationForHomePageDtos.add(new SessionInformationForHomePageDto(qrCode.getQrCodeInfo(), session.getPassword(), participants, pollTurns, session.getUser().getUsername()));
        });
        if(sessionInformationForHomePageDtos.size() == 0) return null;
        return sessionInformationForHomePageDtos;
    }

}
