package com.omg.mapper;

import com.omg.entity.Fee;
import java.util.List;
@org.apache.ibatis.annotations.Mapper
public interface FeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fee
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fee
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    int insert(Fee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fee
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    Fee selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fee
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    List<Fee> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fee
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    int updateByPrimaryKey(Fee record);
}