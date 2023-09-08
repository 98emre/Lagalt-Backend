package project.lagalt.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter){
        this.jwtAuthConverter = jwtAuthConverter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "api/projects/public/**",
                                        "api/comments/public/**",
                                        "api/users/public/**"
                                )
                                .permitAll().
                                anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
