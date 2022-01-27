package com.services.app.security;

import com.services.app.filters.CustomAuthenticationFilter;
import com.services.app.filters.CustomAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  @Autowired
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");

    http.csrf().disable();
    http.cors().configurationSource(corsConfigurationSource());
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // http.authorizeRequests().antMatchers("/api/login/**",
    // "/api/token/refresh/**").permitAll();
    http.authorizeRequests().antMatchers("/api/login/**").permitAll();
    // http.authorizeRequests().antMatchers("/api/token/refresh/**").permitAll();
    // http.authorizeHttpRequests()
    // .antMatchers(MethodType.GET.name(), "/api/departaments/**")
    // .hasAnyAuthority("ROLE_ADMIN");
    // http.authorizeHttpRequests()
    // .antMatchers(MethodType.GET.name(), "/api/positions/**")
    // .hasAnyAuthority("ROLE_USER");
    // http.authorizeHttpRequests()
    // .antMatchers(MethodType.POST.name(), "/api/departaments/adm/**")
    // .hasAnyAuthority("ROLE_USER");

    http.authorizeHttpRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
    configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "", "Content-Type",
        "Accept", "JWT-Token", "Authorization", "Origin, Accept", "X-Requested-with"));
    configuration.setExposedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "", "Content-Type",
        "Accept", "JWT-Token", "Authorization", "Access-Control-Allow-Credentials", "Filename"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
