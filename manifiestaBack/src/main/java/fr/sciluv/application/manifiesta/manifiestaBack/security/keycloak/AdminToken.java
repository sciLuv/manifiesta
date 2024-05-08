package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sciluv.application.manifiesta.manifiestaBack.config.EnvAttributes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AdminToken {

    private String token;
    private String refreshToken;

    EnvAttributes envAttributes = new EnvAttributes();


    //constructor with getToken method inside
    public AdminToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", envAttributes.getAdminClientId());
        map.add("client_secret", envAttributes.getAdminClientSecret());
        map.add("grant_type", envAttributes.getGrantTypePassword());
        map.add("username", envAttributes.getAdminUsername());
        map.add("password", envAttributes.getAdminPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(envAttributes.getBaseUrl() + envAttributes.getTokenUrl(), HttpMethod.POST, request, String.class);

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

    public static void main(String[] args) {
        AdminToken app = new AdminToken();
        System.out.println(app.getToken());
    }

}