package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.AccountCreation;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserDto;
import fr.sciluv.application.manifiesta.manifiestaBack.security.TokenProcessingService;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.AdminToken;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.UserToken;

public class AddUserToKC{
    AdminToken connexion = new AdminToken();

    public String addingUserToKC(UserDto user){
        CreateUser createUser = new CreateUser();
        String creatingUser = createUser.addUser(user);
        System.out.println(creatingUser);
        if ( creatingUser == "User created"){
            UserToken userToken = new UserToken(user.getUsername(), user.getPassword());
            if (userToken.getToken() != null){
                TokenProcessingService tokenProcessingService = new TokenProcessingService();
                String userId = tokenProcessingService.extractTokenData(userToken.getToken());
                AddRoleToUser addRoleToUser = new AddRoleToUser(userId);
                if (addRoleToUser.getResponse()){
                    return "User created";
                } else {
                    return "User created but role not added";
                }
            } else {
                return "User created but token not generated";
            }
        } else {
            return creatingUser;
        }
    }

}
