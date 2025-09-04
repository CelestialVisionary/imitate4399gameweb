package com.game4399.service.impl;

import com.game4399.dao.GameDAO;
import com.game4399.model.Game;
import com.game4399.service.GameService;
import java.util.List;

/**
 * GameService接口的实现类，提供游戏相关的服务
 */
public class GameServiceImpl implements GameService {
    private GameDAO gameDAO;
    
    // 构造方法
    public GameServiceImpl() {
        this.gameDAO = new GameDAO();
    }
    
    @Override
    public List<Game> getAllGames() {
        return gameDAO.getAllGames();
    }
    
    @Override
    public Game getGameById(int id) {
        return gameDAO.getGameById(id);
    }
    
    @Override
    public List<Game> getGamesByCategory(String category) {
        return gameDAO.getGamesByCategory(category);
    }
    
    @Override
    public List<Game> searchGames(String keyword) {
        return gameDAO.searchGames(keyword);
    }
    
    @Override
    public List<Game> searchGames(String keyword, String sort) {
        return gameDAO.searchGames(keyword, sort);
    }
    
    @Override
    public List<Game> getHotGames(int limit) {
        return gameDAO.getHotGames(limit);
    }
    
    /**
     * 更新游戏播放次数
     * @param gameId 游戏ID
     */
    public void updatePlayCount(int gameId) {
        gameDAO.updatePlayCount(gameId);
    }
    
    /**
     * 获取相关游戏
     * @param gameId 当前游戏ID
     * @param category 当前游戏分类
     * @param limit 获取的数量
     * @return 相关游戏列表
     */
    public List<Game> getRelatedGames(int gameId, String category, int limit) {
        return gameDAO.getRelatedGames(gameId, category, limit);
    }
}