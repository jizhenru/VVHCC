package com.vvhcc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class CreateTableUtils {
	
	//创建临时表
	public static Boolean createTable(){
		
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("Table.properties");
		try {
			//获取连接
			Connection con = ConnectionUtils.getConnection("jdbc2.properties");
			prop.load(in);
			String tablename = prop.getProperty("tablename");
			String id = prop.getProperty("id");
			String fieldString = prop.getProperty("field");
			String[] fields = fieldString.split(",");
			String sql = "create table if not exists "+tablename+"("+id+" varchar(32) primary key";
			for (String filed : fields) {
				sql+=","+filed+" varchar(255)";
			}
			sql=sql+")";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
			//关闭流
			ConnectionUtils.closePreparedStatement(statement);
			ConnectionUtils.closeConnection(con);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
