CREATE DATABASE library_system;
USE library_system;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 唯一值，主鍵
    phone_number VARCHAR(15) NOT NULL UNIQUE,      -- 手機號碼，登入用且不可重複
    password_hash VARCHAR(255) NOT NULL,           -- 雜湊後的密碼
    user_name VARCHAR(50) NOT NULL,                -- 使用者名稱
    registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 註冊時間
    last_login_time TIMESTAMP NULL                 -- 最後登入時間
);

CREATE TABLE books (
    isbn VARCHAR(13) PRIMARY KEY,                    -- ISBN，主鍵
    name VARCHAR(255) NOT NULL,                      -- 書名
    author VARCHAR(100) NOT NULL,                    -- 作者
    introduction TEXT                                -- 書籍簡介
);

CREATE TABLE inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 庫存ID，主鍵
    isbn VARCHAR(13) NOT NULL,                        -- 國際標準書號，關聯到書籍
    store_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 書籍入庫時間
    status ENUM('AVAILABLE', 'BORROWED', 'MAINTENANCE', 'LOST', 'DAMAGED', 'DISCARDED') NOT NULL DEFAULT 'available'  -- 書籍狀態
);

CREATE TABLE borrowing_record (
    user_id BIGINT NOT NULL,                         -- 使用者ID，外鍵
    inventory_id BIGINT NOT NULL,                    -- 庫存ID，外鍵
    borrowing_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 借出時間
    return_time TIMESTAMP NULL,                      -- 歸還時間
    PRIMARY KEY (user_id, inventory_id)             -- 組合鍵，唯一記錄
);

-- 插入書籍資料
INSERT INTO books (isbn, name, author, introduction) VALUES
('9780134685991', 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 'This book offers practical advice for writing clean, efficient, and maintainable code.'),
('9780132350884', 'The Clean Coder: A Code of Conduct for Professional Programmers', 'Robert C. Martin', 'In this book, the author emphasizes the importance of professionalism and discipline in coding.'),
('9780596517748', 'Head First Java', 'Kathy Sierra, Bert Bates', 'A beginner-friendly guide to learning Java, with a focus on understanding concepts through fun and engaging examples.'),
('9780201616224', 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', 'A comprehensive guide to 23 classic design patterns used in object-oriented software development.'),
('9780321356680', 'Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 'This book teaches how to improve your existing code by refactoring it to be more readable, maintainable, and efficient.');

-- 插入庫存資料
INSERT INTO inventory (isbn, store_time, status) VALUES
('9780134685991', '2024-01-15 10:30:00', 'AVAILABLE'),
('9780132350884', '2024-01-17 09:00:00', 'AVAILABLE'),
('9780596517748', '2024-01-10 12:45:00', 'MAINTENANCE'),
('9780201616224', '2024-01-11 11:50:00', 'LOST'),
('9780321356680', '2024-01-12 08:30:00', 'AVAILABLE');