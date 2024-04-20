package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.security.TokenProcessingService;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.AdminToken;

public class AddUserToKC{
    AdminToken connexion = new AdminToken();

    public String addingUserToKC(User user){
        CreateUser createUser = new CreateUser();
        if (createUser.addUser(user)){
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
            return "User not created";
        }
    }

}
