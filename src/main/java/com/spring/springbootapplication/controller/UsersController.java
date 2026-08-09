package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.spring.springbootapplication.dto.UserRegisterRequest;
import com.spring.springbootapplication.service.UserService;

@Controller
public class UsersController {
    
    @Autowired
    private UserService userService;
    
    // http://localhost:8080/ にアクセスした時は自動で登録画面へ
    @GetMapping("/")
    public String index() {
        return "redirect:/users/register";
    }

    // ユーザー新規登録画面を表示
    @GetMapping("/users/register")
    public String createForm(Model model) {
        model.addAttribute("user", new UserRegisterRequest());
        return "users/register";
    }

    // 登録処理を実行
    @PostMapping("/users/register")
    public String create(@ModelAttribute("user") @Validated UserRegisterRequest userRegisterRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "users/register";
        }
        
        try {
            // ここで重複している場合に UserService から例外が投げられます
            userService.save(userRegisterRequest);
            
        } catch (Exception e) {
            // ★ ここを「メールアドレスがすでに登録されています」に変更する
            result.rejectValue("email", "error.email.duplicate", "メールアドレスがすでに登録されています");
            return "users/register";
        }
        
        // 登録成功後は /top へ転送
        return "redirect:/top";
    }

    // トップ画面を表示
    @GetMapping("/top")
    public String top() {
        // templates/top.html を表示する場合は "top"
        // templates/users/top.html を表示する場合は "users/top" にしてください
        return "users/top";
    }
}