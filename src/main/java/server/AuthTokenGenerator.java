package server;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthTokenGenerator {

    private final SecureRandom secureRandom;
    private final Base64.Encoder base64Encoder;

    public AuthTokenGenerator(){
        secureRandom = new SecureRandom();
        base64Encoder = Base64.getUrlEncoder();
    }

    public String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
