package com.game4399.service.impl;

import com.game4399.dao.CategoryDAO;
import com.game4399.model.Category;
import com.game4399.service.CategoryService;
import java.util.List;

/**
 * CategoryService接口的实现类，提供游戏分类相关的服务
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO;
    
    // 构造方法
    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAO();
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
    
    @Override
    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }
    
    @Override
    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryByName(name);
    }
    
    @Override
    public boolean addCategory(Category category) {
        return categoryDAO.addCategory(category);
    }
    
    @Override
    public boolean updateCategory(Category category) {
        return categoryDAO.updateCategory(category);
    }
    
    @Override
    public boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }
}