package com.omg.mapper;

import com.omg.dto.TestUserDTO;
import com.omg.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
	User findByName(@Param("username") String username);

    void insertList(List<User> data);

    void updateBatch(List<User> list);

    TestUserDTO findNames();
}
