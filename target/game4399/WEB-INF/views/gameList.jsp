<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - 4399小游戏</title>
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
        .content-section {
            background-color: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .content-section h2 {
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
            display: block;
            color: inherit;
            text-decoration: none;
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
        .no-results {
            text-align: center;
            padding: 40px;
            color: #666;
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
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/game/search" method="get">
                <input type="text" name="keyword" placeholder="搜索游戏名称、分类或描述..." value="${keyword}" />
                <button type="submit">搜索</button>
            </form>
        </div>
        
        <div class="content-section">
            <h2>${title}</h2>
            
            <c:if test="${not empty games}">
                <div class="game-grid">
                    <c:forEach var="game" items="${games}">
                        <a href="${pageContext.request.contextPath}/game/detail/${game.id}" class="game-item">
                            <img src="${pageContext.request.contextPath}/${game.imageUrl}" alt="${game.name}" />
                            <div class="game-info">
                                <h3>${game.name}</h3>
                                <p>${game.category}</p>
                                <div class="game-stats">
                                    <span>${game.playCount}次播放</span>
                                    <span>评分：${game.rating}</span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
            
            <c:if test="${empty games}">
                <div class="no-results">
                    <p>没有找到相关游戏</p>
                </div>
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