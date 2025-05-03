package org.example.hrm.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hrm.service.CustomUserDetailsService;
import org.example.hrm.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    public JwtAuthenticationFilter(JwtUtils jwtUtils,
                                   CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtils.getJwtFromCookie(request);
            if(accessToken != null && jwtUtils.validateToken(accessToken)) {
                authenticate(accessToken, request);
            } else {
                String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);
                if(refreshToken != null && jwtUtils.validateToken(refreshToken)) {
                    String newAccessToken = jwtUtils.generateAccessToken(jwtUtils.extractEmail(refreshToken));
                    jwtUtils.setTokenCookies(newAccessToken, refreshToken, response);
                    authenticate(newAccessToken, request);
                }
            }
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public void authenticate(String token, HttpServletRequest request) {
        String userEmail = jwtUtils.extractEmail(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
