package fr.sciluv.application.manifiesta.manifiestaBack.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.interfaces.RSAPublicKey;

public class RSAKeyConverter {
    public static RSAPublicKey convertPublicKey(String base64PublicKey) {
        try {
            byte[] publicBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return (RSAPublicKey) publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
