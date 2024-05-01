package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.VoteDto;
import fr.sciluv.application.manifiesta.manifiestaBack.service.VoteService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.FindUsersInformationInJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = {"New-Access-Token", "New-Refresh-Token"})
@RestController
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/vote")
    public void createSession(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody VoteDto voteDto)
    {
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();
        voteService.addVote(username, voteDto.getSuggestedMusicId(), voteDto.getQrCode(), voteDto.getPassword());
    }
}