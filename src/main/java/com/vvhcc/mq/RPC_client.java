package com.vvhcc.mq;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RPC_client {
	private static Connection connection;
	private static Channel channel;
	private static String replyQueueName;

	// 获取通道
	public static Channel setConnection(String mq_host, String mq_username, String mq_password, String mq_virtualhost) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(mq_host);
		factory.setUsername(mq_username);
		factory.setPassword(mq_password);
		factory.setVirtualHost(mq_virtualhost);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			return channel;
		} catch (IOException | TimeoutException e) {
			System.out.println("获取MQ连接失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	//发送数据
	public static String sendMessage(Channel channel ,String queue_name, String message){
		try {
			replyQueueName = channel.queueDeclare().getQueue();
			final String corrId = UUID.randomUUID().toString();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
			channel.basicPublish("", queue_name, props, message.getBytes("UTF-8"));
			final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
			channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
			  @Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					if (properties.getCorrelationId().equals(corrId)) {
						response.offer(new String(body, "UTF-8"));
	     		}
		    	}
			});
			try {
				return response.take();
			} catch (InterruptedException e) {
				System.out.println("消息接收异常！");
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//关闭连接
	public static void close(){
		try {
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			System.out.println("流关闭异常！");
			e.printStackTrace();
		}
	}

}
