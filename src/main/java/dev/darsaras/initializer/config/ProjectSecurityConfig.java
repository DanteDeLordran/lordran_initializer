package dev.darsaras.initializer.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import dev.darsaras.initializer.filters.CsrfCookieFilter;
import dev.darsaras.initializer.filters.JWTGeneratorFilter;
import dev.darsaras.initializer.filters.JWTValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception{

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(
            session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .cors(
            cors -> cors
            .configurationSource(new CorsConfigurationSource() {

                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name()
                    ));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Arrays.asList("Authorization"));
                    config.setMaxAge(3600L);

                    return config;
                }
            })
        )
        .csrf( 
            csrf -> csrf
            .csrfTokenRequestHandler(requestHandler)
            .ignoringRequestMatchers("/auth/register","/roles/seed")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        //.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new JWTValidatorFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests(
            requests -> requests
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/mod/**").hasAnyRole("MOD","ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER","ADMIN","MOD")
            .requestMatchers("/auth/register","/roles/seed").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}