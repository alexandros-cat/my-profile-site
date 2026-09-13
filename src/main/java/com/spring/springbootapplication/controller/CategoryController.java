package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @GetMapping("/edit")
    public String editCategory(@RequestParam(value = "id", required = false) Long id, Model model) {
        // 必要に応じてサービスの呼び出しやモデルへの属性追加を行う
        return "categories/edit"; // src/main/resources/templates/categories/edit.html を表示
    }   
}
