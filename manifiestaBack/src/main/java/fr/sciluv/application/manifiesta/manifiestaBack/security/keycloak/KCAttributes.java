package fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak;

public class KCAttributes {


    private String baseUrl = "http://localhost:8080/";
    private String tokenUrl = "realms/manifiesta/protocol/openid-connect/token";


    private String adminClientId = "admin-cli";
    private String adminClientSecret = "XqMiZuOyAiPFcQfMbUAkvlW11wGF020f";
    private String adminUsername = "admin";
    private String adminPassword = "admin";


    private String manifiestaClientId = "manifiesta";
    private String manifiestaClientSecret = "VNqyxjUtgvjNANRkfqJ6XczIbFRiYXWs";

    private String manifiestaNumberId = "ab177886-ee42-4a70-b0cf-41408cea4016";


    private String grantTypePassword = "password";
    private String grantTypeRefreshToken = "refresh_token";


    private String userRoleId= "5681558f-fa19-44c7-8cb8-fd05ee63825e";
    private String guestRoleId= "a93208f0-a807-4ab9-b2e2-29f1e20a97bb";

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getAdminClientId() {
        return adminClientId;
    }

    public String getAdminClientSecret() {
        return adminClientSecret;
    }

    public String getGrantTypePassword() {
        return grantTypePassword;
    }

    public String getGrantTypeRefreshToken() {
        return grantTypeRefreshToken;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getManifiestaClientSecret() {
        return manifiestaClientSecret;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public String getGuestRoleId() {
        return guestRoleId;
    }

    public String getManifiestaClientId() {
        return manifiestaClientId;
    }

    public String getManifiestaNumberId() {
        return manifiestaNumberId;
    }
}