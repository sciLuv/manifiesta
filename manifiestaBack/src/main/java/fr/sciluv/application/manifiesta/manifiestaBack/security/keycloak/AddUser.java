package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AddUser {

    private static final String TOKEN_URL = "http://localhost:8080/admin/realms/manifiesta/users";
    private static final String CONTENT_TYPE = "application/json";

    ApplicationConnexion connexion = new ApplicationConnexion();
    public void addUser() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE);
        headers.add("Authorization", "Bearer " + connexion.getToken());

        String user = "{\n" +
                "    \"username\": \"newugdhfser2\",\n" +
                "    \"enabled\": true,\n" +
                "    \"emailVerified\": false,\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"email\": \"john2.doe@exytuample.com\",\n" +
                "    \"credentials\": [\n" +
                "        {\n" +
                "            \"type\": \"password\",\n" +
                "            \"value\": \"userpassword\",\n" +
                "            \"temporary\": false\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, String.class);

        System.out.println(response.getBody());
    }

    public static void main(String[] args) {
        AddUser keycloak = new AddUser();
        keycloak.addUser();
    }
}
