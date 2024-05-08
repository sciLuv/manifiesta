package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user;

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

public class UserRefreshToken {
    private String token;
    private String refreshToken;

    EnvAttributes envAttributes = new EnvAttributes();


    //constructor with getToken method inside
    public UserRefreshToken(String refreshToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", envAttributes.getManifiestaClientId());
        map.add("client_secret", envAttributes.getManifiestaClientSecret());
        map.add("grant_type", envAttributes.getGrantTypeRefreshToken());
        map.add("refresh_token", refreshToken);

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

//    public static void main(String[] args) {
//        UserRefreshToken userRefreshToken = new UserRefreshToken("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjNWU2ZGE2My01YzRlLTQ1NTAtYmI5YS01NWFlZGNlOWRhY2YifQ.eyJleHAiOjE3MTM2NTUxOTEsImlhdCI6MTcxMzY1MzM5MSwianRpIjoiNzIwMjNjYTAtYjgyOS00NWY2LWJkNWEtNmM0MjI5ZDEzNGUyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9tYW5pZmllc3RhIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9tYW5pZmllc3RhIiwic3ViIjoiMGQ4Y2QxZTktZDdmYS00YTcwLTljNGEtZmY1N2RiMWMzYzMyIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6Im1hbmlmaWVzdGEiLCJzZXNzaW9uX3N0YXRlIjoiNTE3MzA2N2QtNWQ0OC00Y2ZhLTg5NWUtZDU0ZTEzNTU2MGY5Iiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiNTE3MzA2N2QtNWQ0OC00Y2ZhLTg5NWUtZDU0ZTEzNTU2MGY5In0.jmbeOlKFfsDANN7oclzzb6VAU1zsSOAj-vosUf9g8GRGGC-cyU7WqIIA3KbOeVK2FVEqCaw2SW3KqD9rzPEDBA");
//        System.out.println(userRefreshToken.getToken());
//        System.out.println(userRefreshToken.getRefreshToken());
//    }
}
