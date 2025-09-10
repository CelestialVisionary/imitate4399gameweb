package com.game4399.controller;

import com.game4399.service.FavoritesService;
import com.game4399.util.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户控制器，处理用户相关的HTTP请求，包括收藏功能
 */
@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private FavoritesService favoritesService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化收藏服务
        favoritesService = ServiceFactory.getFavoritesService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 3 || !"favorite".equals(pathParts[1])) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String action = pathParts[2];
        if ("check".equals(action)) {
            checkFavorite(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的请求操作");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 3 || !"favorite".equals(pathParts[1])) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String action = pathParts[2];
        switch (action) {
            case "add":
                addFavorite(request, response);
                break;
            case "remove":
                removeFavorite(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的请求操作");
        }
    }

    /**
     * 检查游戏是否已被收藏
     */
    private void checkFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        try {
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            boolean isFavorite = favoritesService.isFavorite(userId, gameId);
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("{\"isFavorite\": " + isFavorite + "}");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的游戏ID");
        }
    }

    /**
     * 添加游戏到收藏
     */
    private void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            boolean success = favoritesService.addFavorite(userId, gameId);
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("{\"success\": " + success + "}");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的游戏ID");
        }
    }

    /**
     * 从收藏中移除游戏
     */
    private void removeFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            boolean success = favoritesService.removeFavorite(userId, gameId);
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("{\"success\": " + success + "}");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的游戏ID");
        }
    }
}