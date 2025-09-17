package com.game4399.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.game4399.model.User;
import com.game4399.service.UserService;
import com.game4399.util.ServiceFactory;

/**
 * 用户个人中心Servlet，处理用户个人信息相关的请求
 */
@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化用户服务
        userService = ServiceFactory.getUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 获取用户信息
        User user = userService.getUserById(userId);
        
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        } else {
            // 用户不存在，显示错误信息
            request.setAttribute("errorMessage", "用户信息不存在");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 获取请求类型
        String action = request.getParameter("action");
        
        if ("updateProfile".equals(action)) {
            updateProfile(request, response, userId);
        } else if ("changePassword".equals(action)) {
            changePassword(request, response, userId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求操作");
        }
    }
    
    /**
     * 更新用户个人信息
     */
    private void updateProfile(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        // 获取表单数据
        String email = request.getParameter("email");
        
        // 数据验证
        boolean hasError = false;
        
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "请输入有效的邮箱地址");
            hasError = true;
        }
        
        if (hasError) {
            // 验证失败，返回个人中心页面
            User user = userService.getUserById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        } else {
            // 创建用户对象并更新
            User user = new User();
            user.setId(userId);
            user.setEmail(email);
            
            boolean updateSuccess = userService.updateUser(user);
            
            if (updateSuccess) {
                request.setAttribute("successMessage", "个人信息更新成功");
            } else {
                request.setAttribute("errorMessage", "个人信息更新失败，请重试");
            }
            
            // 重新获取用户信息并显示
            user = userService.getUserById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        }
    }
    
    /**
     * 修改用户密码
     */
    private void changePassword(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        // 获取表单数据
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 数据验证
        boolean hasError = false;
        
        // 验证旧密码
        if (oldPassword == null || oldPassword.isEmpty()) {
            request.setAttribute("oldPasswordError", "请输入旧密码");
            hasError = true;
        }
        
        // 验证新密码
        if (newPassword == null || newPassword.length() < 6) {
            request.setAttribute("newPasswordError", "新密码长度不能少于6位");
            hasError = true;
        } else if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            request.setAttribute("newPasswordError", "新密码至少包含字母和数字");
            hasError = true;
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "两次新密码输入不一致");
            hasError = true;
        }
        
        if (hasError) {
            // 验证失败，返回个人中心页面
            User user = userService.getUserById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
        } else {
            // 修改密码
            boolean changeSuccess = userService.changePassword(userId, oldPassword, newPassword);
            
            if (changeSuccess) {
                request.setAttribute("successMessage", "密码修改成功，请重新登录");
                // 强制用户重新登录
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "密码修改失败，旧密码不正确或系统错误");
                // 重新获取用户信息并显示
                User user = userService.getUserById(userId);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
            }
        }
    }
}