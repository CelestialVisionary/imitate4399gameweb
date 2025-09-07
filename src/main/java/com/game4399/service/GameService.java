package com.game4399.service;

import com.game4399.model.Game;
import java.util.List;

/**
 * 游戏服务接口，定义游戏相关的服务方法
 */
public interface GameService {
    /**
     * 获取所有游戏
     * @return 游戏列表
     */
    List<Game> getAllGames();
    
    /**
     * 根据ID获取游戏
     * @param id 游戏ID
     * @return 游戏对象
     */
    Game getGameById(int id);
    
    /**
     * 根据分类获取游戏
     * @param category 游戏分类
     * @return 游戏列表
     */
    List<Game> getGamesByCategory(String category);
    
    /**
     * 搜索游戏
     * @param keyword 搜索关键词
     * @return 游戏列表
     */
    List<Game> searchGames(String keyword);
    
    /**
     * 搜索游戏（支持排序）
     * @param keyword 搜索关键词
     * @param sort 排序字段（playCount或rating）
     * @return 游戏列表
     */
    List<Game> searchGames(String keyword, String sort);
    
    /**
     * 获取热门游戏
     * @param limit 获取的数量
     * @return 热门游戏列表
     */
    List<Game> getHotGames(int limit);
    
    /**
     * 更新游戏播放次数
     * @param gameId 游戏ID
     */
    void updatePlayCount(int gameId);
    
    /**
 * 获取相关游戏
 * @param gameId 当前游戏ID
 * @param category 当前游戏分类
 * @param limit 获取的数量
 * @return 相关游戏列表
 */
List<Game> getRelatedGames(int gameId, String category, int limit);

/**
 * 为用户推荐游戏
 * @param userId 用户ID
 * @param limit 获取的数量
 * @return 推荐游戏列表
 */
List<Game> recommendGamesForUser(int userId, int limit);
}