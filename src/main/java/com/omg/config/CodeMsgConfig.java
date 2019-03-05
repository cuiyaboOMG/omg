package com.omg.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * 将配置中的信息存在map中
* @Author:         cyb
* @CreateDate:     2019/2/12 14:06
*/
@Configuration
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "aalib.code.msg")//匹配配置文件中的前缀
public class CodeMsgConfig{

    /**代码消息映射配置*/
    private Map<String,String> map;

    public String getMsg(String code){
        return String.valueOf(StringUtils.defaultString(map.get(code),"-"));
    }


    /**
     * 获取 代码消息映射配置
     * @return msg
     */
    public Map<String, String> getMap() {
        return map;
    }


    /**
     * 设置 代码消息映射配置
     * @param map 代码消息映射配置
     */
    public void setMap(Map<String, String> map) {
        this.map = map;
    }


}
