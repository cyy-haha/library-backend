package com.example.library_borrow_system.repository;

import com.example.library_borrow_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	// 查詢所有書籍及其狀態和 inventory_id
    @Query("SELECT b, i.inventoryId, i.status AS status " +
           "FROM Book b LEFT JOIN b.inventoryList i")
    List<Object[]> findAllBooksWithStatus();
}
