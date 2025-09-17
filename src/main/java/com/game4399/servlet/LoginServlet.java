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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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

        // 验证输入数据
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "用户名和密码不能为空");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // 使用UserService进行登录验证
        User user = userService.login(username, password);
        
        if (user != null) {
            // 登录成功，创建会话
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId()); // 存储用户ID，方便后续操作
            session.setMaxInactiveInterval(3600); // 设置会话有效期为1小时
            // 重定向到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // 登录失败，返回登录页面并显示错误信息
            request.setAttribute("errorMessage", "用户名或密码错误");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理GET请求，跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}