package com.game4399.util;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class PasswordResetUtil {
    // 令牌有效期（分钟）
    private static final int TOKEN_EXPIRY_MINUTES = 60;
    private static final int TOKEN_LENGTH = 32;
    
    /**
     * 生成密码重置令牌
     * @return 随机生成的令牌
     */
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[TOKEN_LENGTH];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes).replaceAll("[^a-zA-Z0-9]", "");
    }
    
    /**
     * 保存密码重置令牌到数据库
     * @param userId 用户ID
     * @param token 重置令牌
     * @return 是否保存成功
     */
    public static boolean saveToken(int userId, String token) {
        // 计算过期时间
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String expiryDateStr = expiryDate.format(formatter);
        
        String sql = "INSERT INTO password_reset_tokens (user_id, token, expiry_date) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, token);
            pstmt.setString(3, expiryDateStr);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 验证令牌是否有效
     * @param token 要验证的令牌
     * @return 用户ID，如果令牌无效则返回-1
     */
    public static int validateToken(String token) {
        String sql = "SELECT user_id FROM password_reset_tokens WHERE token = ? AND expiry_date > NOW()";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, token);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * 删除已使用的令牌
     * @param token 要删除的令牌
     */
    public static void deleteToken(String token) {
        String sql = "DELETE FROM password_reset_tokens WHERE token = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, token);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 发送密码重置邮件
     * @param email 用户邮箱
     * @param token 重置令牌
     * @param baseUrl 应用基础URL
     * @return 是否发送成功
     */
    public static boolean sendResetEmail(String email, String token, String baseUrl) {
        // 注意：这里使用的是示例邮件配置，实际部署时需要替换为真实的邮件服务器配置
        final String username = "your-email@example.com";
        final String password = "your-email-password";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            // 构建重置链接
            String resetLink = baseUrl + "/resetPassword?token=" + token;
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("4399小游戏 - 密码重置请求");
            message.setText("亲爱的用户，\n\n" +
                           "您收到此邮件是因为我们收到了您的密码重置请求。\n\n" +
                           "请点击以下链接重置您的密码（链接将在60分钟后过期）：\n" +
                           resetLink + "\n\n" +
                           "如果您没有请求重置密码，请忽略此邮件，您的密码将保持不变。\n\n" +
                           "此致，\n" +
                           "4399小游戏团队");
            
            Transport.send(message);
            
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}