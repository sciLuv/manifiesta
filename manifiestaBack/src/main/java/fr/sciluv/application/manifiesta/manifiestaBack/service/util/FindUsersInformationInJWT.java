package fr.sciluv.application.manifiesta.manifiestaBack.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;
import java.util.Map;

public class FindUsersInformationInJWT {

    private String token;
    private DecodedJWT jwt;

    public String findUserNameinJWT(){
        return jwt.getClaim("preferred_username").asString();
    }


    public String findUserRolesInJWT(){
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access").asMap();

        if (resourceAccess != null && resourceAccess.containsKey("manifiesta")) {
            Map<String, Object> manifiestaAccess = (Map<String, Object>) resourceAccess.get("manifiesta");
            List<String> roles = (List<String>) manifiestaAccess.get("roles");

            for (String role : roles) {
                if (role.equals("client_user")) {
                    return "user";
                }
            }
            return "guest";
        }
        return null;
    }

    public FindUsersInformationInJWT(String token) {
        System.out.println(token.substring("Bearer ".length()));
        this.token = token.substring("Bearer ".length());
        this.jwt = JWT.decode(this.token);
    }
}
