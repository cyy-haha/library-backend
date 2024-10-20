package com.example.library_borrow_system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_borrow_system.service.BorrowingRecordService;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    // 借書
    @PostMapping("/borrow")
    public ResponseEntity<Map<String, String>> borrowBook(@RequestParam Long userId, @RequestParam Long inventoryId) {
    	Map<String, String> response = new HashMap<>();
    	try {
            borrowingRecordService.borrowBook(userId, inventoryId);
            response.put("message", "Book borrowed successfully");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 還書
    @PostMapping("/return")
    public ResponseEntity<Map<String, String>> returnBook(@RequestParam Long userId, @RequestParam Long inventoryId) {
        Map<String, String> response = new HashMap<>();
    	try {
    		borrowingRecordService.returnBook(userId, inventoryId);
            response.put("message", "Book returned successfully");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 查詢所有書籍及其狀態
    @GetMapping("/books")
    public ResponseEntity<List<Map<String, Object>>> getAllBooks(@RequestParam Long userId) {
        List<Map<String, Object>> booksWithStatus = borrowingRecordService.getAllBooksWithStatus(userId);  // 传递 userId
        return ResponseEntity.ok(booksWithStatus);
    }
}
