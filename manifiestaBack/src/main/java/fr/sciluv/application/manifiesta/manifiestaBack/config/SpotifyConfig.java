package fr.sciluv.application.manifiesta.manifiestaBack.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SpotifyConfig {

    private String clientId;
    private String clientSecret;

    public SpotifyConfig(String propertiesPath) {
        // Tenter de charger à partir du fichier de propriétés
        boolean loaded = loadFromPropertiesFile(propertiesPath);

        // Si le chargement à partir du fichier de propriétés échoue, tenter de charger à partir des variables d'environnement
        if (!loaded) {
            clientId = System.getProperty("spotify.client.id");
            clientSecret = System.getProperty("spotify.client.secret");
        }

        // Vérifier si les informations de configuration sont chargées
        if (clientId == null || clientSecret == null) {
            throw new IllegalStateException("Les informations de configuration de Spotify ne sont pas définies.");
        }
    }

    public SpotifyConfig() {

    }

    private boolean loadFromPropertiesFile(String propertiesPath) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(propertiesPath)) {
            prop.load(input);
            clientId = prop.getProperty("spotify.client.id");
            clientSecret = prop.getProperty("spotify.client.secret");

            // Si les propriétés sont bien définies, retourner true
            return clientId != null && clientSecret != null;
        } catch (IOException ex) {
            System.out.println("Impossible de charger le fichier de configuration : " + ex.getMessage());
            return false;
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public SpotifyConfig(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
