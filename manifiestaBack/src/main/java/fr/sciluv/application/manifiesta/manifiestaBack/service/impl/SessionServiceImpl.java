package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.MusicService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
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
    PollTurnRepository pollTurnRepository;
    @Autowired
    UserService userService;

    @Autowired
    QRCodeRepository qRCodeRepository;

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    SuggestedMusicRepository suggestedMusicRepository;

    @Autowired
    MusicStreamingServiceInformationRepository musicStreamingServiceInformationRepository;

    @Autowired
    StreamingServiceRepository streamingServiceRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    MusicService musicService;

    public Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto, QRCode qrCode) {

        User user = userService.getUser(userLoginDto.getUsername());

        Session newSession = new Session(
                -1,
                LocalDateTime.now(),
                null,
                sessionDto.getPassword(),
                sessionDto.getSongsNumber(),
                sessionDto.getMusicalStylesNumber(),
                user,
                qrCode);
        // Set properties for the session
        return sessionRepository.save(newSession);
    }

    @Transactional
    public SessionInformationToSendDto joinSession(JoinSessionDto joinSessionDto) {
        Optional<QRCode> qrCode = qRCodeRepository.findByQrCodeInfo(joinSessionDto.getQrCodeInfo());
        if (qrCode.isPresent()) {

            List<Music> musics = new ArrayList<>();
            List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
            List<MusicDto> musicDtos = new ArrayList<>();
            StreamingService streamingService = streamingServiceRepository.findByName("Spotify");

            QRCode qrCode1 = qrCode.get();
            Session session = sessionRepository.findByQrCode(qrCode1);
            PollTurn pollTurn = pollTurnRepository.findBySession(session);
            Set<SuggestedMusic> suggestedMusics = pollTurn.getSuggestedMusics();
            suggestedMusics.forEach(suggestedMusic -> {
               musics.add(musicRepository.findBySuggestedMusics(suggestedMusic));
            });
            musics.forEach(music -> {
                musicStreamingServiceInformations.add(musicStreamingServiceInformationRepository.findByMusicAndStreamingService(music, streamingService));

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
             Token token = tokenRepository.findMostRecentNonRefreshToken();
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

}
