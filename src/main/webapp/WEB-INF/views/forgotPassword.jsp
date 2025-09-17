<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>找回密码 - 4399小游戏</title>
    <style>
        body { font-family: Arial, "Microsoft YaHei", sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        header { background-color: #ff6600; color: white; padding: 15px 0; text-align: center; }
        .forgot-box { background-color: white; width: 400px; margin: 50px auto; padding: 30px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .forgot-box h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #666; }
        .form-group input { width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-submit { width: 100%; padding: 12px; background-color: #ff6600; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-submit:hover { background-color: #e55d00; }
        .back-login { text-align: center; margin-top: 15px; }
        .back-login a { color: #ff6600; text-decoration: none; }
        .error-message { background-color: #ffebee; color: #c62828; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #ffcdd2; }
        .success-message { background-color: #e8f5e9; color: #2e7d32; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #a5d6a7; }
        .field-error { color: #c62828; font-size: 12px; margin-top: 5px; display: block; }
        .input-error { border-color: #c62828 !important; }
    </style>
    <script>
        function validateForm() {
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const usernameRegex = /^[a-zA-Z0-9_]{4,16}$/;
            
            let isValid = true;
            
            if (!usernameRegex.test(username)) {
                document.getElementById('username').className = 'input-error';
                document.getElementById('usernameError').textContent = '用户名必须为4-16位字母、数字或下划线';
                isValid = false;
            } else {
                document.getElementById('username').className = '';
                document.getElementById('usernameError').textContent = '';
            }
            
            if (!emailRegex.test(email)) {
                document.getElementById('email').className = 'input-error';
                document.getElementById('emailError').textContent = '请输入有效的邮箱地址';
                isValid = false;
            } else {
                document.getElementById('email').className = '';
                document.getElementById('emailError').textContent = '';
            }
            
            return isValid;
        }
    </script>
</head>
<body>
    <header>
        <h1>4399小游戏 - 找回密码</h1>
    </header>
    <div class="container">
        <div class="forgot-box">
            <h2>找回密码</h2>
            
            <!-- 显示全局错误消息 -->
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
            
            <form onsubmit="return validateForm()" action="forgotPassword" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required 
                        class="<%= (request.getAttribute("usernameError") != null) ? "input-error" : "" %>">
                    <span id="usernameError" class="field-error"><%= (request.getAttribute("usernameError") != null) ? request.getAttribute("usernameError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" required 
                        class="<%= (request.getAttribute("emailError") != null) ? "input-error" : "" %>">
                    <span id="emailError" class="field-error"><%= (request.getAttribute("emailError") != null) ? request.getAttribute("emailError") : "" %></span>
                </div>
                <button type="submit" class="btn-submit">发送重置链接</button>
                <div class="back-login">
                    <a href="login">返回登录</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>