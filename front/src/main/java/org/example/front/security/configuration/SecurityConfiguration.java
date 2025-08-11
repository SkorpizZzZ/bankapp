package org.example.front.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(customizer ->
                        customizer
                                .requestMatchers("/signup").permitAll()
                                .anyRequest().authenticated()
                                )
                .formLogin(form ->
                        form.loginPage("/login").permitAll()
                )
                .logout(withDefaults())
                .build();
    }
}
