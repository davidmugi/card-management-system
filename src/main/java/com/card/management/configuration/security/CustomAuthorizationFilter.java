package com.card.management.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.card.management.dto.CurrentUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //        if is login just allow it
        if (request.getServletPath().equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());

                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();

                    DecodedJWT decodedJWT = verifier.verify(token);

                    String username = decodedJWT.getSubject();
                    String userType = decodedJWT.getClaim("userType").toString();
                    String id = decodedJWT.getClaim("id").toString();


                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    authorities.add(new SimpleGrantedAuthority(userType));

                   final CurrentUserDTO currentUserDTO = new CurrentUserDTO(username,"",authorities);
                   currentUserDTO.setUserType(Integer.parseInt(userType));
                   currentUserDTO.setId(Long.valueOf(id));

                    UsernamePasswordAuthenticationToken userPasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    userPasswordAuthenticationToken.setDetails(currentUserDTO);

                    SecurityContextHolder.getContext().setAuthentication(userPasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    logger.error("Error : " + ex.getMessage());
                    response.setHeader("error", ex.getMessage());
                    String localizedErrorMessage = ex.getLocalizedMessage();

                    int errorCode = FORBIDDEN.value();
                    if (localizedErrorMessage.startsWith("Request processing failed")) {
                        errorCode = INTERNAL_SERVER_ERROR.value();
                    }
                    response.setStatus(errorCode);

                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", localizedErrorMessage);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }
}
