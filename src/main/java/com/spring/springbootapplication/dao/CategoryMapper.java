package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.entity.MonthlyStudyDto;
import com.spring.springbootapplication.entity.LearningData;

@Mapper
public interface CategoryMapper {
        
    //カテゴリー一覧表示 
    @Select("SELECT id, user_id, category_id, study_month, study_item, study_time FROM learning_data")
    public List<LearningData> findAllLearning_data();
    
    // 項目を追加する処理
    @Insert("INSERT INTO learning_data (user_id, category_id, study_month, study_item, study_time) " +
            "VALUES (#{userId}, #{categoryId}, #{studyMonth}, #{studyItem}, #{studyTime})")

    void insert(LearningData learning_data);  
    
    // 学習時間を保存する処理  
    void update(LearningData learning_data);  

    // 月毎の処理
    @Select("SELECT TO_CHAR(study_month, 'YYYY-MM') AS month, SUM(study_time) AS totalStudyTime " +
            "FROM learning_data GROUP BY TO_CHAR(study_month, 'YYYY-MM') ORDER BY month DESC")
    List<MonthlyStudyDto> findMonthlySummary();
}
