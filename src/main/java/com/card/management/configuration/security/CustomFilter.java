package com.card.management.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.card.management.configuration.exception.APIResponse;
import com.card.management.dto.CurrentUserDTO;
import com.card.management.enumeration.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class CustomFilter extends UsernamePasswordAuthenticationFilter
{

    private final AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    public CustomFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

  @Override
  public Authentication attemptAuthentication(final HttpServletRequest request, HttpServletResponse response) throws  AuthenticationException{
      Map<String, String> jsonRequest = null;

      try {
          byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
          jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }

      String username = jsonRequest.get("email");
      String password = jsonRequest.get("password");

      final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
      return authenticationManager.authenticate(authenticationToken);
  }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,final HttpServletResponse response,
                                            final FilterChain chain,final Authentication authentication) throws IOException, ServletException {
        final CurrentUserDTO principalUser =
                (CurrentUserDTO) authentication.getPrincipal();

        final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        final int expiresIn = 3600000 * 18;
        final Date dateOfExpiry = new Date(System.currentTimeMillis() + expiresIn);

        response.setContentType("application/json");

        final String accessToken = JWT.create().withSubject(principalUser.getUsername())
                .withExpiresAt(dateOfExpiry)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("userType", principalUser.getUserType())
                .withClaim("id", principalUser.getId())
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
