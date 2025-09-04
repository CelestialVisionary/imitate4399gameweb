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
            position: relative;
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
        .search-suggestions {
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translateX(-50%);
            width: 500px;
            max-height: 300px;
            overflow-y: auto;
            background-color: white;
            border: 1px solid #ddd;
            border-top: none;
            border-radius: 0 0 4px 4px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            z-index: 1000;
            display: none;
        }
        .search-suggestion-item {
            padding: 10px;
            cursor: pointer;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .search-suggestion-item:hover {
            background-color: #f5f5f5;
        }
        .search-suggestion-history {
            border-bottom: 1px solid #eee;
            font-weight: bold;
            padding: 5px 10px;
            color: #666;
        }
        .clear-history {
            color: #ff6600;
            font-size: 12px;
            cursor: pointer;
        }
        .clear-history:hover {
            text-decoration: underline;
        }
        .search-filters {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin: 15px 0;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
        }
        .filter-item {
            padding: 5px 10px;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .filter-item:hover {
            border-color: #ff6600;
        }
        .filter-item.active {
            background-color: #ff6600;
            color: white;
            border-color: #ff6600;
        }
        .filter-item .remove-filter {
            font-size: 12px;
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
            <form action="${pageContext.request.contextPath}/game/search" method="get" id="search-form">
                <input type="text" name="keyword" id="search-input" placeholder="搜索游戏名称、分类或描述..." value="${keyword}" autocomplete="off" />
                <button type="submit">搜索</button>
            </form>
            <div id="search-suggestions" class="search-suggestions">
                <!-- 搜索建议将通过JavaScript动态生成 -->
            </div>
        </div>
        
        <c:if test="${not empty keyword}">
            <div class="search-filters">
                <span>当前搜索: <strong>${keyword}</strong></span>
                <div class="filter-item <c:if test="${empty sort}">active</c:if>">
                    <span>默认排序</span>
                </div>
                <div class="filter-item <c:if test="${sort eq 'playCount'}">active</c:if>" onclick="window.location.href='${pageContext.request.contextPath}/game/search?keyword=${keyword}&sort=playCount';">
                    <span>按热度排序</span>
                </div>
                <div class="filter-item <c:if test="${sort eq 'rating'}">active</c:if>" onclick="window.location.href='${pageContext.request.contextPath}/game/search?keyword=${keyword}&sort=rating';">
                    <span>按评分排序</span>
                </div>
            </div>
        </c:if>
        
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
                    <div style="margin-top: 20px;">
                        <h4>热门搜索</h4>
                        <div style="display: flex; flex-wrap: wrap; gap: 10px; margin-top: 10px;">
                            <a href="${pageContext.request.contextPath}/game/search?keyword=王者荣耀" style="padding: 5px 10px; background-color: #f5f5f5; border-radius: 15px; text-decoration: none; color: #666;">王者荣耀</a>
                            <a href="${pageContext.request.contextPath}/game/search?keyword=我的世界" style="padding: 5px 10px; background-color: #f5f5f5; border-radius: 15px; text-decoration: none; color: #666;">我的世界</a>
                            <a href="${pageContext.request.contextPath}/game/search?keyword=和平精英" style="padding: 5px 10px; background-color: #f5f5f5; border-radius: 15px; text-decoration: none; color: #666;">和平精英</a>
                            <a href="${pageContext.request.contextPath}/game/search?keyword=迷你世界" style="padding: 5px 10px; background-color: #f5f5f5; border-radius: 15px; text-decoration: none; color: #666;">迷你世界</a>
                        </div>
                    </div>
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
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 搜索建议功能
            const searchInput = document.getElementById('search-input');
            const searchSuggestions = document.getElementById('search-suggestions');
            const searchForm = document.getElementById('search-form');
            
            // 热门搜索关键词
            const popularSearches = ['王者荣耀', '我的世界', '和平精英', '迷你世界', '拼图游戏', '赛车游戏', '射击游戏'];
            
            // 获取搜索历史
            function getSearchHistory() {
                const history = localStorage.getItem('searchHistory');
                return history ? JSON.parse(history) : [];
            }
            
            // 保存搜索历史
            function saveSearchHistory(keyword) {
                let history = getSearchHistory();
                if (!history.includes(keyword)) {
                    history.unshift(keyword);
                    if (history.length > 10) {
                        history = history.slice(0, 10);
                    }
                    localStorage.setItem('searchHistory', JSON.stringify(history));
                }
            }
            
            // 清空搜索历史
            function clearSearchHistory() {
                localStorage.removeItem('searchHistory');
                renderSuggestions([]);
            }
            
            // 渲染搜索建议
            function renderSuggestions(keyword) {
                if (!keyword.trim()) {
                    searchSuggestions.style.display = 'none';
                    return;
                }
                
                const history = getSearchHistory();
                const filteredHistory = history.filter(item => item.toLowerCase().includes(keyword.toLowerCase()));
                const filteredPopular = popularSearches.filter(item => 
                    item.toLowerCase().includes(keyword.toLowerCase()) && 
                    !filteredHistory.includes(item)
                );
                
                searchSuggestions.innerHTML = '';
                
                if (filteredHistory.length > 0) {
                    const historyHeader = document.createElement('div');
                    historyHeader.className = 'search-suggestion-history';
                    historyHeader.innerHTML = '搜索历史 <span class="clear-history" onclick="clearSearchHistory()">清空</span>';
                    searchSuggestions.appendChild(historyHeader);
                    
                    filteredHistory.forEach(item => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.className = 'search-suggestion-item';
                        suggestionItem.textContent = item;
                        suggestionItem.onclick = () => selectSuggestion(item);
                        searchSuggestions.appendChild(suggestionItem);
                    });
                }
                
                if (filteredPopular.length > 0) {
                    if (filteredHistory.length > 0) {
                        const divider = document.createElement('div');
                        divider.style.height = '1px';
                        divider.style.backgroundColor = '#eee';
                        searchSuggestions.appendChild(divider);
                    }
                    
                    filteredPopular.forEach(item => {
                        const suggestionItem = document.createElement('div');
                        suggestionItem.className = 'search-suggestion-item';
                        suggestionItem.textContent = item;
                        suggestionItem.onclick = () => selectSuggestion(item);
                        searchSuggestions.appendChild(suggestionItem);
                    });
                }
                
                if (searchSuggestions.children.length > 0) {
                    searchSuggestions.style.display = 'block';
                } else {
                    searchSuggestions.style.display = 'none';
                }
            }
            
            // 选择搜索建议
            function selectSuggestion(keyword) {
                searchInput.value = keyword;
                searchSuggestions.style.display = 'none';
                searchForm.submit();
            }
            
            // 输入事件监听
            searchInput.addEventListener('input', function() {
                renderSuggestions(this.value);
            });
            
            // 点击其他地方关闭搜索建议
            document.addEventListener('click', function(event) {
                if (!searchBox.contains(event.target)) {
                    searchSuggestions.style.display = 'none';
                }
            });
            
            // 表单提交时保存搜索历史
            searchForm.addEventListener('submit', function() {
                const keyword = searchInput.value.trim();
                if (keyword) {
                    saveSearchHistory(keyword);
                }
            });
            
            // 键盘事件
            searchInput.addEventListener('keydown', function(e) {
                const items = searchSuggestions.querySelectorAll('.search-suggestion-item');
                if (!items.length) return;
                
                if (e.key === 'ArrowDown') {
                    e.preventDefault();
                    const activeItem = document.querySelector('.search-suggestion-item.active');
                    if (activeItem) {
                        activeItem.classList.remove('active');
                        const nextItem = activeItem.nextElementSibling;
                        if (nextItem && nextItem.classList.contains('search-suggestion-item')) {
                            nextItem.classList.add('active');
                        } else {
                            items[0].classList.add('active');
                        }
                    } else {
                        items[0].classList.add('active');
                    }
                } else if (e.key === 'ArrowUp') {
                    e.preventDefault();
                    const activeItem = document.querySelector('.search-suggestion-item.active');
                    if (activeItem) {
                        activeItem.classList.remove('active');
                        const prevItem = activeItem.previousElementSibling;
                        if (prevItem && prevItem.classList.contains('search-suggestion-item')) {
                            prevItem.classList.add('active');
                        } else {
                            items[items.length - 1].classList.add('active');
                        }
                    } else {
                        items[items.length - 1].classList.add('active');
                    }
                } else if (e.key === 'Enter') {
                    e.preventDefault();
                    const activeItem = document.querySelector('.search-suggestion-item.active');
                    if (activeItem) {
                        selectSuggestion(activeItem.textContent);
                    } else {
                        searchForm.submit();
                    }
                }
            });
            
            // 暴露clearSearchHistory函数到全局
            window.clearSearchHistory = clearSearchHistory;
        });
    </script>
</body>
</html>