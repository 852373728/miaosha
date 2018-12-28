package com.sun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sun.entity.User;

@Mapper
public interface UserDao {
	
	@Select("select * from user where id=#{id}")
	public User login(@Param("id")String id);
	

}
