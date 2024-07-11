package com.blog.community.config;

import com.blog.community.exception.security.CustomAccessDeniedHandler;
import com.blog.community.exception.security.CustomAuthenticationEntryPoint;
import com.blog.community.jwt.filter.JwtAuthenticationFilter;
import com.blog.community.jwt.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration corsConfiguration = new CorsConfiguration();

                                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                                corsConfiguration.setAllowCredentials(true);

                                corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));

                                return corsConfiguration;
                            }
                        }));

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/members/register", "/boards","/auth/login","/members/check-email","/members/check-nickname","/auth/reissue").permitAll()
                        .requestMatchers("/members/test").authenticated()
                        .anyRequest().authenticated()
                );

        // 스프링 시큐리티는 기본적으로 인증이 필요한 요청에 대해 세션을 생성하고 관리하는 기능을 제공하는데,
        // JWT 인증 방식에서는 세셩을 생성할 필요가 없기 때문에 이 기능을 STATELESS로 관리한다
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        //JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
