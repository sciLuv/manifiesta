package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.AccountCreation;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserDto;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.AdminToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CreateUser {

    private static final String TOKEN_URL = "http://localhost:8080/admin/realms/manifiesta/users";

    AdminToken connexion = new AdminToken();
    public boolean addUser(UserDto user) {



        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", "Bearer " + connexion.getToken());

            String userInformations = "{\n" +
                    "    \"username\": \"" + user.getUsername()+ "\",\n" +
                    "    \"enabled\": true,\n" +
                    "    \"emailVerified\": true,\n" +
                    "    \"firstName\": \"user\",\n" +
                    "    \"lastName\": \"user\",\n" +
                    "    \"email\": \"" + user.getMail() +"\",\n" +
                    "    \"credentials\": [\n" +
                    "        {\n" +
                    "            \"type\": \"password\",\n" +
                    "            \"value\": \"" + user.getPassword() + "\",\n" +
                    "            \"temporary\": false\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            System.out.println(userInformations);
            HttpEntity<String> request = new HttpEntity<>(userInformations, headers);
            ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, String.class);

            System.out.println(response.getBody());
            return true;


        }   catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}
