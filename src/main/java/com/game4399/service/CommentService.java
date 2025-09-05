package com.game4399.service;

import com.game4399.model.Comment;
import java.util.List;

/**
 * 评论服务接口，定义评论相关的服务方法
 */
public interface CommentService {
    /**
     * 添加新评论
     * @param comment 评论对象
     * @return 是否添加成功
     */
    boolean addComment(Comment comment);
    
    /**
     * 获取指定游戏的所有评论
     * @param gameId 游戏ID
     * @return 评论列表
     */
    List<Comment> getCommentsByGameId(int gameId);
    
    /**
     * 获取指定用户的所有评论
     * @param userId 用户ID
     * @return 评论列表
     */
    List<Comment> getCommentsByUserId(int userId);
    
    /**
     * 根据ID删除评论
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    boolean deleteComment(int commentId);
    
    /**
     * 更新评论
     * @param comment 评论对象
     * @return 是否更新成功
     */
    boolean updateComment(Comment comment);
    
    /**
     * 计算游戏的平均评分
     * @param gameId 游戏ID
     * @return 平均评分
     */
    double calculateAverageRating(int gameId);
    
    /**
     * 根据ID获取评论
     * @param commentId 评论ID
     * @return 评论对象
     */
    Comment getCommentById(int commentId);
}