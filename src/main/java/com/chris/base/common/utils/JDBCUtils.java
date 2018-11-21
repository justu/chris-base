package com.chris.base.common.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

/*
 * 创建JDBC工具类
 * 定义一个properties文件,存储数据库的4大信息
 * 在成员位置定义5个存储数据库信息的变量
 * 定义一个静态代码块
 * 	a.使用Properties集合+IO读取配置文件中的信息,把信息保存到集合中
 * 	b.获取集合中的数据库连接信息,给成员变量赋值
 * 	c.注册驱动和获取数据库连接对象
 * 定义一个静态方法,用于获取并返回数据库连接对象Connection
 * 定义一个静态方法,用于释放资源
 */
public class JDBCUtils {
    /**
     * 在成员位置定义5个存储数据库信息的变量
     */
    private static String driver;

    private static String url;

    private static String user;

    private static String password;

    /**
     * 定义一个Connection类型的变量用来存储获取到的Connection实例化对象
     */
    private static Connection conn;

    /**
     * 私有构造方法，防止用户创建对象，浪费内存空间
     */
    private JDBCUtils() {

    }

    static {
        try {
            /**
             * 使用ResourceBundle集合读取配置信息
             */
            ResourceBundle bundle = ResourceBundle.getBundle("sqlserver");
            /**
             * 获取集合中的数据库连接信息,给成员变量赋值
             */
            driver = bundle.getString("driver");
            url = bundle.getString("url");
            user = bundle.getString("user");
            password = bundle.getString("password");

        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }

        /**
         * 创建驱动和数据库连接对象
         */
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            /**
             * 如果数据库连接失败，则不应该继续往下，抛出运行时异常给虚拟机，终止程序
             */
            throw new RuntimeException("数据库连接失败！");
        }

    }

    /**
     * 定义一个静态方法,用于获取数据库连接对象Connection
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * 定义一个静态方法,用于释放资源
     */
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
