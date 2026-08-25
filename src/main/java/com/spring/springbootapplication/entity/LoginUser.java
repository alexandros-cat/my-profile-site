package com.spring.springbootapplication.entity;

import java.util.ArrayList;

import lombok.Getter;

public class LoginUser extends org.springframework.security.core.userdetails.User { 
    
    @Getter
    private  User user; // 自作の User エンティティ

    public LoginUser(User user) {
        super(
                user.getEmail(),     // ★ ここを user.getEmail() に変更する
                user.getPassword(), // パスワード
                true, // 有効フラグ (true)
                true,
                true,
                true,
                new ArrayList<>()   // 権限リスト
        );
        this.user = user;
    } 
}