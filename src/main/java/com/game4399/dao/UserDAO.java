package com.game4399.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.game4399.model.User;
import com.game4399.util.DBUtil;
import com.game4399.util.PasswordUtil;

public class UserDAO {
    // 获取数据库连接
    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection();
    }

    // 用户注册 - 保存用户到数据库
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, email, register_time) VALUES (?, ?, ?, NOW())";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            // 使用PasswordUtil加密密码
            pstmt.setString(2, PasswordUtil.encryptPassword(user.getPassword()));
            pstmt.setString(3, user.getEmail());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录 - 根据用户名和密码查询用户
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // 使用PasswordUtil验证密码
                    if (PasswordUtil.verifyPassword(password, storedPassword)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(storedPassword); // 存储加密后的密码
                        user.setEmail(rs.getString("email"));
                        user.setRegisterTime(rs.getString("register_time"));
                        
                        // 更新最后登录时间
                        updateLastLoginTime(rs.getInt("id"));
                        
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 更新用户最后登录时间
    private void updateLastLoginTime(int userId) {
        String sql = "UPDATE users SET last_login_time = NOW() WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 检查用户名是否已存在
    public boolean usernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}