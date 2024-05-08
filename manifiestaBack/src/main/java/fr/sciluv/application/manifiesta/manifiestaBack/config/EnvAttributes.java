package fr.sciluv.application.manifiesta.manifiestaBack.config;

public class EnvAttributes {


    private String baseUrl = "http://localhost:8080/";
    private String tokenUrl = "realms/manifiesta/protocol/openid-connect/token";


    private String adminClientId = "admin-cli";
    private String adminClientSecret = "nVxqLHElpsoAuvREh5MzbViJp6uGhuNL";
    private String adminUsername = "admin";
    private String adminPassword = "admin";


    private String manifiestaClientId = "manifiesta";
    private String manifiestaClientSecret = "sqVweT2unKFE8WkbSET2PedQgyddGuVs";

    private String manifiestaNumberId = "1c242811-44c4-4c52-b6d3-364b7449feb8";


    private String grantTypePassword = "password";
    private String grantTypeRefreshToken = "refresh_token";


    private String userRoleId= "733ac06d-8dfb-49aa-afac-00e8e3249460";
    private String guestRoleId= "338b431b-2873-4e18-840d-45e053c8c704";

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