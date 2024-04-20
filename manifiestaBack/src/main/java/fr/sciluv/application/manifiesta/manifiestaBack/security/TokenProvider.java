package fr.sciluv.application.manifiesta.manifiestaBack.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.UserRefreshToken;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
public class TokenProvider {

    private RSAPublicKey publicKey;

    public TokenProvider() {
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFB03IN3NpL4sOwZH9wlr9DaYl54Y9/AIOoufjYbzjayW0erZrG83YlxEwBsYUD/H1FKkCoRSLXQKtvVMFWwxB2TCjMqMdkWYUykd3ShIi//IxhDV17vDDk4YLGhP264VYHKgCS+QJ5QALZuQbrW0Peh34t+acR5NHKbt+KAb6MOE38LKmGq5RlCu7RV6MkK5mjW5qfPVC48OXSI5PeEyYhBRmXaCFk68MPB4HLm4Rf/F4nrX0J9AWkeTSEzeD+7hM9iwIElmnp/LExTzNOX5oGWahtqMWhweFJ+FwF4ijaA9mUCXrhpULs1SqfbCeB2vRIrV4TBSe5AIH7lBTX5+wIDAQAB";
        this.publicKey = RSAKeyConverter.convertPublicKey(publicKeyStr);
    }
    // Méthode pour vérifier si un token est expiré
    public boolean isTokenExpired(String token) {
        System.out.println("Token fourni: " + token);
        try {
            System.out.println("Début de la vérification de l'expiration du token...");

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            boolean expired = jwt.getExpiresAt().before(new Date());
            System.out.println("Token expiré? " + expired);
            return expired;
        } catch (JWTVerificationException e) {
            System.out.println("Le token est définitivement expiré ou invalide: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification du token: " + e.getMessage());
            return false;
        }
    }

    // Hypothétique méthode de validation de refresh token
    public boolean validateRefreshToken(String refreshToken) {
        try {
            System.out.println("Validation du refresh token...");
            Algorithm algorithm = Algorithm.HMAC512("your-256-bit-secret");
            JWTVerifier verifier = JWT.require(algorithm).build(); // Réutiliser l'algorithme pour la signature
            DecodedJWT jwt = verifier.verify(refreshToken);

            // Vérifier si le token est expiré
            boolean isExpired = jwt.getExpiresAt().before(new Date());
            if (isExpired) {
                System.out.println("Refresh token est expiré.");
                return false;
            }

            return true;
        } catch (JWTVerificationException e) {
            System.out.println("Refresh token est invalide: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erreur lors de la validation du refresh token: " + e.getMessage());
            return false;
        }
    }

    // Hypothétique méthode pour rafraîchir le access token
    public String refreshAccessToken(String refreshToken) {
        // Implémentation fictive
        UserRefreshToken userRefreshToken = new UserRefreshToken(refreshToken);
        return userRefreshToken.getToken() + " " + userRefreshToken.getRefreshToken();
    }
}