package com.spring.springbootapplication.service;

import com.spring.springbootapplication.dao.CategoryMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.entity.MonthlyStudyDto;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
  
  public List<LearningData> findAllLearning_data() {
        return categoryMapper.findAllLearning_data();
    }  
    
    // 学習する月を集計する処理
    public List<MonthlyStudyDto> totalMonthlyDate(){
      return categoryMapper.findMonthlySummary();
    }

    // 項目を追加する処理
    public void addStudyItem(LearningData learning_data){
      categoryMapper.insert(learning_data);  
    }

    // 学習時間を保存する処理   
    public void saveStudyTime(LearningData learning_data){
     categoryMapper.update(learning_data);

    }
    
}
