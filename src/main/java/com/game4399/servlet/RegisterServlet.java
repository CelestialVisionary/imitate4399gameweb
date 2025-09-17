package com.game4399.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.game4399.model.User;
import com.game4399.service.UserService;
import com.game4399.util.ServiceFactory;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化用户服务
        userService = ServiceFactory.getUserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // 数据验证
        boolean hasError = false;
        
        // 验证用户名
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "用户名不能为空");
            hasError = true;
        } else if (username.length() < 3 || username.length() > 20) {
            request.setAttribute("usernameError", "用户名长度应在3-20个字符之间");
            hasError = true;
        } else if (userService.usernameExists(username)) {
            request.setAttribute("usernameError", "用户名已存在");
            hasError = true;
        }
        
        // 验证密码
        if (password == null || password.length() < 6) {
            request.setAttribute("passwordError", "密码长度不能少于6位");
            hasError = true;
        } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            request.setAttribute("passwordError", "密码至少包含字母和数字");
            hasError = true;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmError", "两次密码输入不一致");
            hasError = true;
        }
        
        // 验证邮箱
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "请输入有效的邮箱地址");
            hasError = true;
        }

        if (hasError) {
            // 验证失败，返回注册页面
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            // 创建用户对象并保存
            User newUser = new User(username, password, email);
            boolean registerSuccess = userService.register(newUser);
            
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