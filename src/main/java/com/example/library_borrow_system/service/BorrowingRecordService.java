package com.example.library_borrow_system.service;

import com.example.library_borrow_system.model.Book;
import com.example.library_borrow_system.model.BorrowingRecord;
import com.example.library_borrow_system.model.BorrowingRecordId;
import com.example.library_borrow_system.model.Inventory;
import com.example.library_borrow_system.model.Inventory.InventoryStatus;
import com.example.library_borrow_system.model.User;
import com.example.library_borrow_system.repository.BookRepository;
import com.example.library_borrow_system.repository.BorrowingRecordRepository;
import com.example.library_borrow_system.repository.InventoryRepository;
import com.example.library_borrow_system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BorrowingRecordService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    
    // 實現借書功能
    public BorrowingRecord borrowBook(Long userId, Long inventoryId) {
        // 創建嵌入式ID
        BorrowingRecordId borrowingRecordId = new BorrowingRecordId(userId, inventoryId);

        // 創建借書記錄
        BorrowingRecord record = new BorrowingRecord();
        record.setId(borrowingRecordId);

        // 查找用戶和庫存
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new RuntimeException("Inventory not found"));

        // 更新庫存狀態為 BORROWED
        inventory.setStatus(Inventory.InventoryStatus.BORROWED);  // 假設 status 是 ENUM 類型
        inventoryRepository.save(inventory);  // 保存庫存狀態更新

        // 設置借書時間和其他相關數據
        record.setUser(user);
        record.setInventory(inventory);
        record.setBorrowingTime(LocalDateTime.now());

        // 保存借書記錄
        return borrowingRecordRepository.save(record);
    }

    // 還書功能
    public void returnBook(Long userId, Long inventoryId) throws Exception {
        // 查找尚未歸還的借書記錄
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findActiveBorrowingRecord(inventoryId)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found for this inventory."));

        // 验证归还请求者是否是借书者
        if (!borrowingRecord.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only return books that you have borrowed.");
        }

        // 設置歸還時間
        borrowingRecord.setReturnTime(LocalDateTime.now());
        borrowingRecordRepository.save(borrowingRecord);  // 保存借阅记录更新

        // 更新庫存狀態為 AVAILABLE
        Inventory inventory = borrowingRecord.getInventory();  // 获取与借阅记录相关的库存
        inventory.setStatus(Inventory.InventoryStatus.AVAILABLE);  // 将状态更新为 AVAILABLE
        inventoryRepository.save(inventory);  // 保存库存状态更新
    }


    // 查詢所有書籍及其狀態
    public List<Map<String, Object>> getAllBooksWithStatus(Long userId) {
        List<Object[]> results = bookRepository.findAllBooksWithStatus();  // 查询所有书籍及状态
        List<Map<String, Object>> booksWithStatus = new ArrayList<>();

        // 处理查询结果
        for (Object[] result : results) {
            Book book = (Book) result[0];  // 获取书籍
            Long inventoryId = (Long) result[1];  // 获取 inventory_id
            InventoryStatus statusEnum = (InventoryStatus) result[2]; // 获取状态
            String status = statusEnum.name(); // 将枚举转换为字符串

            // 获取当前借阅记录
            Optional<BorrowingRecord> borrowingRecord = borrowingRecordRepository.findActiveBorrowingRecord(inventoryId);
            

            // 如果借阅记录存在且当前用户是借书人，则可以还书
            boolean canReturn = borrowingRecord.isPresent() && borrowingRecord.get().getUser().getId().equals(userId);

            // 将书籍、inventory_id 和状态放入 Map
            Map<String, Object> bookData = new HashMap<>();
            bookData.put("book", book);
            bookData.put("inventoryId", inventoryId);
            bookData.put("status", status);
            bookData.put("canReturn", canReturn);  // 添加是否可以还书的字段

            booksWithStatus.add(bookData);  // 添加到结果列表
        }

        return booksWithStatus;  // 返回结果
    }
}
