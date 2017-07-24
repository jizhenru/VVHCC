package com.vvhcc.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.vvhcc.mq.RPC_client;
import com.vvhcc.utils.ConnectionUtils;
import com.vvhcc.utils.CreateSqlUtils;

import net.sf.json.JSONObject;

public class SyncTest {
	public static void main(String[] args) throws FileNotFoundException {
		// 获取字段集合
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("Table.properties");
		FileInputStream fin = new FileInputStream("test.properties");
		Properties prop2 = new Properties();
		
		
		try {
			prop.load(in);
			prop2.load(fin);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 获取字段集合
		List<String> fieldlist = new ArrayList<String>();
		String id = prop.getProperty("id");
		fieldlist.add(id);
		//String tablename = prop.getProperty("tablename");
		String laststr = prop2.getProperty("last");
		int last = Integer.parseInt(laststr);
		String field = prop.getProperty("field");
		String[] fields = field.split(",");
		for (String string : fields) {
			fieldlist.add(string);
		}
		// 获取连接
		Connection connection = ConnectionUtils.getConnection("jdbc.properties");

		// 获取数据库语句
		String sql = CreateSqlUtils.createSql();
		System.out.println(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			// 获取结果集
			ResultSet resultSet = statement.executeQuery();
			
			String data = "{}";
			JSONObject jsond = JSONObject.fromObject(data);
			while (resultSet.next()) {
				String jsondata = "{}";
				JSONObject json = JSONObject.fromObject(jsondata);
				for (String string : fieldlist) {
					String string2 = resultSet.getString(string);
					json.accumulate(string, string2);
				}
				jsond.accumulate("data", json);
			}
			
			resultSet.last();
			int row = resultSet.getRow();
			//最新行数
			if(row>0){
				last=last+row; 
			}
			
			try {
				Properties pop = new Properties();
				String str = ""+last;
				pop.setProperty("last",str);
				FileOutputStream out = new FileOutputStream("test.properties");
				pop.store(out, "key-value");
				out.flush();
				out.close();
				System.out.println(last);
				
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			String jsonstr = jsond.toString();
			//System.out.println(string);
			
			ConnectionUtils.closeResultSet(resultSet);
			ConnectionUtils.closePreparedStatement(statement);
			ConnectionUtils.closeConnection(connection);
			//MQ
			Channel channel = RPC_client.setConnection("mq01.vvcs.com", "vvadmin", "vvcs", "/");
			String message = RPC_client.sendMessage(channel, "rpc_queue3", jsonstr);
			System.out.println(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
