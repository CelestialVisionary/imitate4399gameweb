package com.game4399.util;

import com.game4399.service.GameService;
import com.game4399.service.CommentService;
import com.game4399.service.impl.GameServiceImpl;
import com.game4399.service.impl.CommentServiceImpl;

/**
 * 服务工厂类，用于创建和获取服务实例
 */
public class ServiceFactory {
    private static GameService gameService;
    private static CommentService commentService;
    
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
    
    /**
     * 获取CommentService实例
     * @return CommentService实例
     */
    public static CommentService getCommentService() {
        if (commentService == null) {
            commentService = new CommentServiceImpl();
        }
        return commentService;
    }
}