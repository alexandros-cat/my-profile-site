package com.spring.springbootapplication.service;

import org.springframework.stereotype.Service;
import com.spring.springbootapplication.dto.UserRegisterRequest;
import com.spring.springbootapplication.entity.User; // 追加
import com.spring.springbootapplication.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     * ユーザ情報登録
     * @param userRegisterRequest リクエストデータ
     */ 
    public void save(UserRegisterRequest userRegisterRequest) {
        // 1. DTO から Entity へデータを詰め替える
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword()); // ※本来はここでパスワードのハッシュ化などを推奨
        
        // 2. Entity を Mapper に渡して保存する
        userMapper.save(user);
    }
}