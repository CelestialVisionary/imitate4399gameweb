package com.game4399.model;

/**
 * 游戏实体类，用于表示游戏信息
 */
public class Game {
    private int id;             // 游戏ID
    private String name;        // 游戏名称
// 由于 category 字段值未被使用，移除该字段
    private int categoryId;     // 分类ID
    private String categoryName; // 分类名称
    private String description; // 游戏描述
    private String imageUrl;    // 游戏图片URL
// 由于 playUrl 字段值未被使用，移除该字段
// 由于 playCount 字段值未被使用，移除该字段
    private double rating;      // 游戏评分
    private String developer;   // 开发商
    private String releaseDate; // 发布日期
    
    // 构造方法
    public Game() {
    }
    
    public Game(int id, String name, String category, String description, String imageUrl, String playUrl, int playCount, double rating) {
        this.id = id;
        this.name = name;
        // 根据类中字段定义，推测此处应赋值给 categoryName
        this.categoryName = category;
        this.description = description;
        this.imageUrl = imageUrl;
// playUrl 字段已移除，该行代码删除
// playCount 字段已被移除，删除该行代码
        this.rating = rating;
        // 其他属性使用默认值
    }
    
    // Getter和Setter方法
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // 兼容原有的title方法
    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }
    
    // 标准的name方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return this.categoryId;
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