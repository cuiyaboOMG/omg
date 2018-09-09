package com.omg.mapper;

import com.omg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends Mapper{
//	@Select("SELECT * FROM user WHERE name = #{username}")
	User findByName(@Param("username") String username);
}
