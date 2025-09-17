<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>重置密码 - 4399小游戏</title>
    <style>
        body { font-family: Arial, "Microsoft YaHei", sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        header { background-color: #ff6600; color: white; padding: 15px 0; text-align: center; }
        .reset-box { background-color: white; width: 400px; margin: 50px auto; padding: 30px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .reset-box h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #666; }
        .form-group input { width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-reset { width: 100%; padding: 12px; background-color: #ff6600; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-reset:hover { background-color: #e55d00; }
        .error-message { background-color: #ffebee; color: #c62828; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #ffcdd2; }
        .success-message { background-color: #e8f5e9; color: #2e7d32; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #a5d6a7; }
        .field-error { color: #c62828; font-size: 12px; margin-top: 5px; display: block; }
        .input-error { border-color: #c62828 !important; }
    </style>
    <script>
        function validateForm() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d).{6,}$/;
            
            let isValid = true;
            
            if (!passwordRegex.test(newPassword)) {
                document.getElementById('newPassword').className = 'input-error';
                document.getElementById('passwordError').textContent = '密码至少6位，需包含字母和数字';
                isValid = false;
            } else {
                document.getElementById('newPassword').className = '';
                document.getElementById('passwordError').textContent = '';
            }
            
            if (newPassword !== confirmPassword) {
                document.getElementById('confirmPassword').className = 'input-error';
                document.getElementById('confirmError').textContent = '两次密码输入不一致';
                isValid = false;
            } else {
                document.getElementById('confirmPassword').className = '';
                document.getElementById('confirmError').textContent = '';
            }
            
            return isValid;
        }
    </script>
</head>
<body>
    <header>
        <h1>4399小游戏 - 重置密码</h1>
    </header>
    <div class="container">
        <div class="reset-box">
            <h2>重置密码</h2>
            
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
            
            <!-- 检查是否有有效的重置令牌 -->
            <% if (request.getAttribute("invalidToken") != null) { %>
                <div class="error-message">
                    重置链接已过期或无效，请重新申请重置密码。
                </div>
            <% } else { %>
                <form onsubmit="return validateForm()" action="resetPassword" method="post">
                    <input type="hidden" name="token" value="<%= request.getAttribute("token") != null ? request.getAttribute("token") : "" %>">
                    
                    <div class="form-group">
                        <label for="newPassword">新密码</label>
                        <input type="password" id="newPassword" name="newPassword" required 
                            class="<%= (request.getAttribute("passwordError") != null) ? "input-error" : "" %>">
                        <span id="passwordError" class="field-error"><%= (request.getAttribute("passwordError") != null) ? request.getAttribute("passwordError") : "" %></span>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">确认新密码</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required 
                            class="<%= (request.getAttribute("confirmError") != null) ? "input-error" : "" %>">
                        <span id="confirmError" class="field-error"><%= (request.getAttribute("confirmError") != null) ? request.getAttribute("confirmError") : "" %></span>
                    </div>
                    <button type="submit" class="btn-reset">重置密码</button>
                </form>
            <% } %>
        </div>
    </div>
</body>
</html>