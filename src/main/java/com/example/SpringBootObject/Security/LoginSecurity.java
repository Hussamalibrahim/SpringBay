package com.example.SpringBootObject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class LoginSecurity {

    @Autowired
    private UserDetailsServicePrincipal userDetailsServicePrincipal;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**"
                                , "/product", "/product/**" ,"/product/post/product","/product/getMine/pyment"
                                , "/myProfile", "/myProfile/changePassword"
                                , "/", "/loginPage", "/registerPage", "/register")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/loginPage")
                                .loginProcessingUrl("/login")
                                .failureUrl("/loginPage?error=true")
                                .defaultSuccessUrl("/product")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loginPage?logout=true")
                        .permitAll()
                )

                .rememberMe(remember -> remember
                        .rememberMeServices(rememberMeServices())
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userDetailsServicePrincipal);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServicePrincipal);
        return daoAuthenticationProvider;
    }
}