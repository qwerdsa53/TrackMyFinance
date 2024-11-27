package qwerdsa53.trackmyfinance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ к этим эндпоинтам для всех
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll()
                        // Разрешаем доступ к GET запросу /api/transactions/count без авторизации
                        .requestMatchers(HttpMethod.GET, "/api/transactions/count").permitAll()
                        // Для остальных запросов к /users/** и /transactions/** требуется роль ADMIN
                        .requestMatchers("/users/**", "/transactions/**").hasRole("ADMIN")
                        // Все остальные запросы требуют авторизации
                        .anyRequest().authenticated()
                )
                // Добавляем фильтр для обработки JWT
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Отключаем форму входа
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
