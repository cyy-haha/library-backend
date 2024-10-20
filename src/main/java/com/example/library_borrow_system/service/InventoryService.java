package com.example.library_borrow_system.service;

import com.example.library_borrow_system.model.Inventory;
import com.example.library_borrow_system.model.Inventory.InventoryStatus;
import com.example.library_borrow_system.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 新增書籍庫存
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
    
    // 檢查庫存是否可用來借閱
    public void checkAvailability(Long inventoryId) throws Exception {
        // 根據庫存 ID 查找對應的庫存
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new Exception("Inventory not found"));

        // 檢查庫存狀態
        if (!inventory.getStatus().equals(Inventory.InventoryStatus.AVAILABLE)) {
            throw new Exception("Book is not available for borrowing"); // 庫存不可借閱，拋出例外
        }
    }

    // 根據 ID 查詢庫存
    public Optional<Inventory> findInventoryById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }

    // 查詢所有庫存
    public List<Inventory> findAllInventories() {
        return inventoryRepository.findAll();
    }

    // 更新書籍狀態（如借閱、歸還、損壞等）
    public void updateInventoryStatus(Long inventoryId, InventoryStatus status) throws Exception {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new Exception("Inventory not found"));
        inventory.setStatus(status);
        inventoryRepository.save(inventory);
    }
}