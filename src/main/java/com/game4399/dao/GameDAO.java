package com.game4399.dao;

import com.game4399.model.Game;
import com.game4399.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏数据访问对象，负责与数据库中的games表交互
 */
public class GameDAO {
    
    /**
     * 根据ID获取游戏
     * @param id 游戏ID
     * @return 游戏对象
     */
    public Game getGameById(int id) {
        Game game = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM games WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                game = mapResultSetToGame(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return game;
    }
    
    /**
     * 获取所有游戏
     * @return 游戏列表
     */
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM games ORDER BY id";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return games;
    }
    
    /**
     * 根据分类获取游戏
     * @param category 游戏分类
     * @return 游戏列表
     */
    public List<Game> getGamesByCategory(String category) {
        List<Game> games = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM games WHERE category = ? ORDER BY id";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return games;
    }
    
    /**
     * 搜索游戏
     * @param keyword 搜索关键词
     * @return 游戏列表
     */
    public List<Game> searchGames(String keyword) {
        return searchGames(keyword, null);
    }
    
    /**
     * 搜索游戏（支持排序）
     * @param keyword 搜索关键词
     * @param sort 排序字段（playCount或rating）
     * @return 游戏列表
     */
    public List<Game> searchGames(String keyword, String sort) {
        List<Game> games = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            
            // 基础SQL查询
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM games WHERE title LIKE ? OR description LIKE ? OR category LIKE ?");
            
            // 根据排序字段调整ORDER BY子句
            if (sort != null && !sort.isEmpty()) {
                if ("playCount".equals(sort)) {
                    sqlBuilder.append(" ORDER BY play_count DESC");
                } else if ("rating".equals(sort)) {
                    sqlBuilder.append(" ORDER BY rating DESC");
                } else {
                    sqlBuilder.append(" ORDER BY id");
                }
            } else {
                sqlBuilder.append(" ORDER BY id");
            }
            
            stmt = conn.prepareStatement(sqlBuilder.toString());
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return games;
    }
    
    /**
     * 获取热门游戏
     * @param limit 获取的数量
     * @return 热门游戏列表
     */
    public List<Game> getHotGames(int limit) {
        List<Game> games = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM games ORDER BY play_count DESC LIMIT ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return games;
    }
    
    /**
     * 更新游戏播放次数
     * @param gameId 游戏ID
     */
    public void updatePlayCount(int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE games SET play_count = play_count + 1 WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, null);
        }
    }
    
    /**
     * 获取相关游戏
     * @param gameId 当前游戏ID
     * @param category 当前游戏分类
     * @param limit 获取的数量
     * @return 相关游戏列表
     */
    public List<Game> getRelatedGames(int gameId, String category, int limit) {
        List<Game> games = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM games WHERE id != ? AND category = ? ORDER BY play_count DESC LIMIT ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            stmt.setString(2, category);
            stmt.setInt(3, limit);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return games;
    }
    
    /**
     * 为用户推荐游戏
     * @param userId 用户ID
     * @param limit 获取的数量
     * @return 推荐游戏列表
     */
    public List<Game> recommendGamesForUser(int userId, int limit) {
        List<Game> recommendedGames = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            // 1. 获取用户评论过的游戏分类及其权重
            String sql = "SELECT g.category, COUNT(*) as category_count " +
                         "FROM comments c JOIN games g ON c.game_id = g.id " +
                         "WHERE c.user_id = ? " +
                         "GROUP BY g.category " +
                         "ORDER BY category_count DESC LIMIT 2";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            List<String> preferredCategories = new ArrayList<>();
            while (rs.next()) {
                preferredCategories.add(rs.getString("category"));
            }
            
            // 2. 如果用户有评论历史，基于偏好分类推荐热门游戏
            if (!preferredCategories.isEmpty()) {
                rs.close();
                stmt.close();
                
                StringBuilder categoryInClause = new StringBuilder();
                for (int i = 0; i < preferredCategories.size(); i++) {
                    if (i > 0) categoryInClause.append(",");
                    categoryInClause.append("?");
                }
                
                sql = "SELECT * FROM games WHERE category IN (" + categoryInClause + ") " +
                      "AND id NOT IN (SELECT game_id FROM comments WHERE user_id = ?) " +
                      "ORDER BY play_count DESC LIMIT ?";
                stmt = conn.prepareStatement(sql);
                
                // 设置分类参数
                for (int i = 0; i < preferredCategories.size(); i++) {
                    stmt.setString(i + 1, preferredCategories.get(i));
                }
                
                // 设置用户ID和限制数量参数
                stmt.setInt(preferredCategories.size() + 1, userId);
                stmt.setInt(preferredCategories.size() + 2, limit);
                
                rs = stmt.executeQuery();
                
                while (rs.next()) {
                    recommendedGames.add(mapResultSetToGame(rs));
                }
            }
            
            // 3. 如果没有足够的推荐结果，使用热门游戏作为补充
            if (recommendedGames.size() < limit) {
                rs.close();
                stmt.close();
                
                int remainingSlots = limit - recommendedGames.size();
                sql = "SELECT * FROM games ORDER BY play_count DESC LIMIT ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, remainingSlots);
                rs = stmt.executeQuery();
                
                while (rs.next()) {
                    Game game = mapResultSetToGame(rs);
                    boolean alreadyRecommended = false;
                    for (Game recommended : recommendedGames) {
                        if (recommended.getId() == game.getId()) {
                            alreadyRecommended = true;
                            break;
                        }
                    }
                    if (!alreadyRecommended) {
                        recommendedGames.add(game);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return recommendedGames;
    }
    
    /**
     * 将结果集映射为游戏对象
     * @param rs 结果集
     * @return 游戏对象
     * @throws SQLException
     */
    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setId(rs.getInt("id"));
        game.setName(rs.getString("title")); // 注意：数据库字段是title，模型类属性是name
        game.setCategory(rs.getString("category"));
        game.setDescription(rs.getString("description"));
        game.setImageUrl(rs.getString("image_url"));
        game.setPlayUrl(rs.getString("play_url"));
        game.setPlayCount(rs.getInt("play_count"));
        game.setRating(rs.getDouble("rating"));
        return game;
    }
}