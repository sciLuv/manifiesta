package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ApplicationConnexion {
    private static final String TOKEN_URL = "http://localhost:8080/realms/manifiesta/protocol/openid-connect/token";
    private static final String CLIENT_ID = "admin-cli";
    private static final String CLIENT_SECRET = "XqMiZuOyAiPFcQfMbUAkvlW11wGF020f";
    private static final String GRANT_TYPE = "password";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private String token;
    private String refreshToken;

    // Sends a request to ApplicationConnexion to retrieve the token


    //constructor with getToken method inside
    public ApplicationConnexion() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", CLIENT_ID);
        map.add("client_secret", CLIENT_SECRET);
        map.add("grant_type", GRANT_TYPE);
        map.add("username", USERNAME);
        map.add("password", PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, String.class);

        //sout response.getBody() to see the response
        System.out.println(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(response.getBody());
            this.token = jsonNode.get("access_token").asText();
            this.refreshToken = jsonNode.get("refresh_token").asText();
        } catch (Exception e) {
            this.token = null;
            this.refreshToken = null;
        }
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

//    public static void main(String[] args) {
//        ApplicationConnexion keycloak = new ApplicationConnexion();
//        System.out.println(keycloak.getToken());
//        System.out.println(keycloak.getRefreshToken());
//    }
}