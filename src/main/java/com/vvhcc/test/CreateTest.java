package com.vvhcc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class CreateTest {
	public static void main(String[] args) throws Exception{
		//加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		
		//java.sql.DriverManager.registerDriver(new Driver());
		
		//打开数据库链接
		String URL = "jdbc:mysql://db02.vvcs.com:3306/test?useUnicode=true&characterEncoding=utf-8";
		Connection connection = DriverManager.getConnection(URL, "JZR", "123456");
		Statement statement = connection.createStatement();
		
		//创建数据表
		String sql = "create table if not exists VVHCC_DATA(id int,name varchar(20));";
		statement.executeUpdate(sql);
		String sql1 = "insert into VVHCC_DATA(id,name) values(4,'呵呵哒')"; 
		statement.executeUpdate(sql1);
		
	}
}
