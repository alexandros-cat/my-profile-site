package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


    @Configuration
    @EnableWebSecurity
    // 1. 認証機能用 設定クラス
    public class SecurityConfig {

        // ① パスワードハッシュ化のためのBean定義（これは残す）
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        // ② セキュリティフィルタの設定
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,MyAuthenticationFailureHandler failureHandler) throws Exception {

            http
            .csrf(csrf -> csrf.disable()) // 一時的、あるいは必要に応じてCSRF保護を無効化
            .authorizeHttpRequests(
                auth -> auth.requestMatchers(
                    "/",
                    "/users/register",
                    "/login",
                    "/error", 
                    "/css/**",
                    "/js/**")
                    .permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(
                    form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("email") // ユーザー名にメールアドレスを使用
                    .passwordParameter("password") // パスワードのパラメータ名
                    .defaultSuccessUrl("/top", true)
                    .failureHandler(failureHandler)
                    .permitAll()
                )

                .logout(
                    logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
                .logoutUrl("/logout") //ログアウトのURL
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                );
            return http.build();
        }
    }