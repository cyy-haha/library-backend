package com.example.library_borrow_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.library_borrow_system.model.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    // 使用 JPQL 查詢尚未歸還的借閱記錄
    @Query("SELECT br FROM BorrowingRecord br WHERE br.inventory.inventoryId = :inventoryId AND br.returnTime IS NULL")
    Optional<BorrowingRecord> findActiveBorrowingRecord(@Param("inventoryId") Long inventoryId);
    
}