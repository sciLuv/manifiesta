package fr.sciluv.application.manifiesta.manifiestaBack.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenProcessingService {
    public String extractTokenData(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String subject = jwt.getSubject();
            String issuer = jwt.getIssuer();
            // Ajoutez plus de champs selon les besoins, tels que les rôles, etc.
            return subject;
        } catch (Exception e) {
            return null;
        }
    }
}
