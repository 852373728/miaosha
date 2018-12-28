package com.sun.controller;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.entity.Miaoshaorder;
import com.sun.entity.User;
import com.sun.rabbitMq.MQSender;
import com.sun.rabbitMq.MiaoShaMsg;
import com.sun.redis.GoodsKey;
import com.sun.redis.RedisService;
import com.sun.result.CodeMsg;
import com.sun.result.Result;
import com.sun.service.GoodsService;
import com.sun.service.OrderService;
import com.sun.vo.GoodsVo;
import com.sun.zhujie.Dlcs;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean{

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MQSender mqSender;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> list = goodsService.list();
		if(list==null) {
			return;
		}
		for (GoodsVo goodsVo : list) {
			redisService.set(GoodsKey.goodsStock,""+goodsVo.getId(),goodsVo.getMiaoshaStock());
		}
		
	}
	
	@Dlcs(seconds=10,maxCount=5,needLogin=true)
	@RequestMapping(value="/do_miaosha",method=RequestMethod.POST)
	@ResponseBody
	public Result<Integer> do_miaosha(User user,long goodsId) {
		Miaoshaorder miaoshaorder = orderService.getByUserIdGoodsId(user.getId(),goodsId);
		if(miaoshaorder!=null) {
			return Result.error(CodeMsg.CFMS);
		}
		Long stock = redisService.decr(GoodsKey.goodsStock, ""+goodsId);
		if(stock<0) {
			return Result.error(CodeMsg.KCBZ);
		}
		MiaoShaMsg msg=new MiaoShaMsg();
		msg.setUser(user);
		msg.setGoodsId(goodsId);
		mqSender.sendMsg(msg);
		return Result.success(0);
	}
	
	@RequestMapping(value="/result",method=RequestMethod.GET)
	@ResponseBody
	public Result<Long> result(User user,long goodsId) {
		if(user==null) {
			return Result.error(CodeMsg.ERROR_LOGIN);
		}
		long orderId = orderService.orderId(user.getId(), goodsId);
		return Result.success(orderId);
	}
	
}
