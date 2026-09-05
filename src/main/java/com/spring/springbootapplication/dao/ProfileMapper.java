package com.spring.springbootapplication.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.springbootapplication.entity.User;

@Mapper
public interface ProfileMapper {
    @Select("SELECT id, name, profile, avatar_image FROM users WHERE id = #{id}")
    User selectById(Long id);

    // ★ データベースのプロフィール情報を更新するメソッドを追加
    @Update("UPDATE users SET profile = #{profile}, avatar_image = #{avatarImage} WHERE id = #{id}")
    void update(User user);
}