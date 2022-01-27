package com.services.app.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.app.services.support.AuthUser;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  @Value("${jwt.secret}")
  private static String secret = "JWTsecretkey";
  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  // Call first check ????
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);

    return authenticationManager.authenticate(authenticationToken);
  }

  // Called if success??
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authentication) throws IOException, ServletException {
    AuthUser user = (AuthUser) authentication.getPrincipal();
    // User user = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
    String access_token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1000))
        .withIssuer(request.getRequestURI().toString())
        .withClaim("roles", user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()))
        .sign(algorithm);
    String refresh_token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 3000 * 60 * 1000))
        .withIssuer(request.getRequestURI().toString())
        .sign(algorithm);

    Map<String, String> token = new HashMap<>();
    token.put("access_token", access_token);
    token.put("refresh_token", refresh_token);
    token.put("empId", user.getEmpId().toString());
    token.put("employeeName", user.getEmployeeName());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);// application/json
    new ObjectMapper().writeValue(response.getOutputStream(), token);
  }

}