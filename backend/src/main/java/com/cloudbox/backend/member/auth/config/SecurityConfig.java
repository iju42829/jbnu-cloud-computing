package com.cloudbox.backend.member.auth.config;

import com.cloudbox.backend.common.constants.Role;
import com.cloudbox.backend.member.auth.filter.JsonUsernamePasswordAuthenticationFilter;
import com.cloudbox.backend.member.auth.handler.CustomAuthenticationFailureHandler;
import com.cloudbox.backend.member.auth.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new JsonUsernamePasswordAuthenticationFilter(successHandler,
                failureHandler,
                authenticationManager,
                new HttpSessionSecurityContextRepository());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .addFilterAt(jsonUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/sign-up").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**").permitAll()

                        .requestMatchers("/api/test/user").hasRole(Role.USER.getRoleName())
                        .requestMatchers("/api/test/admin").hasRole(Role.ADMIN.getRoleName())

                        .anyRequest().authenticated());

        http
                .cors(cors -> cors
                        .configurationSource(corsConfig.corsConfigurationSource()));

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation()
                        .changeSessionId());

        return http.build();
    }
}
