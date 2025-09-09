package com.game4399.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // 数据库连接配置
    private static final String URL = "jdbc:mysql://localhost:3306/game4399db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    // 静态初始化块，加载数据库驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 关闭数据库连接
     * @param conn 数据库连接对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("关闭数据库连接失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 关闭所有数据库资源
     * @param conn 数据库连接对象
     * @param stmt Statement对象
     * @param rs ResultSet对象
     */
    public static void closeConnection(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        // 关闭ResultSet
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("关闭ResultSet失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // 关闭Statement
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("关闭Statement失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // 关闭Connection
        closeConnection(conn);
    }
}