package com.mtvs.sciencemuseum.config;

import com.mtvs.sciencemuseum.domain.auth.filter.CustomLoginFilter;
import com.mtvs.sciencemuseum.domain.auth.filter.ExceptionHandleFilter;
import com.mtvs.sciencemuseum.domain.auth.filter.JwtAuthenticationFilter;
import com.mtvs.sciencemuseum.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
                );

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .addFilterAt(
                        customLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(new ExceptionHandleFilter(), CustomLoginFilter.class);

        /*JWT 검증 필터 등록*/
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), CustomLoginFilter.class)
                .addFilterBefore(new ExceptionHandleFilter(), JwtAuthenticationFilter.class);

        /*로그아웃 필터 설정*/
        http.addFilterBefore(new ExceptionHandleFilter(), LogoutFilter.class);
        http
                .logout(config -> config
                        .logoutUrl("/v1/auth/logout")
                );

        return http.build();
    }

    private CustomLoginFilter customLoginFilter() throws Exception {
        CustomLoginFilter filter = new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtProvider);
        filter.setFilterProcessesUrl("/v1/auth/login");
        return filter;
    }

    /*AuthenticationManager 등록*/
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /*비밀번호 암호화*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
