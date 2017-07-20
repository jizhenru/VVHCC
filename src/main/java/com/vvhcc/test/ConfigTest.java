package com.vvhcc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.vvhcc.utils.ConnectionUtils;

public class ConfigTest {
	public static void main(String[] args) throws Exception {
		Properties prop1 = new Properties();
		Properties prop2 = new Properties();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream sqlin = classloader.getResourceAsStream("sql.properties");
		InputStream table = classloader.getResourceAsStream("Table.properties");
		prop1.load(table);
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
		
		//获取字段集合
		List<String> fieldlist = new ArrayList<String>();
		String id = prop1.getProperty("id");
		fieldlist.add(id);
		String tablename = prop1.getProperty("tablename");
		String field = prop1.getProperty("field");
		String[] fields = field.split(",");
		for (String string : fields) {
			fieldlist.add(string);
		}
		List<Map> list = new ArrayList<Map>();
		while(resultSet.next()){
//			int id = resultSet.getInt("id");
//			String id = resultSet.getString("id");
//			String name = resultSet.getString("name");
//			System.out.println(id+name);
			Map<String,String> map = new HashMap<String, String>();
			for (String string : fieldlist) {
				String str = resultSet.getString(string);
				map.put(string, resultSet.getString(string));
//				System.out.println(str);
			}
			list.add(map);
		}
		
		System.out.println(list);
//		Boolean b = CreateTableUtils.createTable();
//		System.out.println(b);
		
		//关闭流
		ConnectionUtils.closeResultSet(resultSet);
		ConnectionUtils.closePreparedStatement(ps);
		ConnectionUtils.closeConnection(connection);

	}
}
