package zurihaqi.simple_gacha_sim.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zurihaqi.simple_gacha_sim.exception.AccessDeniedException;
import zurihaqi.simple_gacha_sim.exception.AuthenticationEntryException;
import zurihaqi.simple_gacha_sim.security.JWTAuthFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AccessDeniedException accessDeniedException;
    private final AuthenticationEntryException authenticationEntryException;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/***").permitAll()
                        .requestMatchers("/api/v1/refresh-token/refresh").permitAll()
                        .requestMatchers("/api/v1/inventories/***").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/***").hasRole("ADMIN")
                        .requestMatchers("/api/vi/tiers/***").hasRole("ADMIN")
                        .requestMatchers("/api/v1/prizes/***").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception.accessDeniedHandler(accessDeniedException))
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryException));

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> userAuthorizationManager() {
        AuthorizationManager<RequestAuthorizationContext> adminAuth = AuthorityAuthorizationManager.hasRole("ADMIN");
        return (authentication, context) -> {
            if (adminAuth.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            }
            try {
                int userId = Integer.parseInt(context.getVariables().get("id"));
                return new AuthorizationDecision(authentication.get().getPrincipal().equals(userId));
            } catch (NumberFormatException e) {
                return new AuthorizationDecision(false);
            }
        };
    }
}
