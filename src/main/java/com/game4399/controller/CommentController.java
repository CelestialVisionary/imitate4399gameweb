package com.game4399.controller;

import com.game4399.model.Comment;
import com.game4399.service.CommentService;
import com.game4399.service.impl.CommentServiceImpl;
import com.game4399.util.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 评论控制器，处理与评论相关的HTTP请求
 */
@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
    private CommentService commentService;
    
    @Override
    public void init() throws ServletException {
        // 使用服务工厂获取CommentService实例
        commentService = ServiceFactory.getCommentService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        // 根据路径信息处理不同的请求
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String action = pathParts[1];
        
        switch (action) {
            case "game":
                // 获取游戏评论
                if (pathParts.length > 2) {
                    try {
                        int gameId = Integer.parseInt(pathParts[2]);
                        getGameComments(gameId, request, response);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的游戏ID");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少游戏ID");
                }
                break;
            case "rating":
                // 获取游戏评分
                if (pathParts.length > 2) {
                    try {
                        int gameId = Integer.parseInt(pathParts[2]);
                        getGameRating(gameId, response);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的游戏ID");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少游戏ID");
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的请求操作");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            addComment(request, response);
            return;
        }
        
        // 根据路径信息处理不同的请求
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的请求路径");
            return;
        }
        
        String action = pathParts[1];
        
        switch (action) {
            case "add":
                addComment(request, response);
                break;
            case "delete":
                if (pathParts.length > 2) {
                    try {
                        int commentId = Integer.parseInt(pathParts[2]);
                        deleteComment(commentId, request, response);
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的评论ID");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少评论ID");
                }
                break;
            case "update":
                updateComment(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "未知的请求操作");
        }
    }
    
    /**
     * 获取游戏评论
     */
    private void getGameComments(int gameId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Comment> comments = commentService.getCommentsByGameId(gameId);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/views/comment_list.jsp").forward(request, response);
    }
    
    /**
     * 获取游戏评分
     */
    private void getGameRating(int gameId, HttpServletResponse response) throws IOException {
        double rating = commentService.calculateAverageRating(gameId);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{\"rating\": " + rating + "}");
        out.flush();
    }
    
    /**
     * 添加评论
     */
    private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // 获取请求参数
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            String content = request.getParameter("content");
            int rating = Integer.parseInt(request.getParameter("rating"));
            
            // 创建评论对象
            Comment comment = new Comment();
            comment.setGameId(gameId);
            comment.setUserId(userId);
            comment.setContent(content);
            comment.setRating(rating);
            
            // 添加评论
            boolean success = commentService.addComment(comment);
            
            if (success) {
                // 评论添加成功，重定向到游戏详情页
                response.sendRedirect(request.getContextPath() + "/game/" + gameId);
            } else {
                // 评论添加失败
                request.setAttribute("errorMessage", "评论添加失败，请重试");
                request.getRequestDispatcher("/game/" + gameId).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 删除评论
     */
    private void deleteComment(int commentId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 获取要删除的评论
        Comment comment = commentService.getCommentsByUserId(userId).stream()
                .filter(c -> c.getId() == commentId)
                .findFirst()
                .orElse(null);
        
        if (comment == null) {
            request.setAttribute("errorMessage", "您无权删除此评论");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        boolean success = commentService.deleteComment(commentId);
        
        if (success) {
            // 评论删除成功，重定向到游戏详情页
            response.sendRedirect(request.getContextPath() + "/game/" + comment.getGameId());
        } else {
            // 评论删除失败
            request.setAttribute("errorMessage", "评论删除失败，请重试");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新评论
     */
    private void updateComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            // 获取请求参数
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            String content = request.getParameter("content");
            int rating = Integer.parseInt(request.getParameter("rating"));
            
            // 获取要更新的评论
            Comment comment = commentService.getCommentsByUserId(userId).stream()
                    .filter(c -> c.getId() == commentId)
                    .findFirst()
                    .orElse(null);
            
            if (comment == null) {
                request.setAttribute("errorMessage", "您无权更新此评论");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            // 更新评论内容
            comment.setContent(content);
            comment.setRating(rating);
            
            boolean success = commentService.updateComment(comment);
            
            if (success) {
                // 评论更新成功，重定向到游戏详情页
                response.sendRedirect(request.getContextPath() + "/game/" + comment.getGameId());
            } else {
                // 评论更新失败
                request.setAttribute("errorMessage", "评论更新失败，请重试");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的参数格式");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}