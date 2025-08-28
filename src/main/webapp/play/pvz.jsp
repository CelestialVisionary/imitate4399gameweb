<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>植物大战僵尸 - 4399小游戏</title>
    <style>
        body {
            font-family: Arial, "Microsoft YaHei", sans-serif;
            margin: 0;
            padding: 0;
            background-color: #000;
        }
        .game-container {
            width: 100%;
            height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .game-header {
            background-color: #333;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .game-header h1 {
            margin: 0;
            font-size: 24px;
        }
        .game-header .back-btn {
            color: white;
            text-decoration: none;
            background-color: #ff6600;
            padding: 8px 16px;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .game-header .back-btn:hover {
            background-color: #e55d00;
        }
        .game-player {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #111;
        }
        .game-placeholder {
            background-color: #222;
            width: 800px;
            height: 600px;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #999;
            font-size: 24px;
            border: 2px solid #444;
            border-radius: 8px;
        }
        .game-info {
            background-color: #333;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .game-stats {
            display: flex;
            gap: 30px;
        }
        .game-stat-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }
    </style>
</head>
<body>
    <div class="game-container">
        <div class="game-header">
            <h1>植物大战僵尸</h1>
            <a href="${pageContext.request.contextPath}/game/detail/1" class="back-btn">返回游戏详情</a>
        </div>
        
        <div class="game-player">
            <div class="game-placeholder">
                游戏加载中...<br>
                (本页面为示例，实际游戏需要加载Flash或其他游戏引擎)
            </div>
        </div>
        
        <div class="game-info">
            <div class="game-stats">
                <div class="game-stat-item">
                    <span>分类：</span>
                    <span>策略</span>
                </div>
                <div class="game-stat-item">
                    <span>播放次数：</span>
                    <span>1234567</span>
                </div>
                <div class="game-stat-item">
                    <span>评分：</span>
                    <span>4.8</span>
                </div>
            </div>
            <p>适度游戏益脑，沉迷游戏伤身，合理安排时间，享受健康生活</p>
        </div>
    </div>
</body>
</html>