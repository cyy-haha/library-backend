package com.example.library_borrow_system.controller;

import com.example.library_borrow_system.model.User;
import com.example.library_borrow_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 註冊功能
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.registerUser(user);
            response.put("message", "User registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 登入功能
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User loginUser) {
        Optional<User> user = userService.loginUser(loginUser.getPhoneNumber(), loginUser.getPasswordHash());

        if (user.isPresent()) {
            // 創建一個 Map 來存儲返回的數據
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("userId", user.get().getId());

            // 返回包含 userId 的響應
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            // 登錄失敗，返回錯誤消息
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Invalid credentials");

            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
    }

}