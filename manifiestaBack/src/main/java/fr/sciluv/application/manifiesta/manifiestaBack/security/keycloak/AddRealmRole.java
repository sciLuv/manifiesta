//package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//public class AddRealmRole {
//    // Add a realm role to a user in Keycloak
//    public void addRealmRole(String userId, String roleName) {
//        // Create a new ApplicationConnexion object
//        ApplicationConnexion connexion = new ApplicationConnexion();
//
//        // Create a new RestTemplate object
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Create a new HttpHeaders object
//        HttpHeaders headers = new HttpHeaders();
//
//        // Add the Content-Type header to the HttpHeaders object
//        headers.add("Content-Type", "application/json");
//
//        // Add the Authorization header to the HttpHeaders object
//        headers.add("Authorization", "Bearer " + connexion.getToken());
//
//        // Create a new HttpEntity object
//        HttpEntity<String> request = new HttpEntity<>("", headers);
//
//        // Send a request to Keycloak to add the realm role to the user
//        ResponseEntity<String> response = restTemplate.exchange("http://localhost
//}
