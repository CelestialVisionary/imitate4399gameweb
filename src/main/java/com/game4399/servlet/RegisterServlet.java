package com.game4399.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.game4399.dao.UserDAO;
import com.game4399.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // 简单数据验证
        boolean hasError = false;
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "用户名不能为空");
            hasError = true;
        }
        if (password == null || password.length() < 6) {
            request.setAttribute("passwordError", "密码长度不能少于6位");
            hasError = true;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmError", "两次密码输入不一致");
            hasError = true;
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "请输入有效的邮箱地址");
            hasError = true;
        }

        if (hasError) {
            // 验证失败，返回注册页面
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            // 使用UserDAO进行数据库注册
            UserDAO userDAO = new UserDAO();
            
            // 检查用户名是否已存在
            if (userDAO.usernameExists(username)) {
                request.setAttribute("usernameError", "用户名已存在");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }
            
            // 创建用户对象并保存
            User newUser = new User(username, password, email);
            boolean registerSuccess = userDAO.register(newUser);
            
            if (registerSuccess) {
                request.setAttribute("successMessage", "注册成功！请登录");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "注册失败，请重试");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理GET请求，跳转到注册页面
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
}