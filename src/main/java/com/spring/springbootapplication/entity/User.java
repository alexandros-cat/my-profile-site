package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

    /**
 * ユーザー情報 Entity
 */
@Data
public class User implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名前
     */
    private String name;

    /**
     * メールアドレス
     */
    private String email;

    /**
     * パスワード
     */
    private String password;

    /**
     * 自己紹介
     */
    private String profile;

    /**
     * アバター画像
     */
    private String avatar_image;

    /**
     * 登録日時
     */
    private Date createDate;

    /**
     * 更新日時
     */
    private Date updateDate;

}
    

