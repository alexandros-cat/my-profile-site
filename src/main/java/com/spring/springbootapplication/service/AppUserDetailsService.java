package com.spring.springbootapplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserMapper;
import com.spring.springbootapplication.entity.LoginUser;
import com.spring.springbootapplication.entity.User;

// 2. 認証機能 Serviceクラス
@Service
public class AppUserDetailsService implements UserDetailsService {

    // 2-1. Repositoryの依存性注入
    private final UserMapper userMapper ;
    public AppUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 2-2. ログイン時の認証メソッド
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        // List<GrantedAuthority> authorities = List.of(authority);

        return new LoginUser(user);
    }
}

