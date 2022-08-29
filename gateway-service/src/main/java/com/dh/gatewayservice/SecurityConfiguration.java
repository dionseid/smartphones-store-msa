package com.dh.gatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;

@Configuration
public class SecurityConfiguration { // firewall

    @Bean
    SecurityWebFilterChain springSecurityFilterChain /* Agregamos interceptor a cadena de llamados
        para que todes les usuaries estén validades por OAuth 2.0
        Necesitaremos proveedor que habremos de configurar en application.yml */
            (ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {

        // Authenticate through configured OpenID provider
        http.oauth2Login();

        // Also logout at the OpenID Connect provider
        http.logout(logout -> logout.logoutSuccessHandler
                (new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));

        // Require authentication for all requests
        http.authorizeExchange().anyExchange().authenticated();

        // Allow showing /home within a frame
        http.headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);

        // Disable CRSF in the gateway to prevent conflicts with proxied service CSRF
        http.csrf().disable();

        return http.build();

    }

//    protected void configure(HttpSecurity http) throws Exception { // ❓ versión resumida
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login(); // Esta línea fuera la autenticación del proveedor
//    }

}