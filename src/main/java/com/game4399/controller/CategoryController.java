package com.game4399.controller;

import com.game4399.model.Category;
import com.game4399.service.CategoryService;
import com.game4399.util.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类控制器，处理分类相关的HTTP请求
 */
@WebServlet("/category/*")
public class CategoryController extends HttpServlet {
    private CategoryService categoryService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化分类服务
        categoryService = ServiceFactory.getCategoryService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // 默认显示所有分类
            showAllCategories(request, response);
        } else if (pathInfo.startsWith("/detail/")) {
            // 显示分类详情
            showCategoryDetail(request, response);
        } else if (pathInfo.equals("/json")) {
            // 返回JSON格式的所有分类（用于AJAX请求）
            showAllCategoriesAsJson(request, response);
        }
    }
    
    /**
     * 显示所有分类
     */
    private void showAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/categoryList.jsp").forward(request, response);
    }
    
    /**
     * 显示分类详情
     */
    private void showCategoryDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getPathInfo().substring("/detail/".length());
            int id = Integer.parseInt(idStr);
            Category category = categoryService.getCategoryById(id);
            
            if (category != null) {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/WEB-INF/views/categoryDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/category");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendRedirect(request.getContextPath() + "/category");
        }
    }
    
    /**
     * 返回JSON格式的所有分类
     */
    private void showAllCategoriesAsJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        List<Category> categories = categoryService.getAllCategories();
        
        // 简单实现JSON序列化（实际项目中可以使用Jackson或Gson等库）
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            jsonBuilder.append("{\n")
                     .append("  \"id\": ").append(category.getId()).append(",\n")
                     .append("  \"name\": \"").append(escapeJson(category.getName())).append("\",\n")
                     .append("  \"description\": \"").append(escapeJson(category.getDescription())).append("\"\n")
                     .append("}");
            
            if (i < categories.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        
        jsonBuilder.append("]");
        
        response.getWriter().print(jsonBuilder.toString());
    }
    
    /**
     * 转义JSON字符串中的特殊字符
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}