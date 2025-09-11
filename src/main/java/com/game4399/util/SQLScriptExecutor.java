package com.game4399.util;

import java.io.BufferedReader;
// 移除未使用的导入语句 java.io.FileReader
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

/**
 * 数据库SQL脚本执行工具类，用于通过Java代码执行SQL脚本文件
 */
public class SQLScriptExecutor {

    /**
     * 执行指定路径的SQL脚本文件
     * @param filePath SQL脚本文件的绝对路径
     * @throws Exception 执行过程中发生的异常
     */
    public static void executeScript(String filePath) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader br = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            // 使用UTF-8编码读取文件，避免中文注释或特殊字符导致的语法错误
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

            StringBuilder sqlBuffer = new StringBuilder();
            String line;

            // 逐行读取SQL文件
            while ((line = br.readLine()) != null) {
                // 去除行首尾空格
                String trimmedLine = line.trim();

                // 跳过注释行和空行
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("--")) {
                    continue;
                }

                // 将行添加到SQL缓冲区
                sqlBuffer.append(line);

                // 如果行以分号结尾，执行SQL语句
                if (trimmedLine.endsWith(";")) {
                    String sql = sqlBuffer.toString();
                    System.out.println("Executing SQL: " + sql);
                    stmt.execute(sql);
                    sqlBuffer.setLength(0); // 重置缓冲区
                }
            }

            // 执行最后一个可能没有分号结尾的SQL语句
            String finalSql = sqlBuffer.toString().trim();
            if (!finalSql.isEmpty()) {
                stmt.execute(finalSql);
            }

        } finally {
            // 关闭资源
            if (br != null) br.close();
            if (stmt != null) stmt.close();
            if (conn != null) DBUtil.closeConnection(conn);
        }
    }

    /**
     * 主方法，用于测试执行SQL脚本
     * @param args 命令行参数，第一个参数为SQL脚本文件路径
     */
    public static void main(String[] args) {
        // 默认SQL脚本路径，如果未提供命令行参数则使用此路径
        String defaultPath = "d:/imitate4399gameweb/sql/create_user_favorites_table.sql";
        String filePath = (args.length > 0) ? args[0] : defaultPath;

        try {
            // 先执行创建数据库脚本
            executeScript("d:/imitate4399gameweb/sql/create_database.sql");
            // 再执行创建收藏表脚本
            executeScript(filePath);
            System.out.println("SQL脚本执行成功！");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("SQL脚本执行失败: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}