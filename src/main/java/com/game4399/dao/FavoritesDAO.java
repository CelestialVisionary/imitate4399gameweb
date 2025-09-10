package com.game4399.dao;

import com.game4399.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 收藏数据访问对象，负责与数据库中的user_favorites表交互
 */
public class FavoritesDAO {

    /**
     * 添加收藏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否添加成功
     */
    public boolean addFavorite(int userId, int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO user_favorites (user_id, game_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn, stmt, null);
        }
    }
    
    /**
     * 取消收藏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否取消成功
     */
    public boolean removeFavorite(int userId, int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM user_favorites WHERE user_id = ? AND game_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn, stmt, null);
        }
    }
    
    /**
     * 检查用户是否已收藏指定游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否已收藏
     */
    public boolean isFavorite(int userId, int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) as count FROM user_favorites WHERE user_id = ? AND game_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
    }
    
    /**
     * 获取用户收藏的所有游戏ID
     * @param userId 用户ID
     * @return 游戏ID列表
     */
    public List<Integer> getUserFavoriteGameIds(int userId) {
        List<Integer> gameIds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT game_id FROM user_favorites WHERE user_id = ? ORDER BY created_at DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                gameIds.add(rs.getInt("game_id"));
            }
            return gameIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return gameIds;
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
    }
    
    /**
     * 获取用户收藏的游戏数量
     * @param userId 用户ID
     * @return 收藏数量
     */
    public int getUserFavoriteCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) as count FROM user_favorites WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
    }
    
    /**
     * 获取收藏指定游戏的用户数量
     * @param gameId 游戏ID
     * @return 收藏用户数量
     */
    public int getGameFavoriteCount(int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) as count FROM user_favorites WHERE game_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
    }
}