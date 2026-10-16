package com.spring.springbootapplication.controller;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dao.CategoryMapper;
import com.spring.springbootapplication.dao.ProfileMapper; // 
import com.spring.springbootapplication.entity.LoginUser;
import com.spring.springbootapplication.entity.User;

@Controller
public class LoginController {

    @Autowired
    private ProfileMapper profileMapper; // ★これが必要です

     @Autowired
    private CategoryMapper categoryMapper; // ★これが必要です

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("login", new User());

        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        }
        return "login";
    }
    
    @GetMapping("/top")
    public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        // ログイン中のIDを使って、データベースから常に最新のユーザー情報を取得し直す
        User latestUser = profileMapper.selectById(loginUser.getUser().getId());

        // 最新のユーザー情報をモデルにセットする
        model.addAttribute("user", latestUser);
        
        // 横軸のラベル（先々月、先月、今月）
        String[] label = {"先々月", "先月", "今月"};

        YearMonth currentMonth = YearMonth.now();
        String currentMonthStr = String.valueOf(currentMonth.getMonthValue());
        String previousMonthStr = String.valueOf(currentMonth.minusMonths(1).getMonthValue());
        String twoMonthsAgoStr = String.valueOf(currentMonth.minusMonths(2).getMonthValue());

        Long userId = loginUser.getUser().getId();

        // -----------------------------------------------------------------
        // カテゴリごとの3ヶ月分データ（先々月、先月、今月）を取得する例
        // ※ CategoryMapper にカテゴリIDや名称、年月を指定して合計時間を取得するメソッドが必要です
        // -----------------------------------------------------------------

        // 例：バックエンドのデータ (3ヶ月分)
        List<Integer> backendData = new ArrayList<>();
        backendData.add(categoryMapper.findTotalByMonth(userId, 1L, twoMonthsAgoStr));   // 例: ID 1がバックエンド
        backendData.add(categoryMapper.findTotalByMonth(userId, 1L, previousMonthStr));
        backendData.add(categoryMapper.findTotalByMonth(userId, 1L, currentMonthStr));

        // 例：フロントエンドのデータ (3ヶ月分)
        List<Integer> frontendData = new ArrayList<>();
        frontendData.add(categoryMapper.findTotalByMonth(userId, 2L, twoMonthsAgoStr));  // 例: ID 2がフロントエンド
        frontendData.add(categoryMapper.findTotalByMonth(userId, 2L, previousMonthStr));
        frontendData.add(categoryMapper.findTotalByMonth(userId, 2L, currentMonthStr));

        // 例：インフラのデータ (3ヶ月分)
        List<Integer> infraData = new ArrayList<>();
        infraData.add(categoryMapper.findTotalByMonth(userId, 3L, twoMonthsAgoStr));     // 例: ID 3がインフラ
        infraData.add(categoryMapper.findTotalByMonth(userId, 3L, previousMonthStr));
        infraData.add(categoryMapper.findTotalByMonth(userId, 3L, currentMonthStr));

    
        // データの受け渡し
        model.addAttribute("label", label);
        model.addAttribute("backendData",  backendData);
        model.addAttribute("frontendData",  frontendData);
        model.addAttribute("infraData",  infraData);

        return "users/top";
    }
}
