package com.sun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.dao.GoodsDao;
import com.sun.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	public List<GoodsVo> list(){
		return goodsDao.list();
	}
	
	public GoodsVo getByGoodsId(long goodsId) {
		return goodsDao.getByGoodsId(goodsId);
	}
	
	public boolean jskc(Long goodsId) {
		int jskc = goodsDao.jskc(goodsId);
		return jskc>0;
	}
	
	
	
}
