package com.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sun.vo.GoodsVo;

@Mapper
public interface GoodsDao {

	@Select(value="select g.*,mg.miaoshaPrice, mg.miaoshaStock, mg.startDate,mg.endDate from miaoshagoods mg left join goods g on mg.goodsId= g.id")
	public List<GoodsVo> list();
	
	@Select(value="select g.*,mg.miaoshaPrice, mg.miaoshaStock, mg.startDate,mg.endDate from miaoshagoods mg left join goods g on mg.goodsId= g.id where g.id=#{goodsId}")
	public GoodsVo getByGoodsId(@Param("goodsId")long goodsId);
	
	@Update("update miaoshagoods set miaoshaStock=miaoshaStock-1 where goodsId=#{goodsId} and miaoshaStock>0")
	public int jskc(@Param("goodsId")Long goodsId);
	
	
}
