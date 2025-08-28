package com.game4399.model;

/**
 * 游戏实体类，用于表示游戏信息
 */
public class Game {
    private int id;             // 游戏ID
    private String name;        // 游戏名称
    private String category;    // 游戏分类
    private String description; // 游戏描述
    private String imageUrl;    // 游戏图片URL
    private String playUrl;     // 游戏播放URL
    private int playCount;      // 游戏播放次数
    private double rating;      // 游戏评分
    
    // 构造方法
    public Game() {
    }
    
    public Game(int id, String name, String category, String description, String imageUrl, String playUrl, int playCount, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.playUrl = playUrl;
        this.playCount = playCount;
        this.rating = rating;
    }
    
    // Getter和Setter方法
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getPlayUrl() {
        return playUrl;
    }
    
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
    
    public int getPlayCount() {
        return playCount;
    }
    
    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
}