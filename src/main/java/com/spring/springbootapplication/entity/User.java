package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class User implements Serializable {

    // 主キー
    private Long id;

    // --- ログイン用（Spring Security必須） ---
    private String email;
    private String password;
    private boolean enabled = true;

    // --- 新規登録・プロフィール用 ---
    private String name;
    private String profile;
    private String avatarImage;
    private Date createDate;
    private Date updateDate;
    // ----------------------------


}