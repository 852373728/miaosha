package com.sun.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.dao.OrderDao;
import com.sun.entity.Miaoshaorder;
import com.sun.entity.Order;
import com.sun.entity.User;
import com.sun.redis.OrderKey;
import com.sun.redis.RedisService;
import com.sun.vo.GoodsVo;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private GoodsService GoodsService;
	@Autowired
	RedisService redisService;
	
	
	public long orderId(String userId,Long goodsId) {
		Miaoshaorder msr = getByUserIdGoodsId(userId,goodsId);
		if(msr!=null) {
			return msr.getOrderId();
		}else {
			boolean flag= getGoodsOver(goodsId);
			if(flag) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	
	

	public Miaoshaorder getByUserIdGoodsId(String userId,Long goodsId) {
		return redisService.get(OrderKey.orderkey, ""+userId, Miaoshaorder.class);
		//return orderDao.getByUserIdGoodsId(userId, goodsId);
	}
	
	@Transactional
	public Order addOrder(GoodsVo goodsVo,User user) {
		boolean jskc = GoodsService.jskc(goodsVo.getId());
		if(jskc) {
			Order order =new Order();
			order.setUserId(user.getId());
			order.setGoodsId(goodsVo.getId());
			order.setGoodsName(goodsVo.getName());
			order.setGoodsCount(1);
			order.setGoodsPrice(goodsVo.getMiaoshaPrice());
			order.setCreateDate(new Date());
			order.setStatus(0);
			orderDao.add(order);
			Miaoshaorder miaoshaOrder=new Miaoshaorder();
			miaoshaOrder.setUserId(user.getId());
			miaoshaOrder.setOrderId(order.getId());
			miaoshaOrder.setGoodsId(goodsVo.getId());
			orderDao.addMiaoSha(miaoshaOrder);
			redisService.set(OrderKey.orderkey, ""+user.getId(), miaoshaOrder);
			return order;
		}else {
			setGoodsOver(goodsVo.getId());
			return null;
		}
		
	}

	private void setGoodsOver(Long id) {
		redisService.set(OrderKey.goodsOver, ""+id, true);
	}
	
	private boolean getGoodsOver(Long goodsId) {
		return redisService.exist(OrderKey.goodsOver, ""+goodsId);
	}
	
}
