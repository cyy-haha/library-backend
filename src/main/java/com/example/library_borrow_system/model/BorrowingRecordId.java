package com.example.library_borrow_system.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BorrowingRecordId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long userId;
    private Long inventoryId;

    // 無參數建構子
    public BorrowingRecordId() {}

    // 全參數建構子
    public BorrowingRecordId(Long userId, Long inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }

    // Getters 和 Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingRecordId that = (BorrowingRecordId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, inventoryId);
    }
}
