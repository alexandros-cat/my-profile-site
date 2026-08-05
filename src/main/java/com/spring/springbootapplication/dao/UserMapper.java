package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.entity.User;

@Mapper
public interface UserMapper {
    
    /**
     * ユーザー情報登録
     * @param user 登録用エンティティデータ
     */
    @Insert("INSERT INTO users (name, email, password, create_date, update_date) VALUES (#{name}, #{email}, #{password}, NOW(), NOW())")
    void save(User user);

}