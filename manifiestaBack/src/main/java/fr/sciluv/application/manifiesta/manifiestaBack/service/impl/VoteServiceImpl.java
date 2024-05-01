package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SuggestedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.VoteRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserService userService;

    @Autowired
    @Lazy
    SessionService sessionService;

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    SessionParticipantService sessionParticipantService;

    @Autowired
    PollTurnService pollTurnService;

    @Autowired
    SuggestedMusicService suggestedMusicService;

    @Override
    public int countBySuggestedMusic(SuggestedMusic suggestedMusic) {
        return voteRepository.countBySuggestedMusic(suggestedMusic);
    }

    @Override
    public void addVote(String username, int suggestedMusicId, String qrCode, String password) {
        User user = userService.getUser(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        } else {
            QRCode qrCode1 = qrCodeService.findQRCodeByInfo(qrCode);
            Session session = sessionService.findSessionByQrCode(qrCode1);
            if (session == null) {
                throw new IllegalArgumentException("Session not found");
            } else {
                SessionParticipant sessionParticipant = sessionParticipantService.isUserAlreadyParticipant(username, session);
                if (sessionParticipant == null) {
                    throw new IllegalArgumentException("User not participant");
                } else {
                    SuggestedMusic suggestedMusic = suggestedMusicService.findById(suggestedMusicId).orElse(null);
                    if (suggestedMusic == null) {
                        throw new IllegalArgumentException("Suggested music not found");
                    } else {

                        //va chercher le dernier tour de vote
                        PollTurn pollTurn = pollTurnService.findFirstBySessionOrderByNumberTurnDesc(session);
                        List<SuggestedMusic> suggestedMusics = suggestedMusicService.findByPollTurn(pollTurn);

                        boolean voteUpdated = false;  // Variable pour suivre si un vote a été mis à jour

                        for (SuggestedMusic suggestedMusic1 : suggestedMusics) {
                            Vote searchVote = voteRepository.findBySessionParticipantAndSuggestedMusic(sessionParticipant, suggestedMusic1);
                            if (searchVote != null) {
                                searchVote.setSuggestedMusic(suggestedMusic);
                                voteRepository.save(searchVote);
                                voteUpdated = true;  // Mettre à jour le flag
                                break;  // Arrêter la boucle si un vote est mis à jour
                            }
                        }

                        if (!voteUpdated) {  // Vérifier si aucun vote n'a été mis à jour
                            voteRepository.save(new Vote(sessionParticipant, suggestedMusic));
                        }
                    }
                }
            }
        }
    }
}
