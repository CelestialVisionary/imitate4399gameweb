<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a href="game/category?name=策略" class="category-item">策略</a>
                <a href="game/category?name=MOBA" class="category-item">MOBA</a>
                <a href="game/category?name=沙盒" class="category-item">沙盒</a>
                <a href="game/category?name=射击" class="category-item">射击</a>
                <a href="game/category?name=角色扮演" class="category-item">角色扮演</a>
                <a href="game/category?name=休闲" class="category-item">休闲</a>
                <a href="game/category?name=益智" class="category-item">益智</a>
                <a href="game/category?name=动作" class="category-item">动作</a>
                <a href="game/category?name=冒险" class="category-item">冒险</a>
                <a href="game/category?name=体育" class="category-item">体育</a>
            </div>
        </div>
        
        <div class="hot-games">
            <h2>热门游戏</h2>
            <div class="game-grid">
                <!-- 这里会通过JSP动态加载热门游戏 -->
                <a href="game/detail/2" class="game-item">
                    <img src="images/wzry.svg" alt="王者荣耀" />
                    <div class="game-info">
                        <h3>王者荣耀</h3>
                        <p>MOBA</p>
                        <div class="game-stats">
                            <span>987万次播放</span>
                            <span>评分：4.6</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/4" class="game-item">
                    <img src="images/mc.svg" alt="我的世界" />
                    <div class="game-info">
                        <h3>我的世界</h3>
                        <p>沙盒</p>
                        <div class="game-stats">
                            <span>876万次播放</span>
                            <span>评分：4.9</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/5" class="game-item">
                    <img src="images/hpjy.svg" alt="和平精英" />
                    <div class="game-info">
                        <h3>和平精英</h3>
                        <p>射击</p>
                        <div class="game-stats">
                            <span>654万次播放</span>
                            <span>评分：4.4</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/3" class="game-item">
                    <img src="images/mnsj.svg" alt="迷你世界" />
                    <div class="game-info">
                        <h3>迷你世界</h3>
                        <p>沙盒</p>
                        <div class="game-stats">
                            <span>567万次播放</span>
                            <span>评分：4.5</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/6" class="game-item">
                    <img src="images/puzzle.svg" alt="拼图游戏" />
                    <div class="game-info">
                        <h3>拼图游戏</h3>
                        <p>益智</p>
                        <div class="game-stats">
                            <span>123万次播放</span>
                            <span>评分：4.7</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/7" class="game-item">
                    <img src="images/racing.svg" alt="赛车游戏" />
                    <div class="game-info">
                        <h3>赛车游戏</h3>
                        <p>体育</p>
                        <div class="game-stats">
                            <span>456万次播放</span>
                            <span>评分：4.5</span>
                        </div>
                    </div>
                </a>
                <a href="game/detail/8" class="game-item">
                    <img src="images/shooter.svg" alt="射击游戏" />
                    <div class="game-info">
                        <h3>射击游戏</h3>
                        <p>射击</p>
                        <div class="game-stats">
                            <span>789万次播放</span>
                            <span>评分：4.8</span>
                        </div>
                    </div>
                </a>
            </div>
            <a href="game" class="btn-view-all">查看更多热门游戏</a>
        </div>
    </div>
    
    <!-- 游戏排行榜 -->
    <div class="ranking-section">
        <h2>游戏排行榜</h2>
        <div class="ranking-list">
            <div class="ranking-item">
                <div class="ranking-number top1">1</div>
                <div class="ranking-game-info">
                    <img src="images/wzry.svg" alt="王者荣耀">
                    <div class="ranking-game-details">
                        <h3>王者荣耀</h3>
                        <p>MOBA • 987万次播放</p>
                    </div>
                </div>
                <div class="ranking-stats">↑ 2</div>
            </div>
            <div class="ranking-item">
                <div class="ranking-number top2">2</div>
                <div class="ranking-game-info">
                    <img src="images/shooter.svg" alt="射击游戏">
                    <div class="ranking-game-details">
                        <h3>射击游戏</h3>
                        <p>射击 • 789万次播放</p>
                    </div>
                </div>
                <div class="ranking-stats">↓ 1</div>
            </div>
            <div class="ranking-item">
                <div class="ranking-number top3">3</div>
                <div class="ranking-game-info">
                    <img src="images/mc.svg" alt="我的世界">
                    <div class="ranking-game-details">
                        <h3>我的世界</h3>
                        <p>沙盒 • 876万次播放</p>
                    </div>
                </div>
                <div class="ranking-stats">↑ 3</div>
            </div>
            <div class="ranking-item">
                <div class="ranking-number other">4</div>
                <div class="ranking-game-info">
                    <img src="images/racing.svg" alt="赛车游戏">
                    <div class="ranking-game-details">
                        <h3>赛车游戏</h3>
                        <p>体育 • 456万次播放</p>
                    </div>
                </div>
                <div class="ranking-stats">→</div>
            </div>
            <div class="ranking-item">
                <div class="ranking-number other">5</div>
                <div class="ranking-game-info">
                    <img src="images/hpjy.svg" alt="和平精英">
                    <div class="ranking-game-details">
                        <h3>和平精英</h3>
                        <p>射击 • 654万次播放</p>
                    </div>
                </div>
                <div class="ranking-stats">↓ 2</div>
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
        // 轮播图交互
        document.addEventListener('DOMContentLoaded', function() {
            const slides = document.querySelectorAll('.carousel-slide');
            const indicators = document.querySelectorAll('.indicator');
            let currentIndex = 0;

            function showSlide(index) {
                slides.forEach((slide, i) => {
                    slide.classList.toggle('active', i === index);
                });
                indicators.forEach((indicator, i) => {
                    indicator.classList.toggle('active', i === index);
                });
            }

            indicators.forEach((indicator, index) => {
                indicator.addEventListener('click', () => {
                    currentIndex = index;
                    showSlide(currentIndex);
                });
            });

            // 自动轮播
            setInterval(() => {
                currentIndex = (currentIndex + 1) % slides.length;
                showSlide(currentIndex);
            }, 5000);
        });
    </script>
</body>
</html>