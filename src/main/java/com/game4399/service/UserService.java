package com.game4399.service;

import com.game4399.model.User;

/**
 * 用户服务接口，定义用户相关的业务逻辑
 */
public interface UserService {
    
    /**
     * 用户注册
     * @param user 用户对象
     * @return 注册是否成功
     */
    boolean register(User user);
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    User login(String username, String password);
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 用户名是否已存在
     */
    boolean usernameExists(String username);
    
    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserById(int userId);
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新是否成功
     */
    boolean updateUser(User user);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改是否成功
     */
    boolean changePassword(int userId, String oldPassword, String newPassword);
}