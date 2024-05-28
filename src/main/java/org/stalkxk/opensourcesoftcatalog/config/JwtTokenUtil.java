package org.stalkxk.opensourcesoftcatalog.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration lifetime;

    /**
     * Отримує секретний ключ зі значення, вказаного властивістю "jwt.secret".
     *
     * @return секретний ключ
     */
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }

    /**
     * Генерує JWT токен на основі даних користувача.
     *
     * @param userDetails дані користувача
     * @return JWT токен
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime() + lifetime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issueDate)
                .expiration(expireDate)
                .signWith(getSecretKey(), Jwts.SIG.HS256).compact();
    }

    /**
     * Отримує клейми з JWT токену.
     *
     * @param token JWT токен
     * @return клейми
     */
    public Claims getClaimsFromToken(String token){
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Отримує ім'я користувача з JWT токену.
     *
     * @param token JWT токен
     * @return ім'я користувача
     */
    public String getUserNameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Отримує список ролей з JWT токену.
     *
     * @param token JWT токен
     * @return список ролей
     */
    public List<String> getRolesFromToken(String token){
        return getClaimsFromToken(token).get("roles", List.class);
    }
}

