package com.game4399.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.game4399.service.FavoritesService;
import com.game4399.util.ServiceFactory;
import com.google.gson.Gson;

/**
 * 处理用户收藏游戏的Servlet
 */
@WebServlet("/api/favorites")
public class FavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FavoritesService favoritesService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化FavoritesService
        favoritesService = ServiceFactory.getFavoritesService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型为JSON
        response.setContentType("application/json; charset=UTF-8");
        
        // 获取当前用户的会话
        HttpSession session = request.getSession(false);
        
        // 创建响应对象
        ResponseResult result = new ResponseResult();
        
        // 检查用户是否登录
        if (session == null || session.getAttribute("userId") == null) {
            result.setSuccess(false);
            result.setMessage("请先登录");
            writeResponse(response, result);
            return;
        }
        
        try {
            // 获取用户ID和游戏ID
            int userId = (int) session.getAttribute("userId");
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            String action = request.getParameter("action");
            
            // 验证参数
            if (gameId <= 0 || action == null) {
                result.setSuccess(false);
                result.setMessage("参数无效");
                writeResponse(response, result);
                return;
            }
            
            boolean success = false;
            String message = "";
            
            // 根据action参数执行收藏或取消收藏操作
            if ("add".equals(action)) {
                success = favoritesService.addFavorite(userId, gameId);
                message = success ? "收藏成功" : "收藏失败或已收藏";
            } else if ("remove".equals(action)) {
                success = favoritesService.removeFavorite(userId, gameId);
                message = success ? "取消收藏成功" : "取消收藏失败或未收藏";
            } else {
                success = false;
                message = "未知的操作类型";
            }
            
            // 设置响应结果
            result.setSuccess(success);
            result.setMessage(message);
            
            // 如果操作成功，返回当前的收藏状态
            if (success) {
                boolean isFavorite = "add".equals(action);
                result.setData(isFavorite);
            }
            
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMessage("游戏ID格式错误");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("服务器内部错误");
        }
        
        // 写入响应
        writeResponse(response, result);
    }
    
    /**
     * 将响应对象转换为JSON并写入响应
     */
    private void writeResponse(HttpServletResponse response, ResponseResult result) throws IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
    
    /**
     * 响应结果的内部类
     */
    private static class ResponseResult {
        private boolean success;
        private String message;
        private Object data;
        
        // 添加getter方法以解决未使用字段的警告，Gson序列化时需要
        @SuppressWarnings("unused")
        public boolean getSuccess() {
            return success;
        }
        
        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        @SuppressWarnings("unused")
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        @SuppressWarnings("unused")
        public Object getData() {
            return data;
        }
        
        public void setData(Object data) {
            this.data = data;
        }
    }
}