<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page import="com.game4399.model.User" %> <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户中心 - 4399小游戏</title>
    <style>
        body { font-family: Arial, "Microsoft YaHei", sans-serif; margin: 0; padding: 0; background-color: #f0f0f0; }
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        header { background-color: #ff6600; color: white; padding: 15px 0; text-align: center; }
        .profile-box { background-color: white; width: 800px; margin: 50px auto; padding: 30px; border-radius: 5px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .profile-box h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 8px; color: #666; }
        .form-group input { width: 100%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-update { width: 100%; padding: 12px; background-color: #ff6600; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn-update:hover { background-color: #e55d00; }
        .error-message { background-color: #ffebee; color: #c62828; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #ffcdd2; }
        .success-message { background-color: #e8f5e9; color: #2e7d32; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #a5d6a7; }
        .field-error { color: #c62828; font-size: 12px; margin-top: 5px; display: block; }
        .input-error { border-color: #c62828 !important; }
        .section-title { font-size: 18px; font-weight: bold; margin: 30px 0 15px 0; color: #333; border-bottom: 1px solid #ddd; padding-bottom: 8px; }
        .profile-info { margin-bottom: 20px; }
        .profile-info-item { margin-bottom: 10px; }
        .profile-info-label { display: inline-block; width: 100px; color: #666; font-weight: bold; }
    </style>
    <script>
        function validateUpdateForm() {
            const email = document.getElementById('email').value;
            const emailRegex = /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$/;
            
            if (!emailRegex.test(email)) {
                alert('请输入有效的邮箱地址');
                return false;
            }
            return true;
        }
        
        function validatePasswordForm() {
            const oldPassword = document.getElementById('oldPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
            
            if (!oldPassword) {
                alert('请输入旧密码');
                return false;
            }
            if (!passwordRegex.test(newPassword)) {
                alert('新密码至少6位，需包含字母和数字');
                return false;
            }
            if (newPassword !== confirmPassword) {
                alert('两次新密码输入不一致');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <header>
        <h1>4399小游戏 - 用户中心</h1>
    </header>
    <div class="container">
        <div class="profile-box">
            <h2>个人中心</h2>
            
            <% User user = (User) request.getAttribute("user"); %>
            
            <!-- 显示全局错误消息 -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <!-- 显示全局成功消息 -->
            <% if (request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <!-- 用户基本信息 -->
            <div class="profile-info">
                <div class="profile-info-item">
                    <span class="profile-info-label">用户名：</span>
                    <span><%= user.getUsername() %></span>
                </div>
                <div class="profile-info-item">
                    <span class="profile-info-label">注册时间：</span>
                    <span><%= user.getRegisterTime() %></span>
                </div>
            </div>
            
            <!-- 更新个人信息表单 -->
            <div class="section-title">更新个人信息</div>
            <form onsubmit="return validateUpdateForm()" action="profile" method="post">
                <input type="hidden" name="action" value="updateProfile">
                
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required 
                        class="<%= (request.getAttribute("emailError") != null) ? "input-error" : "" %>">
                    <% if (request.getAttribute("emailError") != null) { %>
                        <span class="field-error"><%= request.getAttribute("emailError") %></span>
                    <% } %>
                </div>
                
                <button type="submit" class="btn-update">更新个人信息</button>
            </form>
            
            <!-- 修改密码表单 -->
            <div class="section-title">修改密码</div>
            <form onsubmit="return validatePasswordForm()" action="profile" method="post">
                <input type="hidden" name="action" value="changePassword">
                
                <div class="form-group">
                    <label for="oldPassword">旧密码</label>
                    <input type="password" id="oldPassword" name="oldPassword" required 
                        class="<%= (request.getAttribute("oldPasswordError") != null) ? "input-error" : "" %>">
                    <% if (request.getAttribute("oldPasswordError") != null) { %>
                        <span class="field-error"><%= request.getAttribute("oldPasswordError") %></span>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="newPassword">新密码</label>
                    <input type="password" id="newPassword" name="newPassword" required 
                        class="<%= (request.getAttribute("newPasswordError") != null) ? "input-error" : "" %>">
                    <% if (request.getAttribute("newPasswordError") != null) { %>
                        <span class="field-error"><%= request.getAttribute("newPasswordError") %></span>
                    <% } %>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">确认新密码</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required 
                        class="<%= (request.getAttribute("confirmPasswordError") != null) ? "input-error" : "" %>">
                    <% if (request.getAttribute("confirmPasswordError") != null) { %>
                        <span class="field-error"><%= request.getAttribute("confirmPasswordError") %></span>
                    <% } %>
                </div>
                
                <button type="submit" class="btn-update">修改密码</button>
            </form>
            
            <div style="text-align: center; margin-top: 20px;">
                <a href="<%= request.getContextPath() %>/index.jsp" style="color: #666; text-decoration: none;">返回首页</a>
            </div>
        </div>
    </div>
</body>
</html>