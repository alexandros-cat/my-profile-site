package com.spring.springbootapplication.dto;

import java.io.Serializable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class CategoryRequest implements Serializable {
    
    /**
     * 学習データID（更新時にどの行のデータかを特定するため）
     */
    private Long id;

    /**
     * カテゴリーID
     */
    private Integer category_id;
    
    /**
     * 月
     */
    private String month;

    /**
     * 名前
     */
    @NotEmpty(message = "項目名は必ず入力してください")
    @Size(max = 50, message = "項目名は50文字以内で入力してください")
    private String study_item;

    /**
     * 学習時間
     */
    @NotNull(message = "学習時間は必ず入力してください")
    @Min(value = 0, message = "学習時間は0以上の数字で入力してください")
    private Integer study_time;
}

