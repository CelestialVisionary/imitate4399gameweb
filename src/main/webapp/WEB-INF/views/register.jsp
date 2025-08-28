<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册 - 4399小游戏</title>
    <style>
        body { font-family: Arial, "Microsoft YaHei", sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        header { background-color: #ff6600; color: white; padding: 15px 0; text-align: center; }
        .register-box { background-color: white; width: 400px; margin: 50px auto; padding: 30px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .register-box h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #666; }
        .form-group input { width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-register { width: 100%; padding: 12px; background-color: #ff6600; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-register:hover { background-color: #e55d00; }
        .login-link { text-align: center; margin-top: 15px; }
        .login-link a { color: #ff6600; text-decoration: none; }
    </style>
</head>
<body>
    <header>
        <h1>4399小游戏 - 用户注册</h1>
    </header>
    <div class="container">
        <div class="register-box">
            <h2>账号注册</h2>
            <form action="register" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">确认密码</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <button type="submit" class="btn-register">注册</button>
                <div class="login-link">
                    已有账号？<a href="login.jsp">立即登录</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>