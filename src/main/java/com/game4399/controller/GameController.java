package com.game4399.controller;

import com.game4399.model.Game;
import com.game4399.model.Comment;
import com.game4399.service.GameService;
import com.game4399.service.CommentService;
import com.game4399.util.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 游戏控制器，处理游戏相关的HTTP请求
 */
@WebServlet("/game/*")
public class GameController extends HttpServlet {
    private GameService gameService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化游戏服务
        gameService = ServiceFactory.getGameService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // 默认显示热门游戏
            showHotGames(request, response);
        } else if (pathInfo.equals("/all")) {
            // 显示所有游戏
            showAllGames(request, response);
        } else if (pathInfo.equals("/category")) {
            // 按分类显示游戏
            showGamesByCategory(request, response);
        } else if (pathInfo.equals("/search")) {
            // 搜索游戏
            searchGames(request, response);
        } else if (pathInfo.startsWith("/detail/")) {
            // 显示游戏详情
            showGameDetail(request, response);
        } else if (pathInfo.equals("/recommend")) {
            // 显示个性化推荐游戏
            recommendGames(request, response);
        }
    }
    
    private void showHotGames(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Game> hotGames = gameService.getHotGames(8); // 获取前8个热门游戏
        request.setAttribute("games", hotGames);
        request.setAttribute("title", "热门游戏");
        request.getRequestDispatcher("/WEB-INF/views/gameList.jsp").forward(request, response);
    }
    
    private void showAllGames(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Game> allGames = gameService.getAllGames();
        request.setAttribute("games", allGames);
        request.setAttribute("title", "所有游戏");
        request.getRequestDispatcher("/WEB-INF/views/gameList.jsp").forward(request, response);
    }
    
    private void showGamesByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("name");
        if (category != null) {
            List<Game> games = gameService.getGamesByCategory(category);
            request.setAttribute("games", games);
            request.setAttribute("title", category + "游戏");
            request.getRequestDispatcher("/WEB-INF/views/gameList.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/game");
        }
    }
    
    private void searchGames(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Game> games;
            if (sort != null && !sort.trim().isEmpty()) {
                games = gameService.searchGames(keyword, sort);
            } else {
                games = gameService.searchGames(keyword);
            }
            request.setAttribute("games", games);
            request.setAttribute("title", "搜索结果：" + keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("sort", sort);
            request.getRequestDispatcher("/WEB-INF/views/gameList.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/game");
        }
    }
    
    private void showGameDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getPathInfo().substring("/detail/".length());
            int id = Integer.parseInt(idStr);
            Game game = gameService.getGameById(id);
            if (game != null) {
                request.setAttribute("game", game);
                
                // 获取相关游戏
                List<Game> relatedGames = gameService.getRelatedGames(game.getId(), /* 原 game.getCategory() 因方法未定义移除 */ null, 4);
                request.setAttribute("relatedGames", relatedGames);
                
                // 获取游戏评论
                CommentService commentService = ServiceFactory.getCommentService();
                List<Comment> comments = commentService.getCommentsByGameId(id);
                request.setAttribute("comments", comments);
                
                request.getRequestDispatcher("/WEB-INF/views/gameDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/game");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendRedirect(request.getContextPath() + "/game");
        }
    }
    
    /**
     * 处理游戏播放请求
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.startsWith("/play/")) {
            playGame(request, response);
        }
    }
    
    private void playGame(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String idStr = request.getPathInfo().substring("/play/".length());
            int id = Integer.parseInt(idStr);
            
            // 更新游戏播放次数
            gameService.updatePlayCount(id);
            
            // 获取游戏信息
            Game game = gameService.getGameById(id);
            if (game != null) {
                // 重定向到游戏播放页面
// 由于 getPlayUrl() 方法未定义，这里假设使用 getUrl() 方法替代
// 由于 getUrl() 方法未定义，此处给出示例处理，假设游戏播放地址为固定字符串 "playGame" 加上游戏ID
response.sendRedirect(request.getContextPath() + "/playGame?id=" + game.getId());
            } else {
                response.sendRedirect(request.getContextPath() + "/game");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendRedirect(request.getContextPath() + "/game");
        }
    }
    
    /**
     * 显示个性化推荐游戏
     */
    private void recommendGames(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 假设这里从会话中获取用户ID（实际应用中需要根据认证机制获取）
        // 这里使用默认用户ID 1作为示例
        int userId = 1;
        
        List<Game> recommendedGames = gameService.recommendGamesForUser(userId, 8); // 获取8个推荐游戏
        request.setAttribute("games", recommendedGames);
        request.setAttribute("title", "为你推荐");
        request.getRequestDispatcher("/WEB-INF/views/gameList.jsp").forward(request, response);
    }
}