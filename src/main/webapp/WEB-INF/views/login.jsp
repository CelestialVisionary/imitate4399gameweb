<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录 - 4399小游戏</title>
    <style>
        body { font-family: Arial, "Microsoft YaHei", sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        header { background-color: #ff6600; color: white; padding: 15px 0; text-align: center; }
        .login-box { background-color: white; width: 400px; margin: 50px auto; padding: 30px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .login-box h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #666; }
        .form-group input { width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-login { width: 100%; padding: 12px; background-color: #ff6600; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-login:hover { background-color: #e55d00; }
        .register-link { text-align: center; margin-top: 15px; }
        .register-link a { color: #ff6600; text-decoration: none; }
        .error-message { background-color: #ffebee; color: #c62828; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #ffcdd2; }
        .success-message { background-color: #e8f5e9; color: #2e7d32; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #a5d6a7; }
        <script>
            function validateLoginForm() {
                const username = document.getElementById('username').value;
                const password = document.getElementById('password').value;
                const usernameRegex = /^[a-zA-Z0-9_]{4,16}$/;
                const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d).{6,}$/;

                if (!usernameRegex.test(username)) {
                    alert('用户名必须为4-16位字母、数字或下划线');
                    return false;
                }
                if (!passwordRegex.test(password)) {
                    alert('密码至少6位，需包含字母和数字');
                    return false;
                }
                return true;
            }
        </script>
    </style>
</head>
<body>
    <header>
        <h1>4399小游戏 - 用户登录</h1>
    </header>
    <div class="container">
        <div class="login-box">
            <h2>账号登录</h2>
            
            <!-- 显示错误消息 -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <!-- 显示成功消息 -->
            <% if (request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <form onsubmit="return validateLoginForm()" action="login" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn-login">登录</button>
                <div class="register-link">
                    还没有账号？<a href="register">立即注册</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>