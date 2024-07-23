package com.amrsoftware.msvc_user.security.filter;

import com.amrsoftware.msvc_user.domain.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import static com.amrsoftware.msvc_user.security.util.constant.Constant.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        var username = user.getUsername();
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        var claims =
            Jwts.claims()
            .add("authorities", new ObjectMapper().writeValueAsString(roles)).build();

        String jws = Jwts
            .builder().subject(username)
            .claims(claims)
            .signWith(SECRET_KEY)
            .compact();

        var body = new HashMap<String, String>();
        body.put("token", jws);
        body.put("username", username);
        body.put("message", "Token generado correctamente!");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jws);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        var body = new HashMap<String, String>();
        body.put("message", "Error, credenciales incorrecta");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
