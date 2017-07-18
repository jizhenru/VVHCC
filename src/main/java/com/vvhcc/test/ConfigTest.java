package com.vvhcc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConfigTest {
	public static void main(String[] args) throws Exception {
		Properties prop1 = new Properties();
		Properties prop2 = new Properties();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream configin = classloader.getResourceAsStream("config.properties");
		InputStream sqlin = classloader.getResourceAsStream("sql.properties");
		//InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
		prop1.load(configin);
		prop2.load(sqlin);
		/*Iterator<String> iterator = properties.stringPropertyNames().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+":"+properties.getProperty(key));
		}*/
		String url = prop1.getProperty("url");
		String driver = prop1.getProperty("driver");
		String username = prop1.getProperty("username");
		String password = prop1.getProperty("password");
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, username, password);
		Statement statement = connection.createStatement();
		
		String sql = prop2.getProperty("sql");
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()){
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			System.out.println(id+name);
		}
		
//		System.out.println(url);
//		System.out.println(sql);
	}
}
