package com.spring.springbootapplication.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // エラーメッセージをリクエストに追加
        request.setAttribute("error", "メールアドレス、もしくはパスワードが間違っています");

        // エラーメッセージをログイン画面に渡すクエリパラメータを追加してリダイレクト
        response.sendRedirect("/login?error=true");
    }
}
