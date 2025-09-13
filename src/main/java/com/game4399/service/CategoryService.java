package com.game4399.service;

import com.game4399.model.Category;
import java.util.List;

/**
 * 分类服务接口，定义分类相关的业务逻辑
 */
public interface CategoryService {
    /**
     * 获取所有游戏分类
     * @return 分类列表
     */
    List<Category> getAllCategories();
    
    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类对象
     */
    Category getCategoryById(int id);
    
    /**
     * 根据名称获取分类
     * @param name 分类名称
     * @return 分类对象
     */
    Category getCategoryByName(String name);
    
    /**
     * 添加新分类
     * @param category 分类对象
     * @return 是否添加成功
     */
    boolean addCategory(Category category);
    
    /**
     * 更新分类信息
     * @param category 分类对象
     * @return 是否更新成功
     */
    boolean updateCategory(Category category);
    
    /**
     * 删除分类
     * @param id 分类ID
     * @return 是否删除成功
     */
    boolean deleteCategory(int id);
}