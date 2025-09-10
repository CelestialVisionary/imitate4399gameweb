-- 4399小游戏网站数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS game4399db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换到game4399db数据库
USE game4399db;

-- 创建users表
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    register_time DATETIME NOT NULL,
    last_login_time DATETIME,
    status TINYINT DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建索引以提高查询性能
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);

-- 创建游戏表
CREATE TABLE IF NOT EXISTS games (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    play_url VARCHAR(255) NOT NULL,
    play_count INT DEFAULT 0,
    rating FLOAT DEFAULT 0,
    release_date DATE,
    is_hot TINYINT DEFAULT 0,
    is_new TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建游戏评论表
CREATE TABLE IF NOT EXISTS comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    game_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    rating INT NOT NULL,
    comment_time DATETIME NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO users (username, password, email, register_time) VALUES 
('testuser', 'test123', 'test@example.com', NOW());

-- 插入游戏数据
INSERT INTO games (title, description, category, image_url, play_url, play_count, rating, release_date, is_hot, is_new) VALUES
('Minecraft', 'Classic sandbox construction game', 'Sandbox', 'images/mc.svg', 'play/mc.jsp', 1250000, 4.8, '2011-11-18', 1, 0),
('Mini World', '3D sandbox creative game', 'Sandbox', 'images/mnsj.svg', 'play/mnsj.jsp', 980000, 4.6, '2015-08-01', 1, 0),
('Peacekeeper Elite', 'Tactical competitive shooting game', 'Shooting', 'images/hpjy.svg', 'play/hpjy.jsp', 2500000, 4.7, '2019-05-08', 1, 0),
('Honor of Kings', 'Multiplayer online battle arena', 'MOBA', 'images/wzry.svg', 'play/wzry.jsp', 3800000, 4.9, '2015-11-26', 1, 0),
('Jigsaw Puzzle', 'Casual puzzle challenge', 'Puzzle', 'images/puzzle.svg', 'play/puzzle.jsp', 320000, 4.5, '2023-01-15', 0, 1),
('Racing Game', 'Speed racing experience', 'Racing', 'images/racing.svg', 'play/racing.jsp', 450000, 4.4, '2022-10-20', 0, 1),
('Shooting Game', 'Classic shooting game', 'Shooting', 'images/shooter.svg', 'play/shooter.jsp', 680000, 4.3, '2022-05-10', 0, 0);

-- 授予权限
-- 注意：在实际生产环境中，应使用更严格的权限设置
GRANT ALL PRIVILEGES ON game4399db.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- 显示创建的表
SHOW TABLES;