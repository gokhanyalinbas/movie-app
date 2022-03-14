package com.backbase.movie.config.jwt;

import com.backbase.movie.exception.LoginSessionExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public class JwtTokenFilter extends GenericFilterBean {
    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    private String SECRET;
    private UserDetailsService userDetailsService;

    public JwtTokenFilter(UserDetailsService userDetailsService, String SECRET) {
        this.userDetailsService = userDetailsService;
        this.SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String headerValue = ((HttpServletRequest) req).getHeader(AUTHORIZATION);
        try {
            getBearerToken(headerValue).ifPresent(token -> {
                String username = getClaimFromToken(token, Claims::getSubject);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);


                if (username.equals(userDetails.getUsername()) && !isJwtExpired(token)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) req));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            });

        } catch (ExpiredJwtException ex) {
            throw new LoginSessionExpiredException("Session is expired. Please try to login again ! ");
        }

        filterChain.doFilter(req, res);
    }

    private Optional<String> getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private Boolean isJwtExpired(String token) throws ExpiredJwtException {
        Date expirationDate = getClaimFromToken(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}