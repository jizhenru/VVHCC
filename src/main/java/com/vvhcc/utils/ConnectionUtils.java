package com.vvhcc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
	//参数
	private static String url;
	private static String driver;
	private static String username;
	private static String password;
	
	//获取连接
	public static Connection getConnection(String jdbcProperties){
		
		Properties proper = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(jdbcProperties);
		try {
			proper.load(inputStream);
			driver=proper.getProperty("driver");
			url = proper.getProperty("url");
			username = proper.getProperty("username");
			password = proper.getProperty("password");
		} catch (IOException e) {
			System.out.println("配置文件流异常！");
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("获取连接失败！");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载失败！");
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭连接
	public static void closeConnection(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("连接关闭异常！");
				e.printStackTrace();
			}
		}
	}
	
	//关闭结果集
	public static void closeResultSet(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("结果集关闭异常！");
				e.printStackTrace();
			}
		}
	}
	
	//关闭预处理
	public static void closePreparedStatement(PreparedStatement ps){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				System.out.println("预处理语句关闭异常！");
				e.printStackTrace();
			}
		}
	}
}
