package com.game4399.service;

import java.util.List;

/**
 * 收藏服务接口，定义用户收藏游戏的业务逻辑
 */
public interface FavoritesService {
    
    /**
     * 添加收藏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否添加成功
     */
    boolean addFavorite(int userId, int gameId);
    
    /**
     * 取消收藏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否取消成功
     */
    boolean removeFavorite(int userId, int gameId);
    
    /**
     * 检查用户是否已收藏指定游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否已收藏
     */
    boolean isFavorite(int userId, int gameId);
    
    /**
     * 获取用户收藏的所有游戏ID
     * @param userId 用户ID
     * @return 游戏ID列表
     */
    List<Integer> getUserFavoriteGameIds(int userId);
    
    /**
     * 获取用户收藏的游戏数量
     * @param userId 用户ID
     * @return 收藏数量
     */
    int getUserFavoriteCount(int userId);
    
    /**
     * 获取收藏指定游戏的用户数量
     * @param gameId 游戏ID
     * @return 收藏用户数量
     */
    int getGameFavoriteCount(int gameId);
}