package com.game4399.service.impl;

import com.game4399.dao.UserDAO;
import com.game4399.model.User;
import com.game4399.service.UserService;

/**
 * 用户服务实现类，实现用户相关的业务逻辑
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    
    public UserServiceImpl() {
        this.userDAO = new UserDAO();
    }
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (userDAO.usernameExists(user.getUsername())) {
            return false;
        }
        
        // 执行注册操作
        return userDAO.register(user);
    }
    
    @Override
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }
    
    @Override
    public boolean usernameExists(String username) {
        return userDAO.usernameExists(username);
    }
    
    @Override
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
    
    @Override
    public boolean updateUser(User user) {
        // 验证用户ID是否有效
        if (user == null || user.getId() <= 0) {
            return false;
        }
        
        // 检查用户是否存在
        User existingUser = userDAO.getUserById(user.getId());
        if (existingUser == null) {
            return false;
        }
        
        return userDAO.updateUser(user);
    }
    
    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // 验证密码长度是否符合要求
        if (newPassword == null || newPassword.length() < 6) {
            return false;
        }
        
        return userDAO.changePassword(userId, oldPassword, newPassword);
    }
}