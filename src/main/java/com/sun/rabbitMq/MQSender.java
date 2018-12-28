package com.sun.rabbitMq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.redis.RedisService;

@Service
public class MQSender {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	
	
	public void sendMsg(MiaoShaMsg msg) {
		String msg1=RedisService.BeanToString(msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHAQUEUE, msg1);
	}
	
	
	/*public void send(Object msg) {
		String msg1=RedisService.BeanToString(msg);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg1);
	}
	
	public void sendtopic(Object msg) {
		String msg1=RedisService.BeanToString(msg);
		amqpTemplate.convertAndSend(MQConfig.TOPICEXCHANGE,"key.1",msg1+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPICEXCHANGE,"key.2",msg1+"2");
	}*/

	
	
	
}
