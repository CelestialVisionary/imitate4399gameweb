package com.game4399.model;

import java.util.Date;

/**
 * 评论实体类，用于表示游戏评论信息
 */
public class Comment {
    private int id;             // 评论ID
    private int gameId;         // 游戏ID
    private int userId;         // 用户ID
    private String username;    // 用户名（非数据库字段，用于显示）
    private String content;     // 评论内容
    private int rating;         // 评分（1-5分）
    private Date commentTime;   // 评论时间
    
    // 构造方法
    public Comment() {
    }
    
    public Comment(int id, int gameId, int userId, String content, int rating, Date commentTime) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.commentTime = commentTime;
    }
    
    // getter和setter方法
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getGameId() {
        return gameId;
    }
    
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public Date getCommentTime() {
        return commentTime;
    }
    
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}