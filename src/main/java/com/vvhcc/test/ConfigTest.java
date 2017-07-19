package com.vvhcc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.vvhcc.utils.ConnectionUtils;
import com.vvhcc.utils.CreateTableUtils;

public class ConfigTest {
	public static void main(String[] args) throws Exception {
		Properties prop2 = new Properties();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream sqlin = classloader.getResourceAsStream("sql.properties");
		prop2.load(sqlin);
		/*Iterator<String> iterator = properties.stringPropertyNames().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+":"+properties.getProperty(key));
		}*/
		Connection connection = ConnectionUtils.getConnection("jdbc.properties");
		
		String sql = prop2.getProperty("sql");
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery(sql);
		
		while(resultSet.next()){
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			System.out.println(id+name);
		}
		
		Boolean b = CreateTableUtils.createTable();
		System.out.println(b);
		
		//关闭流
		ConnectionUtils.closeResultSet(resultSet);
		ConnectionUtils.closePreparedStatement(ps);
		ConnectionUtils.closeConnection(connection);

	}
}
