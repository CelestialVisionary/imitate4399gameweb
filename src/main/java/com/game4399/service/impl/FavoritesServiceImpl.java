package com.game4399.service.impl;

import com.game4399.dao.FavoritesDAO;
import com.game4399.service.FavoritesService;
import java.util.List;

/**
 * FavoritesService接口的实现类，提供用户收藏游戏的服务
 */
public class FavoritesServiceImpl implements FavoritesService {
    private FavoritesDAO favoritesDAO;
    
    // 构造方法
    public FavoritesServiceImpl() {
        this.favoritesDAO = new FavoritesDAO();
    }
    
    @Override
    public boolean addFavorite(int userId, int gameId) {
        return favoritesDAO.addFavorite(userId, gameId);
    }
    
    @Override
    public boolean removeFavorite(int userId, int gameId) {
        return favoritesDAO.removeFavorite(userId, gameId);
    }
    
    @Override
    public boolean isFavorite(int userId, int gameId) {
        return favoritesDAO.isFavorite(userId, gameId);
    }
    
    @Override
    public List<Integer> getUserFavoriteGameIds(int userId) {
        return favoritesDAO.getUserFavoriteGameIds(userId);
    }
    
    @Override
    public int getUserFavoriteCount(int userId) {
        return favoritesDAO.getUserFavoriteCount(userId);
    }
    
    @Override
    public int getGameFavoriteCount(int gameId) {
        return favoritesDAO.getGameFavoriteCount(gameId);
    }
}