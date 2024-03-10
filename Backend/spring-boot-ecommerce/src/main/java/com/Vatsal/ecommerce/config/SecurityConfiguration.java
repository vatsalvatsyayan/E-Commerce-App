package com.Vatsal.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.okta.spring.boot.oauth.Okta;

@Configuration
// @EnableWebSecurity
public class SecurityConfiguration {

    //@SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         //protect endpoint /api/orders
        http.authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/orders/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));
 
        // + CORS filters
        http.cors(Customizer.withDefaults());
 
        // + content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());
 
        // + non-empty response body for 401 (more friendly)
        Okta.configureResourceServer401ResponseBody(http);
 
        // we are not using Cookies for session tracking >> disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);
 
        return http.build();
    }
}

//      @Bean
//     public JwtDecoder jwtDecoder() {
//         return NimbusJwtDecoder.withJwkSetUri("https://your-auth0-domain/.well-known/jwks.json").build();
//     }

//     @Bean
//     public SecurityConfigurerAdapter securityConfigurerAdapter() {
//         return new SecurityConfigurerAdapter(jwtDecoder());
//     }

//     @EnableWebSecurity
//     public static class SecurityConfigurerAdapter extends SecurityConfigurerAdapter {
//         private final JwtDecoder jwtDecoder;

//         public SecurityConfigurerAdapter(JwtDecoder jwtDecoder) {
//             this.jwtDecoder = jwtDecoder;
//         }

//         @Override
//         public void configure(HttpSecurity http) throws Exception {
//             http.oauth2ResourceServer().jwt().decoder(jwtDecoder);
//         }
//     }

// }

