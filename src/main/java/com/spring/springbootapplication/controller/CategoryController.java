package com.spring.springbootapplication.controller;

import java.util.ArrayList;
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dto.CategoryRequest;
import com.spring.springbootapplication.entity.LoginUser;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.service.CategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // --- 一覧・編集画面表示（月ごとの絞り込み対応） ---
    @GetMapping("/edit")
    public String editCategory(@RequestParam(value = "month", required = false) String month, Model model) {

        if (month == null || month.isEmpty()) {
            month = String.valueOf(java.time.LocalDate.now().getMonthValue());
        }

        List<LearningData> allLearningData = categoryService.findAllLearning_data();

        List<LearningData> backendList = new ArrayList<>();
        List<LearningData> frontendList = new ArrayList<>();
        List<LearningData> infraList = new ArrayList<>();

        for (LearningData data : allLearningData) {
            if (month.equals(data.getStudyMonth())) {
                if (data.getCategoryId() == 1) {
                    backendList.add(data);     
                } else if (data.getCategoryId() == 2) {
                    frontendList.add(data);  
                } else if (data.getCategoryId() == 3) {
                    infraList.add(data);     
                }
            }
        }

        model.addAttribute("backend_data", backendList);
        model.addAttribute("frontend_data", frontendList);
        model.addAttribute("infra_data", infraList);

        model.addAttribute("currentMonth", month);
        
        return "categories/edit"; 
    }

    // --- 項目追加画面表示 ---
    @GetMapping("/add")        
    public String addCategory(
        @RequestParam(value = "categoryId", required = false) Long categoryId, 
        @RequestParam(value = "month", required = false) String month,
        Model model) {
        
        if (month == null || month.isEmpty()) {
            month = String.valueOf(java.time.LocalDate.now().getMonthValue());
        }

        String categoryName = getCategoryNameById(categoryId);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("month", month); 
        model.addAttribute("categoryRequest", new CategoryRequest());
        
        return "categories/add"; 
    }

    // --- 新規登録・重複チェック処理（重複時はバリデーションエラーとして画面に戻す） ---
    @PostMapping("/add")       
    public String registerCategory(
        @RequestParam(value = "categoryId", required = false) Long categoryId, 
        @RequestParam(value = "month", required = false) String month,
        @ModelAttribute("categoryRequest") @Validated CategoryRequest categoryRequest, // ★小文字に統一
        BindingResult result,
        Model model,
        @AuthenticationPrincipal LoginUser loginUser,
        RedirectAttributes redirectAttributes
        ) {

        int targetCategoryId = (categoryId != null) ? categoryId.intValue() : categoryRequest.getCategory_id();
        int userId = (loginUser != null && loginUser.getUser() != null) ? loginUser.getUser().getId().intValue() : 0;
        
        if (month == null || month.isEmpty()) {
            month = String.valueOf(java.time.LocalDate.now().getMonthValue());
        }

        // フォームの入力エラーがない場合のみ、重複チェックを行う
        if (!result.hasErrors()) {
            List<LearningData> allData = categoryService.findAllLearning_data();
            if (allData != null) {
                for (LearningData data : allData) {
                    if (data.getUserId() == userId &&
                        data.getCategoryId() == targetCategoryId &&
                        month.equals(data.getStudyMonth()) &&
                        data.getStudyItem() != null &&
                        data.getStudyItem().equals(categoryRequest.getStudy_item())) {
                        
                        // 既に存在する場合はエラーを持たせる
                        result.rejectValue("study_item", "error.studyItem", 
                            categoryRequest.getStudy_item() + " は既に登録されています");
                        break;
                    }
                }
            }
        }

        // バリデーションエラー（または重複エラー）がある場合は入力画面に戻す
        if (result.hasErrors()) {
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categoryName", getCategoryNameById(categoryId));
            model.addAttribute("month", month);
            model.addAttribute("categoryRequest", categoryRequest); // フォーム内容を保持して戻す
            return "categories/add";
        }

        // 新規登録処理（INSERT）
        LearningData learning_data = new LearningData();
        learning_data.setCategoryId(targetCategoryId);
        learning_data.setUserId(userId);
        learning_data.setStudyItem(categoryRequest.getStudy_item());
        learning_data.setStudyTime(categoryRequest.getStudy_time());
        learning_data.setStudyMonth(month);

        categoryService.addStudyItem(learning_data);

        String currentCategoryName = getCategoryNameById(categoryId);
        String successMessage = currentCategoryName + " に " 
                              + categoryRequest.getStudy_item() + " を<br>"
                              + categoryRequest.getStudy_time() + " 分で追加しました！";

        model.addAttribute("successMessage", successMessage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", currentCategoryName);
        model.addAttribute("month", month);
        model.addAttribute("categoryRequest", new CategoryRequest());

        return "categories/add";
   }

    private String getCategoryNameById(Long categoryId) {
        if (categoryId == null) return "未選択";
        switch (categoryId.intValue()) {
            case 1: return "バックエンド";
            case 2: return "フロントエンド";
            case 3: return "インフラ";
            default: return "カテゴリー";
        }
    }

    @PostMapping("/edit")
    public String studyTimeUpdate(
        @ModelAttribute CategoryRequest categoryRequest,
        RedirectAttributes redirectAttributes,
        Model model) {


        // 学習時間の更新処理
        LearningData learningData = new LearningData();
        learningData.setId(categoryRequest.getId());
        learningData.setStudyTime(categoryRequest.getStudy_time());
        categoryService.updateStudyTime(learningData);

        // リダイレクト先にも「何月か」を維持して戻す
        String month = categoryRequest.getMonth();
        if (month != null && !month.isEmpty()) {
            redirectAttributes.addAttribute("month", month);
        }
        
        // フラッシュメッセージを設定する
        String successMessage = categoryRequest.getStudy_item() + " の学習時間を保存しました！";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        
        

        return "redirect:/categories/edit";
    }

    /** ユーザー削除処理*/
    @PostMapping("/delete")
    public String studyTimeDelete(
        CategoryRequest categoryRequest,
        Model model,
        RedirectAttributes redirectAttributes ) {
    	
    	//ユーザーを削除
        LearningData learningData = new LearningData();
        learningData.setId(categoryRequest.getId());
        learningData.setStudyTime(categoryRequest.getStudy_time());
    	categoryService.deleteStudyTime(learningData);
    	
        // リダイレクト先にも「何月か」を維持して戻す
        String month = categoryRequest.getMonth();
        if (month != null && !month.isEmpty()) {
            redirectAttributes.addAttribute("month", month);
        }

         // フラッシュメッセージを設定する
        String successMessage = categoryRequest.getStudy_item() + " を削除しました！";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

    	//ユーザー一覧画面にリダイレクト
    	return "redirect:/categories/edit";
    }

}