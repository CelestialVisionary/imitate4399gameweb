<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${game.name} - 4399小游戏</title>
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
        nav ul li a:hover {
            color: #ff6600;
        }
        .game-detail {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .game-detail h2 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #ff6600;
            padding-bottom: 10px;
        }
        .game-header {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .game-image {
            width: 300px;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
        }
        .game-basic-info {
            flex: 1;
        }
        .game-basic-info h3 {
            margin-top: 0;
            font-size: 24px;
        }
        .game-meta {
            display: flex;
            gap: 30px;
            margin: 15px 0;
            color: #666;
        }
        .game-meta-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .btn-play {
            display: inline-block;
            padding: 12px 30px;
            background-color: #ff6600;
            color: white;
            text-decoration: none;
            font-size: 18px;
            font-weight: bold;
            border-radius: 5px;
            margin-top: 10px;
            transition: background-color 0.3s ease;
        }
        .btn-play:hover {
            background-color: #e55d00;
        }
        .game-description {
            margin: 20px 0;
            line-height: 1.6;
            color: #333;
        }
        .related-games {
            margin-top: 30px;
        }
        .related-games h3 {
            color: #333;
            margin-bottom: 15px;
        }
        .related-games-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
        }
        .related-game-item {
            border: 1px solid #ddd;
            border-radius: 5px;
            overflow: hidden;
            transition: all 0.3s ease;
            display: block;
            color: inherit;
            text-decoration: none;
        }
        .related-game-item:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            transform: translateY(-5px);
        }
        .related-game-item img {
            width: 100%;
            height: 100px;
            object-fit: cover;
        }
        .related-game-info {
            padding: 8px;
            font-size: 14px;
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
                <li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
                <li><a href="${pageContext.request.contextPath}/game/all">全部游戏</a></li>
                <li><a href="${pageContext.request.contextPath}/game">热门游戏</a></li>
                <li><a href="${pageContext.request.contextPath}/game/category?name=策略">策略游戏</a></li>
                <li><a href="${pageContext.request.contextPath}/game/category?name=角色扮演">角色扮演</a></li>
                <li><a href="${pageContext.request.contextPath}/game/category?name=射击">射击游戏</a></li>
            </ul>
        </div>
    </nav>
    
    <div class="container">
        <div class="game-detail">
            <c:if test="${not empty game}">
                <h2>${game.name}</h2>
                
                <div class="game-header">
                    <img src="${pageContext.request.contextPath}/${game.imageUrl}" alt="${game.name}" class="game-image" />
                    <div class="game-basic-info">
                        <h3>${game.name}</h3>
                        <div class="game-meta">
                            <div class="game-meta-item">
                                <span>分类：</span>
                                <span>${game.category}</span>
                            </div>
                            <div class="game-meta-item">
                                <span>播放次数：</span>
                                <span>${game.playCount}</span>
                            </div>
                            <div class="game-meta-item">
                                <span>评分：</span>
                                <span>${game.rating}</span>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/${game.playUrl}" class="btn-play">开始游戏</a>
                    </div>
                </div>
                
                <div class="game-description">
                    <p>${game.description}</p>
                </div>
                
                <div class="related-games">
                    <h3>相关游戏</h3>
                    <div class="related-games-grid">
                        <!-- 这里可以通过JSP动态加载相关游戏 -->
                        <c:forEach var="relatedGame" items="${pageContext.request.getAttribute('relatedGames')}">
                            <a href="${pageContext.request.contextPath}/game/detail/${relatedGame.id}" class="related-game-item">
                                <img src="${pageContext.request.contextPath}/${relatedGame.imageUrl}" alt="${relatedGame.name}" />
                                <div class="related-game-info">
                                    ${relatedGame.name}
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${empty game}">
                <p>游戏不存在</p>
            </c:if>
        </div>
    </div>
    
    <footer>
        <div class="container">
            <p>抵制不良游戏，拒绝盗版游戏，注意自我保护，谨防受骗上当</p>
            <p>适度游戏益脑，沉迷游戏伤身，合理安排时间，享受健康生活</p>
            <p>© 2025 4399小游戏 版权所有</p>
        </div>
    </footer>
</body>
</html>