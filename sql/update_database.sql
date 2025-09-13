-- 更新数据库结构，添加categories表并修改games表

-- 创建categories表
CREATE TABLE IF NOT EXISTS categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- 插入分类数据
INSERT IGNORE INTO categories (id, name, description) VALUES
(1, '休闲益智', '轻松愉快的益智类游戏'),
(2, '动作冒险', '刺激的动作和冒险类游戏'),
(3, '射击游戏', '考验反应力的射击类游戏'),
(4, '策略游戏', '需要思考和策略的游戏'),
(5, '角色扮演', '扮演角色体验剧情的游戏');

-- 修改games表，添加外键约束
ALTER TABLE games
DROP COLUMN category,
ADD COLUMN category_id INT,
ADD CONSTRAINT fk_game_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL;

-- 添加category_id索引以提高查询性能
CREATE INDEX idx_games_category_id ON games(category_id);

-- 更新现有游戏的分类信息
UPDATE games
SET category_id = CASE 
    WHEN category_name = '休闲益智' THEN 1
    WHEN category_name = '动作冒险' THEN 2
    WHEN category_name = '射击游戏' THEN 3
    WHEN category_name = '策略游戏' THEN 4
    WHEN category_name = '角色扮演' THEN 5
    ELSE 1
END;

-- 确保所有表的引擎一致性
ALTER TABLE games ENGINE=InnoDB;
ALTER TABLE categories ENGINE=InnoDB;
ALTER TABLE comments ENGINE=InnoDB;
ALTER TABLE users ENGINE=InnoDB;
ALTER TABLE user_favorites ENGINE=InnoDB;

-- 检查并修复所有表
CHECK TABLE games, categories, comments, users, user_favorites;