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

public class AdminToken {

    private String token;
    private String refreshToken;

    KCAttributes kcAttributes = new KCAttributes();


    //constructor with getToken method inside
    public AdminToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", kcAttributes.getAdminClientId());
        map.add("client_secret", kcAttributes.getAdminClientSecret());
        map.add("grant_type", kcAttributes.getGrantTypePassword());
        map.add("username", kcAttributes.getAdminUsername());
        map.add("password", kcAttributes.getAdminPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(kcAttributes.getBaseUrl() + kcAttributes.getTokenUrl(), HttpMethod.POST, request, String.class);

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