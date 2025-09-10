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
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
}