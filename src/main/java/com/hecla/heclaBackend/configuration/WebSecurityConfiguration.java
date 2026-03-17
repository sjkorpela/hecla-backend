package com.hecla.heclaBackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(smc -> smc
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(rsc -> rsc
                    .jwt(jwtc -> jwtc
                            .jwtAuthenticationConverter(jwtAuthenticationConverter)))
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers(HttpMethod.GET,    "/api/persons").permitAll()
                    .requestMatchers(HttpMethod.POST,   "/api/persons").authenticated()
                    .requestMatchers(HttpMethod.PUT,    "/api/persons/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/persons/**").authenticated()
                    .anyRequest().authenticated());

    return httpSecurity.build();
  }
}