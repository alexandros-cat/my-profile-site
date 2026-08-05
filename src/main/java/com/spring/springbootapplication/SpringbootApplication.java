package com.spring.springbootapplication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; 
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 追加
// import org.springframework.security.crypto.password.PasswordEncoder; // 追加


@SpringBootApplication
@MapperScan("com.spring.springbootapplication.dao")
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	
	
}

// @Bean
// 	public PasswordEncoder passwordEncoder() {
// 		return new BCryptPasswordEncoder();
// 	}