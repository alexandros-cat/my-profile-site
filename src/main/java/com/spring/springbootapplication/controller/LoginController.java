package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import com.spring.springbootapplication.entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 画面遷移を行う Controllerクラス
@Controller
public class LoginController {

    // ログイン画面
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        
        // Thymeleafで使うためのUserオブジェクトをモデルに追加する
        model.addAttribute("login", new User());

        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        }
        return "login";
    }

}