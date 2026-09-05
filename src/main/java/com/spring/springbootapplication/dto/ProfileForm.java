    package com.spring.springbootapplication.dto;

    import java.io.Serializable;
    import jakarta.validation.constraints.NotEmpty;
    import jakarta.validation.constraints.Size;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.web.multipart.MultipartFile;


    /**
     * 自己紹介編集機能 リクエストデータ
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ProfileForm implements Serializable {
        
        /* ユーザID */
        private Long userId;	// ユーザーID

        /* ユーザID */
        @Size(min = 50, max = 200, message = "自己紹介は50文字以上200文字以下で入力してください")
        private String profile;
        
        private String avatarImage;

        private MultipartFile avatarFile;
    }