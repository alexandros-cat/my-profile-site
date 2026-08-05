package com.spring.springbootapplication.config; // パッケージ名は適宜合わせてください

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ① パスワードハッシュ化のためのBean定義（これは残す）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ② 勝手にログイン画面に飛ばないように、すべてのアクセスを許可する設定
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // すべてのリクエストのアクセスを無条件で許可する
            )
            .csrf(csrf -> csrf.disable()); // 必要に応じてCSRFを無効化（APIやPOST送信で弾かれるのを防ぐため）

        return http.build();
    }
}