package com.omg.service.base;

import com.omg.domain.RepeatBean;
import com.omg.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author:         cyb
* @CreateDate:     2018/11/28 13:37
*/
@Service
public class BaseService {

    @Autowired
    private CommonMapper commonMapper;
    /**
     * 通用判重复
     * @param id 主键 用于更新和新增判断
     * @param clazz
     * @param repeatBeans 需要判断的字段以及值
     * @return
     */
    private Boolean exist(Serializable id, Class clazz, RepeatBean...repeatBeans){
        Table annotation = (Table)clazz.getAnnotation(Table.class);
        Field[] fields = clazz.getDeclaredFields();
        String major = "";
        for (Field field:fields){
            if(field.isAnnotationPresent(Id.class)){
                major = field.getName();
                break;
            }
        }
        if(annotation==null){
            return false;
        }
        String name = annotation.name();
        Map<String,Object> map = new HashMap<>();
        for(RepeatBean bean:repeatBeans){
            map.put(bean.getColumn(),bean.getValue());
        }
        RepeatBean majorBean = new RepeatBean();
        majorBean.setColumn(major);
        majorBean.setValue(id);
        List<Object> record = commonMapper.getExistsCount(name,map,majorBean);
        return record!=null&&record.size()>0;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     * @param request
     * @return
     */
    public final static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
