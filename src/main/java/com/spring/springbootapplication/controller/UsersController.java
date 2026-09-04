package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dao.ProfileMapper;
import com.spring.springbootapplication.dto.ProfileForm;
import com.spring.springbootapplication.dto.UserRegisterRequest;
import com.spring.springbootapplication.entity.User; 
import com.spring.springbootapplication.service.ProfileService;
import com.spring.springbootapplication.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private ProfileMapper profileMapper; // ★1. ここに ProfileMapper を追加する

    @GetMapping("/")
    public String index() {
        return "redirect:/users/register";
    }

    @GetMapping("/users/register")
    public String createForm(Model model) {
        model.addAttribute("user", new UserRegisterRequest());
        return "users/register";
    }

    @PostMapping("/users/register")
    public String create(@ModelAttribute("user") @Validated UserRegisterRequest userRegisterRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "users/register";
        }
        
        try {
            userService.save(userRegisterRequest);
        } catch (Exception e) {
            result.rejectValue("email", "error.email.duplicate", "メールアドレスがすでに登録されています");
            return "users/register";
        }
        
        return "redirect:/top";
    }
    
	@GetMapping("/users/edit")
    public String editProfile(@RequestParam("id") Long userId, Model model) {
        ProfileForm form = profileService.getEditProfile(userId);
        model.addAttribute("profileForm", form);
        return "users/edit";
    }

    // 自己紹介フォームの編集処理を実行
    @PostMapping("/users/edit")
    public String updateProfile(
            @Validated @ModelAttribute ProfileForm profileForm, 
            BindingResult result, 
            HttpSession session) { // ★2. 引数に HttpSession session を追加
      
        System.out.println("=== フォーム受信 ===");
        System.out.println("プロフィール: " + profileForm.getProfile());
        System.out.println("ファイルオブジェクト: " + profileForm.getAvatarFile());
        if (profileForm.getAvatarFile() != null) {
            System.out.println("ファイル名: " + profileForm.getAvatarFile().getOriginalFilename());
            System.out.println("ファイルサイズ: " + profileForm.getAvatarFile().getSize());
        }
        System.out.println("エラー有無: " + result.hasErrors());

        if(result.hasErrors()) {
            return "users/edit";
        }
        
        // データベースの情報を更新
        profileService.updateProfile(profileForm);
        
        // ★3. ここから追加：更新後の最新ユーザー情報を取得してセッションを上書きする
        User updatedUser = profileMapper.selectById(profileForm.getUserId());
        session.setAttribute("user", updatedUser);
        // ★ここまで
        
        return "redirect:/top"; 
	}

} 