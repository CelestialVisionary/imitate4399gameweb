<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            cursor: pointer;
            border: none;
        }
        .btn-play:hover {
            background-color: #e55d00;
        }
        
        /* 游戏详情区域样式 */
        .game-content {
            display: flex;
            gap: 30px;
            margin: 30px 0;
        }
        
        .game-main-info {
            flex: 2;
        }
        
        .game-sidebar {
            flex: 1;
        }
        
        .game-screenshots {
            margin-bottom: 30px;
        }
        
        .screenshots-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
            margin-top: 10px;
        }
        
        .screenshot-item {
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }
        
        .screenshot-item:hover {
            transform: scale(1.02);
        }
        
        .screenshot-item img {
            width: 100%;
            height: auto;
            display: block;
        }
        
        .game-stats {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .stat-item {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid #ddd;
        }
        
        .stat-item:last-child {
            border-bottom: none;
        }
        
        .stat-label {
            color: #666;
        }
        
        .stat-value {
            font-weight: bold;
            color: #333;
        }
        
        .game-system-requirements {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 5px;
        }
        
        .req-title {
            font-weight: bold;
            margin-bottom: 10px;
            color: #333;
        }
        
        .req-item {
            padding: 5px 0;
        }
        
        /* 响应式设计 */
        @media (max-width: 1200px) {
            .container {
                width: 100%;
                padding: 0 20px;
            }
            
            .game-content {
                flex-direction: column;
            }
            
            .screenshots-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }
        
        @media (max-width: 768px) {
            .game-header {
                flex-direction: column;
                align-items: center;
            }
            
            .game-image {
                width: 100%;
                max-width: 300px;
            }
            
            .game-meta {
                flex-wrap: wrap;
                gap: 10px;
            }
            
            .screenshots-grid {
                grid-template-columns: 1fr;
            }
            
            .related-games-grid {
                grid-template-columns: repeat(2, 1fr);
            }
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
                        <form action="${pageContext.request.contextPath}/game/play/${game.id}" method="post">
                            <button type="submit" class="btn-play">开始游戏</button>
                        </form>
                    </div>
                </div>
                
                <div class="game-content">
                    <div class="game-main-info">
                        <h3>游戏介绍</h3>
                        <div class="game-description">
                            <p>${game.description}</p>
                        </div>
                        
                        <div class="game-screenshots">
                            <h3>游戏截图</h3>
                            <div class="screenshots-grid">
                                <!-- 这里可以添加更多游戏截图 -->
                                <div class="screenshot-item">
                                    <img src="${pageContext.request.contextPath}/${game.imageUrl}" alt="游戏截图1" />
                                </div>
                                <div class="screenshot-item">
                                    <img src="${pageContext.request.contextPath}/${game.imageUrl}" alt="游戏截图2" />
                                </div>
                                <div class="screenshot-item">
                                    <img src="${pageContext.request.contextPath}/${game.imageUrl}" alt="游戏截图3" />
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="game-sidebar">
                        <div class="game-stats">
                            <h3>游戏数据</h3>
                            <div class="stat-item">
                                <span class="stat-label">分类</span>
                                <span class="stat-value">${game.category}</span>
                            </div>
                            <div class="stat-item">
                                <span class="stat-label">总播放次数</span>
                                <span class="stat-value">${game.playCount}</span>
                            </div>
                            <div class="stat-item">
                                <span class="stat-label">评分</span>
                                <span class="stat-value">${game.rating}/5.0</span>
                            </div>
                            <div class="stat-item">
                                <span class="stat-label">平均时长</span>
                                <span class="stat-value">约20分钟</span>
                            </div>
                        </div>
                        
                        <div class="game-system-requirements">
                            <h3>系统要求</h3>
                            <div class="req-title">最低配置</div>
                            <div class="req-item">操作系统：Windows 7/8/10</div>
                            <div class="req-item">浏览器：Chrome 60+、Firefox 55+、Edge 80+</div>
                            <div class="req-item">内存：2GB RAM</div>
                            <div class="req-item">网络：宽带连接</div>
                            <div class="req-item">分辨率：1024x768及以上</div>
                        </div>
                    </div>
                </div>
                
                <!-- 评论功能区域 -->
                <div class="game-comments" style="margin-top: 30px;">
                    <h3>玩家评论</h3>
                    
                    <!-- 游戏评分显示 -->
                    <div class="game-rating-display" style="margin-bottom: 20px; padding: 15px; background-color: #f9f9f9; border-radius: 5px;">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <span style="font-weight: bold;">综合评分：</span>
                            <span style="font-size: 24px; font-weight: bold; color: #ff6600;">${game.rating}</span>
                            <span>/ 5.0</span>
                            <div class="star-rating" style="color: #ffd700; font-size: 18px;">
                                <c:forEach begin="1" end="${game.rating}" varStatus="status">
                                    ★
                                </c:forEach>
                                <c:forEach begin="${game.rating + 1}" end="5" varStatus="status">
                                    ☆
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 评论表单 -->
                    <c:if test="${not empty sessionScope.userId}">
                        <div class="comment-form" style="margin-bottom: 30px; padding: 20px; background-color: #f9f9f9; border-radius: 5px;">
                            <h4>发表评论</h4>
                            <form action="${pageContext.request.contextPath}/comment/add" method="post">
                                <input type="hidden" name="gameId" value="${game.id}">
                                
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label for="rating" style="display: block; margin-bottom: 8px; color: #666;">评分：</label>
                                    <div class="rating-input" style="display: flex; gap: 5px;">
                                        <input type="radio" id="rating5" name="rating" value="5" checked>
                                        <label for="rating5" style="cursor: pointer;">★</label>
                                        <input type="radio" id="rating4" name="rating" value="4">
                                        <label for="rating4" style="cursor: pointer;">★</label>
                                        <input type="radio" id="rating3" name="rating" value="3">
                                        <label for="rating3" style="cursor: pointer;">★</label>
                                        <input type="radio" id="rating2" name="rating" value="2">
                                        <label for="rating2" style="cursor: pointer;">★</label>
                                        <input type="radio" id="rating1" name="rating" value="1">
                                        <label for="rating1" style="cursor: pointer;">★</label>
                                    </div>
                                </div>
                                
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label for="content" style="display: block; margin-bottom: 8px; color: #666;">评论内容：</label>
                                    <textarea id="content" name="content" rows="4" cols="50" style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical;" required></textarea>
                                </div>
                                
                                <button type="submit" class="btn-submit-comment" style="padding: 8px 20px; background-color: #ff6600; color: white; border: none; border-radius: 4px; cursor: pointer;">提交评论</button>
                            </form>
                        </div>
                    </c:if>
                    
                    <!-- 未登录提示 -->
                    <c:if test="${empty sessionScope.userId}">
                        <div style="margin-bottom: 30px; padding: 15px; background-color: #e3f2fd; border-radius: 5px; text-align: center;">
                            请先<a href="${pageContext.request.contextPath}/login.jsp" style="color: #ff6600; text-decoration: none;">登录</a>后发表评论
                        </div>
                    </c:if>
                    
                    <!-- 评论列表 -->
                    <div class="comments-list">
                        <c:choose>
                            <c:when test="${not empty comments}">
                                <c:forEach var="comment" items="${comments}">
                                    <div class="comment-item" style="margin-bottom: 15px; padding: 15px; background-color: #f9f9f9; border-radius: 5px;">
                                        <div class="comment-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
                                            <div class="comment-author" style="font-weight: bold; color: #333;">${comment.username}</div>
                                            <div class="comment-time" style="font-size: 12px; color: #999;">
                                                <fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </div>
                                        </div>
                                        
                                        <div class="comment-rating" style="color: #ffd700; margin-bottom: 10px;">
                                            <c:forEach begin="1" end="${comment.rating}" varStatus="status">
                                                ★
                                            </c:forEach>
                                            <c:forEach begin="${comment.rating + 1}" end="5" varStatus="status">
                                                ☆
                                            </c:forEach>
                                        </div>
                                        
                                        <div class="comment-content" style="color: #333; line-height: 1.6;">${comment.content}</div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="padding: 20px; text-align: center; color: #999;">
                                    暂无评论，来发表第一条评论吧！
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                
                <div class="related-games">
                    <h3>相关游戏推荐</h3>
                    <div class="related-games-grid">
                        <c:choose>
                            <c:when test="${not empty relatedGames}">
                                <c:forEach var="relatedGame" items="${relatedGames}">
                                    <a href="${pageContext.request.contextPath}/game/detail/${relatedGame.id}" class="related-game-item">
                                        <img src="${pageContext.request.contextPath}/${relatedGame.imageUrl}" alt="${relatedGame.name}" />
                                        <div class="related-game-info">
                                            <div>${relatedGame.name}</div>
                                            <div style="font-size: 12px; color: #666; margin-top: 4px;">评分: ${relatedGame.rating}</div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="grid-column: 1 / -1; text-align: center; color: #999; padding: 20px;">
                                    暂无相关游戏推荐
                                </div>
                            </c:otherwise>
                        </c:choose>
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