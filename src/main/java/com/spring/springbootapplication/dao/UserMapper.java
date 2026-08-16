package com.spring.springbootapplication.dao;

import java.util.Optional;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.entity.User;

@Mapper
public interface UserMapper {
    
    /**
     * ユーザー情報登録
     * @param user 登録用エンティティデータ
     */
    @Insert("INSERT INTO users (name, email, password, create_date, update_date) VALUES (#{name}, #{email}, #{password}, NOW(), NOW())")
    void save(User user);

    /**
     * メールアドレスでユーザーを検索
     * @param email メールアドレス
     * @return ユーザー情報（存在しない場合はOptional.empty）
     */
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * PWでユーザーを検索
     * @param password パスワード
     * @return ユーザー情報（存在しない場合はOptional.empty）
     */
    @Select("SELECT * FROM users WHERE password = #{password}")
    Optional<User> findByPassword(@Param("password") String password);

    
}   

