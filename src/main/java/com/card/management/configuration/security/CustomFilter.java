package com.card.management.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.card.management.configuration.exception.APIResponse;
import com.card.management.enumeration.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomFilter extends UsernamePasswordAuthenticationFilter
{

    private final AuthenticationManager authenticationManager;


    public CustomFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

  @Override
  public Authentication attemptAuthentication(
         final HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      String username = request.getParameter("email");
      String password = request.getParameter("password");

      final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
      return authenticationManager.authenticate(authenticationToken);
  }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,final HttpServletResponse response,
                                            final FilterChain chain,final Authentication authentication) throws IOException, ServletException {
        final org.springframework.security.core.userdetails.User principalUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        final int expiresIn = 3600000 * 18;
        final Date dateOfExpiry = new Date(System.currentTimeMillis() + expiresIn);

        final String accessToken = JWT.create().withSubject(principalUser.getUsername())
                .withExpiresAt(dateOfExpiry)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        final APIResponse apiResponse = new APIResponse<>(ResponseStatus.SUCESSS.getStatus(), ResponseStatus.SUCESSS.getStatusCode(), accessToken);

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String error = failed.getLocalizedMessage();

        Map<String, Object> map = new HashMap<>();
        map.put("code", error.equalsIgnoreCase(ResponseStatus.DISABLED.getStatus()) ? ResponseStatus.DISABLED.getStatusCode()
                : ResponseStatus.INVALID_LOGIN_ATTEMPTED.getStatusCode());
        map.put("description", error);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final APIResponse apiResponse = new APIResponse<>(ResponseStatus.INVALID_LOGIN_ATTEMPTED.getStatus(), ResponseStatus.INVALID_LOGIN_ATTEMPTED.getStatusCode(), map);

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }

}
