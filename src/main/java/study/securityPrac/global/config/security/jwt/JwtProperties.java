package study.securityPrac.global.config.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    Key accessSecret;
    Key refreshSecret;

    public JwtProperties(String accessSecret, String refreshSecret) {
        this.accessSecret = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshSecret = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }
}
