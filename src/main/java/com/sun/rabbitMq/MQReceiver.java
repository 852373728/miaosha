package com.sun.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.entity.Miaoshaorder;
import com.sun.entity.User;
import com.sun.redis.RedisService;
import com.sun.service.GoodsService;
import com.sun.service.OrderService;
import com.sun.vo.GoodsVo;

@Service
public class MQReceiver {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	
	
	@RabbitListener(queues=MQConfig.MIAOSHAQUEUE)
	public void receivetopic1(String msg) {
		MiaoShaMsg miaosha = RedisService.stringToBean(msg, MiaoShaMsg.class);
		User user = miaosha.getUser();
		long goodsId = miaosha.getGoodsId();
		
		GoodsVo goodsVo = goodsService.getByGoodsId(goodsId);
		if(goodsVo.getMiaoshaStock()<=0) {
			return;
		}
		Miaoshaorder miaoshaorder = orderService.getByUserIdGoodsId(user.getId(),goodsId);
		if(miaoshaorder!=null) {
			return;
		}
		orderService.addOrder(goodsVo, user);
	}
	

	/*@RabbitListener(queues=MQConfig.QUEUE)
	public void receive(String msg) {
		System.out.println("接收"+msg);
	}
	
	
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
	public void receivetopic1(String msg) {
		System.out.println("receivetopic1接收"+msg);
	}
	
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
	public void receivetopic2(String msg) {
		System.out.println("receivetopic2接收"+msg);
	}*/
	
}
