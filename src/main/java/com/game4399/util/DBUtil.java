package com.game4399.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBUtil {
    // 数据库连接配置
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static final String PROPERTIES_FILE = "db.properties";

    // 静态初始化块，从配置文件加载数据库连接信息并加载驱动
    static {
        Properties props = new Properties();
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (in == null) {
                throw new RuntimeException("数据库配置文件" + PROPERTIES_FILE + "未找到");
            }
            props.load(in);
            URL = props.getProperty("jdbc.url");
            USERNAME = props.getProperty("jdbc.username");
            PASSWORD = props.getProperty("jdbc.password", "");
            // 调试输出加载的配置信息（不含密码）
            System.out.println("Loaded DB config - URL: " + URL + ", User: " + USERNAME);
            String driver = props.getProperty("jdbc.driver");
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库配置文件加载失败", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库驱动类未找到", e);
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        try {
            // 密码为空时传递null而非空字符串，避免MySQL无密码连接被拒绝
return DriverManager.getConnection(URL, USERNAME, PASSWORD.isEmpty() ? null : PASSWORD);
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

    public static void closeConnection(Connection conn, java.sql.Statement stmt) {
        closeConnection(conn);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("关闭Statement失败: " + e.getMessage());
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