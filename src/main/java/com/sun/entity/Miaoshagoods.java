package com.sun.entity;

import java.util.Date;

public class Miaoshagoods {
	
	private Long id;
	private Long goodsId;
	private Float miaoshaPrice;
	private Integer miaoshaStock;
	private Date startDate;
	private Date endDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
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
