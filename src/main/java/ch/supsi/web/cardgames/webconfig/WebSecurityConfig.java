package ch.supsi.web.cardgames.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home-table").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/card/new").authenticated()
                        // Next two lines should be removed, because the owner should be able to delete or update his own cards
                        // But it gets complicated, the CardController should verify the user is also the owner or that user is admin
                        .requestMatchers("/card/*/edit").hasRole("ADMIN")
                        .requestMatchers("/card/*/delete").hasRole("ADMIN")
                        .requestMatchers("/card/**").permitAll()
                        .requestMatchers("/cards/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    PasswordEncoder BCPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
