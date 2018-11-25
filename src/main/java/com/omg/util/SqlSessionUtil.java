package com.omg.util;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @Author:         cyb
* @CreateDate:     2018/11/22 10:52
*/
public class SqlSessionUtil {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public <T> void saveList(List<T> list,Class clazz){
        long startTime = System.currentTimeMillis();
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            Mapper mapper = (Mapper)sqlSession.getMapper(clazz);
            int index =0;
            for(T t:list){
                index++;
                mapper.insert(t);
                if(index % 100 ==0){
                    logger.info("第{}批导入成功...", index / 100);
                    sqlSession.commit();
                }
            }
            sqlSession.commit();
            logger.info("批量新增成功，耗时：{}ms",System.currentTimeMillis()-startTime);
        }catch (Exception e){
            logger.error("错误信息：{}",e.getMessage());
        }finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}
