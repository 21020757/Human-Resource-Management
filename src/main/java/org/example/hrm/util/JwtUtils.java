package org.example.hrm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    @Value( "${jwt.secret}")
    private String secret;
    @Value( "${jwt.token.expires}")
    private Long jwtExpiresMinutes;
    @Value( "${jwt.refresh.token.expires}")
    private Long jwtRefreshExpiresMinutes;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiresMinutes * Constants.IN_MINUTES))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiresMinutes * Constants.IN_DAYS))
                .signWith(getSignInKey())
                .compact();
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAgeMinutes) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (maxAgeMinutes * 60));
        response.addCookie(cookie);
    }

    public void setTokenCookies(String accessToken, String refreshToken, HttpServletResponse response) {
        addCookie(response, "JWT", accessToken, jwtExpiresMinutes);
        addCookie(response, "REFRESH_JWT", refreshToken, jwtRefreshExpiresMinutes);
    }

    public String getJwtFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "JWT");
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "REFRESH_JWT");
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch(JwtException e){
// catch null, wrong token, expired token
            return false;
        }
    }

    public void removeTokenFromCookie(HttpServletResponse response){
        addCookie(response, "JWT", null, 0);
        addCookie(response, "REFRESH_JWT", null, 0);
    }

    public String extractEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }


    private SecretKey getSignInKey() {
        //        SignatureAlgorithm.HS256, this.secret
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
