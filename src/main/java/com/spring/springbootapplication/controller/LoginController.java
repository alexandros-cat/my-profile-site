package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dao.ProfileMapper; // 
import com.spring.springbootapplication.entity.LoginUser;
import com.spring.springbootapplication.entity.User;

@Controller
public class LoginController {

    @Autowired
    private ProfileMapper profileMapper; // ★これが必要です

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("login", new User());

        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        }
        return "login";
    }
    
    @GetMapping("/top")
    public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        // ログイン中のIDを使って、データベースから常に最新のユーザー情報を取得し直す
        User latestUser = profileMapper.selectById(loginUser.getUser().getId());
        
        // 最新のユーザー情報をモデルにセットする
        model.addAttribute("user", latestUser);
        
        return "users/top";
    }
}