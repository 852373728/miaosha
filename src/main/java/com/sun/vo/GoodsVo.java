package com.sun.vo;

import java.util.Date;

import com.sun.entity.Goods;

public class GoodsVo extends Goods{

	private Float miaoshaPrice;
	private Integer miaoshaStock;
	private Date startDate;
	private Date endDate;
	public Float getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Float miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
	public Integer getMiaoshaStock() {
		return miaoshaStock;
	}
	public void setMiaoshaStock(Integer miaoshaStock) {
		this.miaoshaStock = miaoshaStock;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
