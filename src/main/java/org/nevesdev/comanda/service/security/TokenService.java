package org.nevesdev.comanda.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.nevesdev.comanda.exceptions.UsernameException;
import org.nevesdev.comanda.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create().withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withArrayClaim("role", new String[]{user.getRole().getRole().toUpperCase()})
                    .withExpiresAt(this.generateExpDate()).sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generation token", e);
        }
    }

    public Map<String, String> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            var jwt = JWT.require(algorithm).withIssuer("auth-api").build().verify(token);
            List<String> role = jwt.getClaim("role").asList(String.class);
            String subject = jwt.getSubject();
            Map<String, String> response = new HashMap<>();
            response.put("role", role.get(0));
            response.put("subject", subject);
            return response;
        } catch (JWTVerificationException e) {
            throw new UsernameException(e.getLocalizedMessage(), 403);
        }
    }

    private Instant generateExpDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }


}
