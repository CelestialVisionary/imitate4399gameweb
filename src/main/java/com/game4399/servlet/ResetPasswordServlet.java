package com.game4399.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.game4399.dao.UserDAO;
import com.game4399.util.PasswordResetUtil;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取令牌参数
        String token = request.getParameter("token");
        
        if (token == null || token.trim().isEmpty()) {
            // 令牌为空，显示无效令牌信息
            request.setAttribute("invalidToken", true);
            request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            return;
        }
        
        // 验证令牌是否有效
        int userId = PasswordResetUtil.validateToken(token);
        
        if (userId == -1) {
            // 令牌无效或已过期
            request.setAttribute("invalidToken", true);
        } else {
            // 令牌有效，保存令牌以便POST请求使用
            request.setAttribute("token", token);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 验证令牌是否有效
        int userId = PasswordResetUtil.validateToken(token);
        
        if (userId == -1) {
            // 令牌无效或已过期
            request.setAttribute("invalidToken", true);
            request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            return;
        }
        
        // 简单数据验证
        boolean hasError = false;
        if (newPassword == null || newPassword.length() < 6 || !newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
            request.setAttribute("passwordError", "密码至少6位，需包含字母和数字");
            hasError = true;
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("confirmError", "两次密码输入不一致");
            hasError = true;
        }
        
        if (hasError) {
            // 验证失败，返回重置密码页面
            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
            return;
        }
        
        // 更新用户密码
        UserDAO userDAO = new UserDAO();
        
        if (userDAO.updatePassword(userId, newPassword)) {
            // 密码更新成功，删除令牌
            PasswordResetUtil.deleteToken(token);
            
            // 显示成功消息，并提示用户登录
            request.setAttribute("successMessage", "密码重置成功，请使用新密码登录");
        } else {
            // 密码更新失败
            request.setAttribute("errorMessage", "密码重置失败，请稍后再试");
            request.setAttribute("token", token);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/resetPassword.jsp").forward(request, response);
    }
}