-- 创建密码重置令牌表
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expiry_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 创建唯一索引以防止重复的令牌
CREATE UNIQUE INDEX idx_token ON password_reset_tokens(token);

-- 创建索引以加快用户ID的查询
CREATE INDEX idx_user_id ON password_reset_tokens(user_id);