package com.spring.springbootapplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.springbootapplication.dto.UserRegisterRequest;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // ★追加


/**
 * ユーザー情報 Service
 */
@Service
public class UserService {

    /**
     * ユーザー情報 Mapper
     */
    @Autowired
    private UserMapper userMapper;

    // ★パスワードエンコーダーを追加
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ユーザ情報登録
     * @param userRegisterRequest リクエストデータ
     */ 
    public void save(UserRegisterRequest userRegisterRequest) {
        // 1. DTO から Entity へデータを詰め替える
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        
        // ★ここでパスワードをハッシュ化してからセットする
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        user.setPassword(hashedPassword);
        
        // 2. Entity を Mapper に渡して保存する
        userMapper.save(user);
    }
}