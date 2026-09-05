package com.spring.springbootapplication.config;

import java.io.File;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ユーザーディレクトリ（プロジェクトのルート）を基準にした絶対パスを取得
        String rootDir = System.getProperty("user.dir");
        File uploadDir = new File(rootDir, "uploads");
        
        // フォルダが存在しない場合は作成しておく
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        String uploadUri = uploadDir.toURI().toString();
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadUri);
    }
}