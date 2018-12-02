package com.omg.mapper;

import com.omg.domain.RepeatBean;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @Author:         cyb
* @CreateDate:     2018/11/28 13:41
*/
public interface CommonMapper {
    Map<String,Object> getExistsCount(@Param("table")String table, @Param("params") Map<String, Object> map, @Param("majorBean")RepeatBean majorBean);
}
