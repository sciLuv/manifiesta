package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user;

import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.AdminToken;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.KCAttributes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AddRoleToUser {

    private boolean response;

    public AddRoleToUser(String userId) {
        // Create a new AdminToken object
        AdminToken connexion = new AdminToken();

        KCAttributes kcAttributes = new KCAttributes();

        // Create a new RestTemplate object
        RestTemplate restTemplate = new RestTemplate();

        // Create a new HttpHeaders object
        HttpHeaders headers = new HttpHeaders();

        // Add the Content-Type header to the HttpHeaders object
        headers.add("Content-Type", "application/json");

        // Add the Authorization header to the HttpHeaders object
        headers.add("Authorization", "Bearer " + connexion.getToken());


        String roleInformations = "[\n" +
                "        {\n" +
                "            \"id\": \"" + kcAttributes.getUserRoleId() + "\",\n" +
                "            \"name\": \"client_user\",\n" +
                "            \"description\": \"\",\n" +
                "            \"composite\": true,\n" +
                "            \"clientRole\": true,\n" +
                "            \"containerId\": \"" + kcAttributes.getManifiestaNumberId() + "\",\n" +
                "            \"attributes\": {}\n" +
                "        }\n" +
                "    ]";


        // Create a new HttpEntity object
        HttpEntity<String> request = new HttpEntity<>(roleInformations, headers);

        // Send a request to Keycloak to add the realm role to the user
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/admin/realms/manifiesta/users/" +
                        userId + "/role-mappings/clients/" + kcAttributes.getManifiestaNumberId(),
                HttpMethod.POST, request, String.class
        );

        // Print the response
        System.out.println(response.getBody());
        // print the http status code
        System.out.println(response.getStatusCode());
        //output the response
        if(response.getStatusCode().is2xxSuccessful()){
            this.response = true;
        }else{
            this.response = false;
        }

    }

    public boolean getResponse() {
        return response;
    }

    public static void main(String[] args) {
        AddRoleToUser addRole = new AddRoleToUser("0d8cd1e9-d7fa-4a70-9c4a-ff57db1c3c32");
    }

}
