package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AddUser {

    private static final String TOKEN_URL = "http://localhost:8080/admin/realms/manifiesta/users";
    private static final String CONTENT_TYPE = "application/json";

    ApplicationConnexion connexion = new ApplicationConnexion();
    public boolean addUser(User user) {



        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", CONTENT_TYPE);
            headers.add("Authorization", "Bearer " + connexion.getToken());

            String userInformations = "{\n" +
                    "    \"username\": \"" + user.getUsername()+ "\",\n" +
                    "    \"enabled\": true,\n" +
                    "    \"emailVerified\": false,\n" +
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

            HttpEntity<String> request = new HttpEntity<>(userInformations, headers);
            ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, String.class);

            return true;


        }   catch (Exception e) {

            return false;
        }

    }
}
