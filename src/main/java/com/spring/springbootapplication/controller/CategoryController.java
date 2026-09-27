    package com.spring.springbootapplication.controller;

    import java.util.ArrayList;
    import java.util.List;  
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import com.spring.springbootapplication.dto.CategoryRequest;
    import com.spring.springbootapplication.entity.LoginUser;
    import com.spring.springbootapplication.entity.LearningData;
    import com.spring.springbootapplication.service.CategoryService;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 💡 追加


    @Controller
    @RequestMapping("/categories")
    public class CategoryController {

        @Autowired
        private CategoryService categoryService;

        // --- 一覧・編集画面表示（月ごとの絞り込み対応） ---
    // --- 一覧・編集画面表示（月ごとの絞り込み対応） ---
        @GetMapping("/edit")
        public String editCategory(@RequestParam(value = "month", required = false) String month, Model model) {

            // 1. プルダウンで月が選ばれていない（初回アクセス時など）は、現在の月をデフォルトにする
            if (month == null || month.isEmpty()) {
                month = String.valueOf(java.time.LocalDate.now().getMonthValue());
            }

            // 2. データベースからすべての学習データを取得
            List<LearningData> allLearningData = categoryService.findAllLearning_data();

            // ★ここにデバッグ用のログを追加
            System.out.println("=== 絞り込みデバッグ ===");
            System.out.println("URLから受け取った month: [" + month + "]");
            for (LearningData d : allLearningData) {
                System.out.println("DBのデータ -> id: " + d.getId() + ", item: " + d.getStudyItem() + ", study_month: [" + d.getStudyMonth() + "]");
            }
            System.out.println("========================");

            // 3. カテゴリーごとに分けるためのリストを用意する
            List<LearningData> backendList = new ArrayList<>();
            List<LearningData> frontendList = new ArrayList<>();
            List<LearningData> infraList = new ArrayList<>();

            // 4. 取得したデータを「study_month」と「category_id」で振り分ける
            for (LearningData data : allLearningData) {
                // 選択されている月と、データの study_month が一致するものだけを対象にする
                if (month.equals(data.getStudyMonth())) {
                    if (data.getCategoryId() == 1) {
                        backendList.add(data);     // バックエンド (category_id = 1)
                    } else if (data.getCategoryId() == 2) {
                        frontendList.add(data);  // フロントエンド (category_id = 2)
                    } else if (data.getCategoryId() == 3) {
                        infraList.add(data);     // インフラ (category_id = 3)
                    }
                }
            }

            // 5. HTML側にデータを渡す
            model.addAttribute("backend_data", backendList);
            model.addAttribute("frontend_data", frontendList);
            model.addAttribute("infra_data", infraList);
            
            
            return "categories/edit"; 
        }

        
        @GetMapping("/add")        
        public String addCategory(@RequestParam(value = "id", required = false) Long id, Model model) {
            model.addAttribute("CategoryRequest", new CategoryRequest());
            return "categories/add"; 
        }

        // --- 新規登録処理（現在の月を study_month に自動保存する） ---
        @PostMapping("/add")       
        public String registerCategory(
            @RequestParam(value = "id", required = false) Long id, 
            @ModelAttribute("CategoryRequest") @Validated CategoryRequest categoryRequest, 
            BindingResult result,
            Model model,
            @AuthenticationPrincipal LoginUser loginUser,
            RedirectAttributes redirectAttributes
            ) {

            // バリデーションエラーがある場合は登録画面に戻る
            if (result.hasErrors()) {
                return "categories/add";
            }

            // 1. データベース用の Entity のインスタンスを作成
            LearningData learning_data = new LearningData();

            // 2. 画面から送られてきた値を Entity にセットする
            learning_data.setCategoryId(categoryRequest.getCategory_id());
            learning_data.setUserId(loginUser.getUser().getId().intValue());
            learning_data.setStudyItem(categoryRequest.getStudy_item());
            learning_data.setStudyTime(categoryRequest.getStudy_time());

            // ★重要：登録する瞬間の「現在の月（例: 9）」を study_month にセットする
            String currentMonth = String.valueOf(java.time.LocalDate.now().getMonthValue());
            learning_data.setStudyMonth(currentMonth);

            // 3. Serviceを呼んでデータベースに保存（INSERT）する
            categoryService.addStudyItem(learning_data);

            // 動的な完了メッセージを組み立てる
            String successMessage = "カテゴリー" + " に " 
                                  + categoryRequest.getStudy_item() + " を " 
                                  + categoryRequest.getStudy_time() + " 分で追加しました！";

            model.addAttribute("successMessage", successMessage);
            model.addAttribute("CategoryRequest", new CategoryRequest()); // フォームをクリア

            return "categories/add";
       }
    }