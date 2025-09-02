package com.game4399.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";

    /**
     * 生成随机盐值
     * @return 随机生成的盐值
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 对密码进行哈希处理
     * @param password 原始密码
     * @param salt 盐值
     * @return 哈希后的密码
     */
    private static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码哈希算法不可用", e);
        }
    }

    /**
     * 加密密码（生成盐值并哈希）
     * @param password 原始密码
     * @return 加密后的密码（包含盐值）
     */
    public static String encryptPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password, salt);
        
        // 将盐值和哈希后的密码组合并编码为Base64字符串
        byte[] combined = new byte[salt.length + hashedPassword.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 验证密码是否匹配
     * @param password 待验证的原始密码
     * @param storedPassword 存储的加密密码
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String password, String storedPassword) {
        try {
            // 解码存储的密码
            byte[] combined = Base64.getDecoder().decode(storedPassword);
            byte[] salt = new byte[SALT_LENGTH];
            byte[] storedHash = new byte[combined.length - SALT_LENGTH];
            
            // 提取盐值和哈希值
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(combined, SALT_LENGTH, storedHash, 0, storedHash.length);
            
            // 计算输入密码的哈希值
            byte[] inputHash = hashPassword(password, salt);
            
            // 比较哈希值是否相同
            return MessageDigest.isEqual(inputHash, storedHash);
        } catch (Exception e) {
            throw new RuntimeException("密码验证失败", e);
        }
    }
}