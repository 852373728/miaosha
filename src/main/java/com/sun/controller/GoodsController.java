package com.sun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.entity.User;
import com.sun.result.Result;
import com.sun.service.GoodsService;
import com.sun.vo.GoodsDetails;
import com.sun.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;

	@RequestMapping("/list")
	public String list(Model model,User user) {
		if(user==null) {
			return "login";
		}
		model.addAttribute("goodsList", goodsService.list());
		return "goods_list";
	}
	
	@RequestMapping("/detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetails> getOne(User user,@PathVariable(value="goodsId")long goodsId) {
		
		GoodsVo goods = goodsService.getByGoodsId(goodsId);
		long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	
    	int miaoshaStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		miaoshaStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		miaoshaStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		miaoshaStatus = 1;
    		remainSeconds = 0;
    	}
    	GoodsDetails goodsDetails=new GoodsDetails();
    	goodsDetails.setGoods(goods);
    	goodsDetails.setUser(user);
    	goodsDetails.setMiaoshaStatus(miaoshaStatus);
    	goodsDetails.setRemainSeconds(remainSeconds);
		return Result.success(goodsDetails);
	}
	
	
	
	
}
