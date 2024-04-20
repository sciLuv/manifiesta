package fr.sciluv.application.manifiesta.manifiestaBack.security;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenRefresherFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public JwtTokenRefresherFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Filtrage de la requête pour la vérification du token...");
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7); // Supprime le préfixe "Bearer " du token

        if (accessToken == null || accessToken.isEmpty()) {
            System.out.println("Aucun token fourni avec la requête.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Aucun token d'authentification fourni.");
            return; // Arrête le traitement de la requête ici
        }

        if (tokenProvider.isTokenExpired(accessToken)) {
            System.out.println("Token expiré détecté.");
            String refreshToken = request.getHeader("Refresh-Token");

            if (refreshToken != null && tokenProvider.validateRefreshToken(refreshToken)) {
                System.out.println("Refresh token valide trouvé, rafraîchissement du token d'accès...");
                String newAccessToken = tokenProvider.refreshAccessToken(refreshToken);
                String[] tokens = newAccessToken.split(" ");
                response.setHeader("New-Access-Token", tokens[0]);
                response.setHeader("New-Refresh-Token", tokens[1]);
            }
        }

        filterChain.doFilter(request, response);
        System.out.println("Fin du filtrage de la requête.");
    }

}
