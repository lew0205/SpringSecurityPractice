package study.securityPrac.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.securityPrac.global.config.security.jwt.JwtProperties;
import study.securityPrac.global.security.auth.AuthDetailService;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AuthDetailService authDetailService;

    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final Long ACCESS_EXP = 60L * 15;
    private static final Long REFRESH_EXP = 60L * 60 * 24 * 15;
    private static final String TOKEN_PREFIX = "Bearer ";

    ZonedDateTime accessExpiredTime;

    public ZonedDateTime getAccessExpiredTime() {
        return ZonedDateTime.now().plusSeconds(ACCESS_EXP);
    }

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_TYPE, jwtProperties.accessSecret, ACCESS_EXP);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_TYPE, jwtProperties.refreshSecret, REFRESH_EXP);
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        } else return parseToken(token);
    }

    public String exactEmailFromRefreshToken(String refresh) {
        return getTokenSubject(refresh, jwtProperties.refreshSecret);
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String parseToken(String token) {
        if (token.startsWith(TOKEN_PREFIX)) return token.replace(TOKEN_PREFIX, "");
        else return null;
    }

    public String generateToken(String sub, String type, Key secret, Long exp) {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secret)
            .setSubject(sub)
            .claim("type", type)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
            .compact();
    }

    private Claims getTokenBody(String token, Key secret) {
        Claims claims;
            claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body;
        return claims;
    }

    private String getTokenSubject(String token, Key secret) {
        return getTokenBody(token, secret).getSubject();
    }
}
