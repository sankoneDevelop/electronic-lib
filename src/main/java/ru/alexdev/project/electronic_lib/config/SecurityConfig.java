package ru.alexdev.project.electronic_lib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.alexdev.project.electronic_lib.services.AuthUserDetailsService;
import ru.alexdev.project.electronic_lib.services.AuthUserService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthUserDetailsService authUserDetailsService;

    @Autowired
    public SecurityConfig(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/authors/new", "/authors/*/edit", "/authors/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authors/**").hasRole("ADMIN")

                        .requestMatchers("/authors/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().hasAnyRole("USER", "ADMIN"))

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/main", true)
                        .failureUrl("/auth/login?error"))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"))
                .exceptionHandling( exception -> exception
                        .accessDeniedPage("/access-denied"));


        return http.build();
    }


    // Связка кастомной логики аутентификации
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Используй этот сервис для поиска пользователей
        authProvider.setUserDetailsService(authUserDetailsService);

        // Используй этот кодировщик проверки паролей
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
