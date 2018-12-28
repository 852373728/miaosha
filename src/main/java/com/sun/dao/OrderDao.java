package com.sun.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sun.entity.Miaoshaorder;
import com.sun.entity.Order;

@Mapper
public interface OrderDao {

	@Select("select * from `order` where userId=#{userId} and goodsId=#{goodsId}")
	public Order getByUserIdGoodsId(@Param("userId")String userId,@Param("goodsId")Long goodsId);
	
	
	@Insert("insert into `order`(userId,goodsId,goodsName,goodsCount,goodsPrice,status,createDate) values(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{status},now())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public long add(Order order);
	
	@Insert("insert into miaoshaorder(userId,orderId,goodsId) values(#{userId},#{orderId},#{goodsId})")
	public void addMiaoSha(Miaoshaorder miaoshaorder);
	
}
