package com.game4399.util;

import com.game4399.service.GameService;
import com.game4399.service.impl.GameServiceImpl;

/**
 * 服务工厂类，用于创建和获取服务实例
 */
public class ServiceFactory {
    private static GameService gameService;
    
    /**
     * 获取GameService实例
     * @return GameService实例
     */
    public static GameService getGameService() {
        if (gameService == null) {
            gameService = new GameServiceImpl();
        }
        return gameService;
    }
}