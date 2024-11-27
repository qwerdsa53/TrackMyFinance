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
                // Отключение CSRF (так как используется JWT для защиты API)
                .csrf(csrf -> csrf.disable())
                // Настройка авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        // Эндпоинты с публичным доступом
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/register"
                        ).permitAll()
                        // Делаем маршруты аналитики доступными без авторизации
                        .requestMatchers("/api/v1/analytic/**").permitAll()
                        // Разрешаем доступ к определённым GET-запросам
                        .requestMatchers(HttpMethod.GET, "/api/transactions/count").permitAll()
                        // Защищённые маршруты: доступ к /users/** и /transactions/** только для роли ADMIN
                        .requestMatchers("/users/**", "/transactions/**").permitAll()
                        // Остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                // Подключаем фильтр JWT перед фильтром обработки аутентификации
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Отключаем стандартную форму входа
                .formLogin(form -> form.disable());

        return http.build();
    }

    // Настройка кодировщика паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Настройка менеджера аутентификации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
