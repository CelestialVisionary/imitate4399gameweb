package com.game4399.dao;

import com.game4399.model.Comment;
import com.game4399.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论数据访问对象，负责与数据库中的comments表交互
 */
public class CommentDAO {
    
    /**
     * 添加新评论
     * @param comment 评论对象
     * @return 是否添加成功
     */
    public boolean addComment(Comment comment) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO comments (game_id, user_id, content, rating) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, comment.getGameId());
            stmt.setInt(2, comment.getUserId());
            stmt.setString(3, comment.getContent());
            stmt.setInt(4, comment.getRating());
            
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
     * 获取指定游戏的所有评论
     * @param gameId 游戏ID
     * @return 评论列表
     */
    public List<Comment> getCommentsByGameId(int gameId) {
        List<Comment> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.*, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.game_id = ? ORDER BY c.comment_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = mapResultSetToComment(rs);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return comments;
    }
    
    /**
     * 获取指定用户的所有评论
     * @param userId 用户ID
     * @return 评论列表
     */
    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.*, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.user_id = ? ORDER BY c.comment_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = mapResultSetToComment(rs);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return comments;
    }
    
    /**
     * 根据ID删除评论
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    public boolean deleteComment(int commentId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM comments WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, commentId);
            
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
     * 更新评论
     * @param comment 评论对象
     * @return 是否更新成功
     */
    public boolean updateComment(Comment comment) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE comments SET content = ?, rating = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getRating());
            stmt.setInt(3, comment.getId());
            
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
     * 计算游戏的平均评分
     * @param gameId 游戏ID
     * @return 平均评分（保留一位小数）
     */
    public double calculateAverageRating(int gameId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT AVG(rating) as avg_rating FROM comments WHERE game_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Double avgRating = rs.getDouble("avg_rating");
                return avgRating != null ? Math.round(avgRating * 10) / 10.0 : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return 0.0;
    }
    
    /**
     * 根据ID获取评论
     * @param commentId 评论ID
     * @return 评论对象
     */
    public Comment getCommentById(int commentId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.*, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, commentId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToComment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn, stmt, rs);
        }
        
        return null;
    }
    
    /**
     * 将结果集映射为评论对象
     * @param rs 结果集
     * @return 评论对象
     * @throws SQLException SQL异常
     */
    private Comment mapResultSetToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setGameId(rs.getInt("game_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUsername(rs.getString("username"));
        comment.setContent(rs.getString("content"));
        comment.setRating(rs.getInt("rating"));
        comment.setCommentTime(rs.getTimestamp("comment_time"));
        return comment;
    }
}