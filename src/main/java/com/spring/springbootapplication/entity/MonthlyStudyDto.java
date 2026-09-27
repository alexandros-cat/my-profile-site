package com.spring.springbootapplication.entity;

import lombok.Data;

@Data
public class MonthlyStudyDto {
    private String month;         // '2026-09' などが入る
    private int totalStudyTime;   // 合計時間が入る
    // ゲッターセッター等
}