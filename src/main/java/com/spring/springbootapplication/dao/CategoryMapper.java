package com.spring.springbootapplication.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import com.spring.springbootapplication.entity.MonthlyStudyDto;
import com.spring.springbootapplication.entity.LearningData;

@Mapper
public interface CategoryMapper {
        
    //カテゴリー一覧表示 
    @Select("SELECT id, user_id, category_id, study_month, study_item, study_time FROM learning_data WHERE user_id = #{userId} ORDER BY id ASC")
    public List<LearningData> findAllLearning_data(@Param("userId") Long userId);
    
    // 項目を追加する処理
    @Insert("INSERT INTO learning_data (user_id, category_id, study_month, study_item, study_time) " +
            "VALUES (#{userId}, #{categoryId}, #{studyMonth}, #{studyItem}, #{studyTime})")

    void insert(LearningData learning_data);  
    

    // 月毎の処理
    @Select("SELECT TO_CHAR(study_month, 'YYYY-MM') AS month, SUM(study_time) AS totalStudyTime " +
            "FROM learning_data WHERE user_id = #{userId} GROUP BY TO_CHAR(study_month, 'YYYY-MM') ORDER BY month DESC")
    List<MonthlyStudyDto> findMonthlySummary(@Param("userId") Long userId);
   
    // 学習時間を更新する処理
    @Update("UPDATE learning_data SET study_time = #{studyTime} WHERE id = #{id} AND user_id = #{userId}")
    void update(LearningData learning_data);

    // 学習データを削除する処理
    @Delete("DELETE FROM learning_data WHERE id = #{id} AND user_id = #{userId}")
    void delete(@Param("id") Long id, @Param("userId") Long userId);
   
      // 学習データ合算する処理
    @Select("SELECT SUM(study_time) FROM learning_data " +
                "WHERE user_id = #{userId} " +
                "AND category_id = #{categoryId} " +
                "AND study_month = #{studyMonth}")
     Integer findTotalByMonth(@Param("userId") Long userId, 
                                @Param("categoryId") Long categoryId, 
                                @Param("studyMonth") String studyMonth); 
    
}
