package com.vvhcc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class CreateSqlUtils {
	
	@Test
	public static String createSql(){
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("Table.properties");
		String querySql = "";
		
		try {
			prop.load(in);
			String tablename = prop.getProperty("tablename");
			String id = prop.getProperty("id");
			String fieldString = prop.getProperty("field");
			Integer limit = Integer.parseInt(prop.getProperty("limit"));
			FileInputStream fin = new FileInputStream("test.properties");
			Properties prop2 = new Properties();
			prop2.load(fin);
			Integer last = Integer.parseInt(prop2.getProperty("last"));
//			String[] fields = fieldString.split(",");
			querySql ="select "+id+","+ fieldString +" from "+tablename+" limit "+last+","+limit;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return querySql;
	}
	
}
