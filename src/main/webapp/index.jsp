<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.game4399.model.Category" %>
<%@ page import="com.game4399.service.CategoryService" %>
<%@ page import="com.game4399.util.ServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.game4399.model.Game" %>
<%@ page import="com.game4399.service.GameService" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>4399小游戏 - 免费在线小游戏网站</title>
    <style>
        body {
            font-family: Arial, "Microsoft YaHei", sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }
        .container {
            width: 1200px;
            margin: 0 auto;
        }
        header {
            background-color: #ff6600;
            color: white;
            padding: 15px 0;
        }
        header h1 {
            margin: 0;
            font-size: 36px;
            text-align: center;
        }
        nav {
            background-color: #333;
            padding: 10px 0;
        }
        nav ul {
            list-style: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }
        nav ul li {
            margin: 0 15px;
        }
        nav ul li a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        nav ul li.auth-item { margin-left: auto; }
        nav ul li a:hover {
            color: #ff6600;
        }
        .search-box {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            text-align: center;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .search-box input {
            width: 500px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px;
        }
        .search-box button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #ff6600;
            color: white;
            border: none;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
        }
        .search-box button:hover {
            background-color: #e55d00;
        }
        .category-section {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .category-section h2 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #ff6600;
            padding-bottom: 10px;
        }
        .category-list {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }
        .category-item {
            padding: 8px 15px;
            background-color: #f5f5f5;
            border-radius: 20px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .category-item:hover {
            background-color: #ff6600;
            color: white;
        }
        .hot-games {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .hot-games h2 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #ff6600;
            padding-bottom: 10px;
        }
        .game-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-top: 20px;
        }
        .game-item {
            border: 1px solid #ddd;
            border-radius: 5px;
            overflow: hidden;
            transition: all 0.3s ease;
        }
        .game-item:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            transform: translateY(-5px);
        }
        .game-item img {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }
        .game-info {
            padding: 10px;
        }
        .game-info h3 {
            margin: 0;
            font-size: 16px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .game-info p {
            margin: 5px 0;
            font-size: 14px;
            color: #666;
        }
        .game-stats {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
            font-size: 12px;
            color: #888;
        }
        .btn-view-all {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            background-color: #ff6600;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .btn-view-all:hover {
            background-color: #e55d00;
        }
        /* 排行榜样式 */
        .ranking-section {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .ranking-section h2 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #ff6600;
            padding-bottom: 10px;
        }
        .ranking-list {
            margin-top: 20px;
        }
        .ranking-item {
            display: flex;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #eee;
            transition: background-color 0.3s ease;
        }
        .ranking-item:hover {
            background-color: #f9f9f9;
        }
        .ranking-number {
            width: 30px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            border-radius: 50%;
            font-weight: bold;
            margin-right: 15px;
        }
        .ranking-number.top1 {
            background-color: #ffd700;
            color: #333;
        }
        .ranking-number.top2 {
            background-color: #c0c0c0;
            color: #333;
        }
        .ranking-number.top3 {
            background-color: #cd7f32;
            color: white;
        }
        .ranking-number.other {
            background-color: #eee;
            color: #666;
        }
        .ranking-game-info {
            display: flex;
            align-items: center;
            flex: 1;
        }
        .ranking-game-info img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            margin-right: 15px;
            border-radius: 5px;
        }
        .ranking-game-details {
            flex: 1;
        }
        .ranking-game-details h3 {
            margin: 0 0 5px 0;
            font-size: 16px;
        }
        .ranking-game-details p {
            margin: 0;
            color: #666;
            font-size: 14px;
        }
        .ranking-stats {
            color: #ff6600;
            font-weight: bold;
        }
        /* 轮播图样式 */
        .carousel-container {
            width: 100%;
            overflow: hidden;
            position: relative;
            margin: 20px 0;
        }
        .carousel-slider {
            display: flex;
            transition: transform 0.5s ease;
        }
        .carousel-slide {
            min-width: 100%;
            height: 300px;
        }
        .carousel-slide img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .carousel-indicators {
            position: absolute;
            bottom: 15px;
            left: 50%;
            transform: translateX(-50%);
            display: flex;
            gap: 10px;
        }
        .indicator {
            width: 12px;
            height: 12px;
            border-radius: 50%;
            background-color: #ccc;
            cursor: pointer;
        }
        .indicator.active {
            background-color: #ff6600;
        }
        footer {
            background-color: #333;
            color: white;
            padding: 20px 0;
            text-align: center;
            margin-top: 40px;
        }
        footer p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <h1>4399小游戏</h1>
        </div>
    </header>
    
    <nav>
        <div class="container">
            <ul>
                <li><a href="index.jsp">首页</a></li>
                <li><a href="game/all">全部游戏</a></li>
                <li><a href="game/new">最新上线</a></li>
                <li><a href="game/hot">热门游戏</a></li>
                <li><a href="game/category">分类大全</a></li>
                <li><a href="game/minigame">小游戏</a></li>
                <li><a href="game/webgame">网页游戏</a></li>
                <li><a href="game/category?name=策略">策略</a></li>
                <li><a href="game/category?name=角色扮演">角色扮演</a></li>
                <li><a href="game/category?name=射击">射击</a></li>
                    <li class="auth-item"><a href="login">登录</a></li>
        <li class="auth-item"><a href="register">注册</a></li>
    </ul>
        </div>
    </nav>
    
    <!-- 轮播图组件 -->
    <div class="carousel-container">
        <div class="carousel-slider">
            <div class="carousel-slide active">
                <img src="images/carousel1.svg" alt="热门游戏推荐" />
            </div>
            <div class="carousel-slide">
                <img src="images/carousel2.svg" alt="新品游戏上线" />
            </div>
            <div class="carousel-slide">
                <img src="images/carousel3.svg" alt="游戏活动宣传" />
            </div>
        </div>
        <div class="carousel-indicators">
            <span class="indicator active"></span>
            <span class="indicator"></span>
            <span class="indicator"></span>
        </div>
    </div>
    
    <div class="container">
        <div class="search-box">
            <form action="game/search" method="get">
                <input type="text" name="keyword" placeholder="搜索游戏名称、分类或描述..." />
                <button type="submit">搜索</button>
            </form>
        </div>
        
        <div class="category-section">
            <h2>游戏分类</h2>
            <div class="category-list">
                <% 
                    // 获取所有分类
                    CategoryService categoryService = ServiceFactory.getCategoryService();
                    List<Category> categories = categoryService.getAllCategories();
                    
                    // 如果没有分类数据，使用默认分类
                    if (categories == null || categories.isEmpty()) {
                %>
                <a href="game/category?name=休闲益智" class="category-item">休闲益智</a>
                <a href="game/category?name=动作冒险" class="category-item">动作冒险</a>
                <a href="game/category?name=射击游戏" class="category-item">射击游戏</a>
                <a href="game/category?name=策略游戏" class="category-item">策略游戏</a>
                <a href="game/category?name=角色扮演" class="category-item">角色扮演</a>
                <% 
                    } else {
                        for (Category category : categories) {
                %>
                <a href="game/category?name=<%= category.getName() %>" class="category-item"><%= category.getName() %></a>
                <% 
                        }
                    }
                %>
            </div>
        </div>
        
        <div class="hot-games">
            <h2>热门游戏</h2>
            <div class="game-grid">
                <!-- 通过JSP动态加载热门游戏 -->
                <% 
                    GameService gameService = ServiceFactory.getGameService();
                    List<Game> hotGames = gameService.getHotGames(4); // 获取前4个热门游戏
                    if (hotGames != null && !hotGames.isEmpty()) {
                        for (Game game : hotGames) {
                %>
                <a href="${pageContext.request.contextPath}/game/detail/<%= game.getId() %>" class="game-item">
                    <img src="${pageContext.request.contextPath}/<%= game.getImageUrl() %>" alt="<%= game.getName() %>" />
                    <div class="game-info">
                        <h3><%= game.getName() %></h3>
                        <p><%= game.getCategoryName() %></p>
                        <div class="game-stats">
                            <span><%= game.getPlayCount() %>次播放</span>
                            <span>评分：<%= game.getRating() %></span>
                        </div>
                    </div>
                </a>
                <% 
                        }
                    }
                %>
            </div>
            <a href="game" class="btn-view-all">查看更多热门游戏</a>
        </div>
    </div>
    
    <!-- 游戏排行榜 -->
    <div class="ranking-section">
        <div class="container">
            <h2>游戏排行榜</h2>
            <div class="ranking-list">
                <!-- 通过JSP动态加载游戏排行榜 -->
                <% 
                    List<Game> rankingGames = gameService.getHotGames(5); // 获取前5个热门游戏作为排行榜
                    if (rankingGames != null && !rankingGames.isEmpty()) {
                        for (int i = 0; i < rankingGames.size(); i++) {
                            Game game = rankingGames.get(i);
                            String rankingClass = "other";
                            if (i == 0) rankingClass = "top1";
                            else if (i == 1) rankingClass = "top2";
                            else if (i == 2) rankingClass = "top3";
                %>
                <div class="ranking-item">
                    <div class="ranking-number <%= rankingClass %>"><%= (i + 1) %></div>
                    <div class="ranking-game-info">
                        <img src="${pageContext.request.contextPath}/<%= game.getImageUrl() %>" alt="<%= game.getName() %>">
                        <div class="ranking-game-details">
                            <h3><%= game.getName() %></h3>
                            <p><%= game.getCategoryName() %> • <%= game.getPlayCount() %>次播放</p>
                        </div>
                    </div>
                    <div class="ranking-stats">
                        <% 
                            // 这里可以添加真实的排名变化逻辑，当前只是示例
                            if (i == 0) out.print("↑ 2");
                            else if (i == 1) out.print("↓ 1");
                            else if (i == 3) out.print("→");
                            else if (i == 4) out.print("↓ 2");
                        %>
                    </div>
                </div>
                <% 
                        }
                    }
                %>
            </div>
        </div>
    </div>
    </div>
    
    <footer>
        <div class="container">
            <p>抵制不良游戏，拒绝盗版游戏，注意自我保护，谨防受骗上当</p>
            <p>适度游戏益脑，沉迷游戏伤身，合理安排时间，享受健康生活</p>
            <p>闽网文〔2024〕4287-092号 | （署）网出证（闽）字第015号 | ICP证闽B2-20040099</p>
            <p>闽公网安备 35020302000081号 | 公司地址：厦门市厦门火炬高新区软件园二期望海路2号楼202室</p>
            <p>举报电话：4006834399(转6) | 举报邮箱：jubao@4399.com</p>
            <p>© 2004 - 2025 4399.com All Rights Reserved. 四三九九网络股份有限公司 版权所有</p>
        </div>
    </footer>
    <script>
        // 轮播图功能实现
        document.addEventListener('DOMContentLoaded', function() {
            const slider = document.querySelector('.carousel-slider');
            const slides = document.querySelectorAll('.carousel-slide');
            const indicators = document.querySelectorAll('.indicator');
            let currentIndex = 0;
            const slideCount = slides.length;
            let slideInterval;
    
            // 设置轮播图宽度
            function initSlider() {
                slides.forEach(slide => {
                    slide.style.minWidth = `${100 / slideCount}%`;
                });
                startSlideInterval();
            }
    
            // 切换到指定幻灯片
            function goToSlide(index) {
                if (index < 0 || index >= slideCount) return;
                currentIndex = index;
                slider.style.transform = `translateX(-${currentIndex * (100 / slideCount)}%)`;
                updateIndicators();
            }
    
            // 更新指示器状态
            function updateIndicators() {
                indicators.forEach((indicator, index) => {
                    indicator.classList.toggle('active', index === currentIndex);
                });
            }
    
            // 下一张幻灯片
            function nextSlide() {
                currentIndex = (currentIndex + 1) % slideCount;
                goToSlide(currentIndex);
            }
    
            // 开始自动轮播
            function startSlideInterval() {
                slideInterval = setInterval(nextSlide, 5000);
            }
    
            // 停止自动轮播
            function stopSlideInterval() {
                clearInterval(slideInterval);
            }
    
            // 绑定指示器点击事件
            indicators.forEach((indicator, index) => {
                indicator.addEventListener('click', () => {
                    goToSlide(index);
                    stopSlideInterval();
                    startSlideInterval();
                });
            });
    
            // 鼠标悬停时停止轮播
            slider.addEventListener('mouseenter', stopSlideInterval);
            slider.addEventListener('mouseleave', startSlideInterval);
    
            // 初始化轮播
            initSlider();
        });
    </script>
</body>
</html>