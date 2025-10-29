package ru.alexdev.project.electronic_lib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/registration").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN"))

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/hello", true)
                        .failureUrl("/auth/login?error"))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")); // Настройка формы

        return http.build();
    }


    // Связка кастомной логики аутентификации
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Используй этот сервис для поиска пользователей
        authProvider.setUserDetailsService(personDetailsService);

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
