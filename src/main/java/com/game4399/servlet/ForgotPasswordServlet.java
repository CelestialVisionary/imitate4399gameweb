package com.game4399.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.game4399.dao.UserDAO;
import com.game4399.model.User;
import com.game4399.util.PasswordResetUtil;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理GET请求，跳转到找回密码页面
        request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        
        // 简单数据验证
        boolean hasError = false;
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "用户名不能为空");
            hasError = true;
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "请输入有效的邮箱地址");
            hasError = true;
        }
        
        if (hasError) {
            // 验证失败，返回找回密码页面
            request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
            return;
        }
        
        // 验证用户名和邮箱是否匹配
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);
        
        if (user == null || !user.getUsername().equals(username)) {
            request.setAttribute("errorMessage", "用户名和邮箱不匹配");
            request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
            return;
        }
        
        int userId = user.getId();
        
        // 生成重置令牌
        String token = PasswordResetUtil.generateToken();
        
        // 保存令牌到数据库
        if (!PasswordResetUtil.saveToken(userId, token)) {
            request.setAttribute("errorMessage", "生成重置链接失败，请稍后再试");
            request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
            return;
        }
        
        // 发送重置邮件
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        boolean emailSent = PasswordResetUtil.sendResetEmail(email, token, baseUrl);
        
        if (emailSent) {
            // 邮件发送成功，显示成功消息
            request.setAttribute("successMessage", "重置密码链接已发送到您的邮箱，请在60分钟内点击链接重置密码");
        } else {
            // 邮件发送失败
            request.setAttribute("errorMessage", "发送重置邮件失败，请稍后再试");
        }
        
        request.getRequestDispatcher("/WEB-INF/views/forgotPassword.jsp").forward(request, response);
    }
}