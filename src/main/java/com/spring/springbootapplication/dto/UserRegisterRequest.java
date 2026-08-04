package com.spring.springbootapplication.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * ユーザー情報登録 リクエストデータ
 */
@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 名前
     */
    @NotEmpty(message = "名前は必ず入力してください")
    @Size(max = 255, message = "名前は255文字以内で入力してください")
    private String name;

    /**
     * メールアドレス
     */
    @NotEmpty(message = "メールアドレスは必ず入力してください")
    @Pattern(
        regexp = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$", 
        message = "メールアドレスが正しい形式ではありません"
    )
    private String email;

    /**
     * パスワード
     */
    @NotEmpty(message = "パスワードは必ず入力してください")
    // @Size(min = 8, max = 255, message = "パスワードは8文字以上で入力してください")
    @Pattern(regexp = "^[A-Za-z0-9]{8,}$", message = "英数字8文字以上で入力してください")
    private String password;
}