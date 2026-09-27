package com.spring.springbootapplication.entity;

import lombok.Data;

@Data
public class LearningData {
    private Long id;
    private int userId;      // user_id から変更
    private int categoryId;  // category_id から変更
    private String studyMonth; // study_month から変更
    private String studyItem;  // study_item から変更
    private Integer studyTime; // study_time から変更
}
