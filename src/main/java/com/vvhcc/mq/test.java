package com.vvhcc.mq;

import com.rabbitmq.client.Channel;

public class test {
	public static void main(String[] args) {
		Channel channel = RPC_client.setConnection("mq01.vvcs.com", "vvadmin", "vvcs", "/");
			String string = RPC_client.sendMessage(channel, "rpc_queue3", "呵呵哒");
			RPC_client.close();
			System.out.println(string);
			/*StringBuffer str1 = new StringBuffer("abc");
			StringBuffer str2 = new StringBuffer("abc");
			System.out.println(str1==str2);
			System.out.println(str1.equals(str2));*/
	}
}

	class test2{
		public static void hehe(){
			System.out.println("呵呵哒！");
		}
	}
