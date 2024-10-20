package com.example.library_borrow_system.service;

import com.example.library_borrow_system.model.User;
import com.example.library_borrow_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 註冊新使用者
    public User registerUser(User user) {
        // 在註冊之前，先檢查手機號碼是否已存在
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already registered.");
        }
        // 設定 registrationTime 為當前時間
        user.setRegistrationTime(LocalDateTime.now());

        // 儲存使用者，這裡應該對密碼進行加鹽並雜湊處理（略）
        return userRepository.save(user);
    }

    // 根據手機號碼查找使用者
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // 登入時更新最後登入時間
    public void updateLastLoginTime(User user) {
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);  // 更新使用者
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // 用戶登入方法
    public Optional<User> loginUser(String phoneNumber, String password) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        // 如果找到用戶並且密碼匹配，返回用戶
        if (user.isPresent() && validatePassword(password, user.get().getPasswordHash())) {
            // 更新最後登入時間
            User existingUser = user.get();
            existingUser.setLastLoginTime(LocalDateTime.now());
            userRepository.save(existingUser);

            return Optional.of(existingUser);
        }

        // 若驗證失敗，返回空的 Optional
        return Optional.empty();
    }

    // 驗證密碼（加密後的密碼驗證邏輯）
    private boolean validatePassword(String rawPassword, String hashedPassword) {
        // 加鹽後密碼的比對邏輯，例如使用BCrypt等雜湊算法進行比對
        return hashedPassword.equals(hashPassword(rawPassword));
    }
    
 // 假設的密碼加密方法 (具體的加密邏輯，例如加鹽後進行哈希)
    private String hashPassword(String password) {
        // 這裡可以使用 BCrypt 或其他加鹽雜湊算法
        return password; // 範例：簡化為直接返回密碼 (實際應加密)
    }
}
