package com.game4399.service.impl;

import com.game4399.dao.CommentDAO;
import com.game4399.model.Comment;
import com.game4399.service.CommentService;
import java.util.List;

/**
 * CommentService接口的实现类，提供评论相关的服务
 */
public class CommentServiceImpl implements CommentService {
    private CommentDAO commentDAO;
    
    // 构造方法
    public CommentServiceImpl() {
        this.commentDAO = new CommentDAO();
    }
    
    @Override
    public boolean addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }
    
    @Override
    public List<Comment> getCommentsByGameId(int gameId) {
        return commentDAO.getCommentsByGameId(gameId);
    }
    
    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return commentDAO.getCommentsByUserId(userId);
    }
    
    @Override
    public boolean deleteComment(int commentId) {
        return commentDAO.deleteComment(commentId);
    }
    
    @Override
    public boolean updateComment(Comment comment) {
        return commentDAO.updateComment(comment);
    }
    
    @Override
    public double calculateAverageRating(int gameId) {
        return commentDAO.calculateAverageRating(gameId);
    }
}