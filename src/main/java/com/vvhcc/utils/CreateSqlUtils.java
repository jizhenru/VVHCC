package com.vvhcc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CreateSqlUtils {
	
	public static Boolean createSql(){
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("Table.properties");
		String tablename = prop.getProperty("tablename");
		String id = prop.getProperty("id");
		String fieldString = prop.getProperty("field");
//		String[] fields = fieldString.split(",");
		String querySql ="select "+id+","+ fieldString +" form "+tablename;
		return null;
	}
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("Table.properties");
		prop.load(in);
		String tablename = prop.getProperty("tablename");
		String id = prop.getProperty("id");
		String fieldString = prop.getProperty("field");
//		String[] fields = fieldString.split(",");
		String querySql ="select "+id+","+ fieldString +" form "+tablename;
		System.out.println(querySql);
	}
}
