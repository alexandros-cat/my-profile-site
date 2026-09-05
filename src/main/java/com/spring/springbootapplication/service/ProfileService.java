package com.spring.springbootapplication.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.spring.springbootapplication.dao.ProfileMapper;
import com.spring.springbootapplication.dto.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.entity.User;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileMapper profileMapper;

    // 自己紹介フォームの準備（登録済み情報の取得）
	@Transactional
	public ProfileForm getEditProfile(Long userId) {
		User user = profileMapper.selectById(userId);

		ProfileForm form = new ProfileForm();
		form.setUserId(userId);
		form.setProfile(user.getProfile());
		form.setAvatarImage(user.getAvatarImage());
		
		return form;
	}    

   // 自己紹介情報の更新処理
    @Transactional
    public void updateProfile(ProfileForm form) {
        System.out.println("【DEBUG】updateProfile 処理開始: userId = " + form.getUserId());
        
        // 1. データベースから「既存のユーザー情報」を丸ごと取得する
        User user = profileMapper.selectById(form.getUserId());
        if (user == null) {
            throw new RuntimeException("対象のユーザーが見つかりません: " + form.getUserId());
        }

        // 2. 変更があった項目（自己紹介文）だけを上書きする
        user.setProfile(form.getProfile());
        
        // 3. アップロードされたファイルがあるかチェック
        MultipartFile file = form.getAvatarFile();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String uploadDir = new java.io.File("uploads").getAbsolutePath();
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
                
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }
                
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());
                
                user.setAvatarImage("/images/" + fileName);
                System.out.println("【DEBUG】ファイルを保存しました: " + fileName);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 4. データベースの情報を更新する
        profileMapper.update(user);
        System.out.println("【DEBUG】profileMapper.update 実行完了しました");
    }
}