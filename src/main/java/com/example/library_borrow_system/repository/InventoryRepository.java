package com.example.library_borrow_system.repository;

import com.example.library_borrow_system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
