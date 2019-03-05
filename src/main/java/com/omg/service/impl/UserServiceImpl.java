package com.omg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.omg.entity.User;
import com.omg.enumerate.UserType;
import com.omg.mapper.UserMapper;
import com.omg.service.UserService;
import com.omg.util.ExcelUtils;
import com.omg.util.RedisService;
import com.omg.util.SpringContextHolder;
import com.omg.util.excel.ImportResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.AdaptiveRandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: cui
 * @Date: 2018-09-02 12:20
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public User findByName(String name) {
        User value = redisService.getValue(name, User.class);
        if(value!=null){
            return value;
        }
        User byName = userMapper.findByName(name);
        byName.setType(UserType.student);
        redisService.set(name,byName, 5L);
        return byName;
    }

    @Override
    public Map<String, String> verifyCode() throws IOException {
       ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        // 验证码字符组成
        AdaptiveRandomWordFactory wordFactory = new AdaptiveRandomWordFactory();
        wordFactory.setCharacters("1234567890");
        wordFactory.setMinLength(4);
        wordFactory.setMaxLength(4);
        cs.setWordFactory(wordFactory);
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setHeight(40);
        cs.setWidth(140);
        RandomFontFactory fontFactory = new RandomFontFactory();
        fontFactory.setMinSize(30);
        fontFactory.setMaxSize(30);
        cs.setFontFactory(fontFactory);
        //int filter = new Random().nextInt(5);
        int filter = 3;
        switch (filter) {
            case 0: // 干扰线
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1: // 大理石纹
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2: // 振动纹
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3: // 摆动纹
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4: // 扩散纹
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Captcha captcha = cs.getCaptcha();
        ImageIO.write(captcha.getImage(), "png", output);
        String base64 = "data:image/png;base64," + Base64.encodeBase64String(output.toByteArray());
        String verifyCodeId = UUID.randomUUID().toString();
        redisService.set(verifyCodeId,captcha.getChallenge());
        Map<String, String> result = new HashMap<String, String>();
        result.put("verifyCodeId", verifyCodeId);
        result.put("img", base64);
        logger.debug("验证码:{}",captcha.getChallenge());
        return result;
    }

    @Override
    public Map<String, String> login(String userName, String password, String verifyCode, String verifyCodeId, String s, String s1) {
        Map<String, String> map = new HashMap<>();
        String value = redisService.getValue(verifyCodeId, String.class);
        if(StringUtils.isBlank(verifyCode)|| !verifyCode.equalsIgnoreCase(value)){
            map.put("status","failure");
            map.put("message","验证码错误");
            return map;
        }
        User query = new User();
        query.setName(userName);
        User user = userMapper.selectOne(query);
        if(user==null || !StringUtils.equals(user.getPassword(),password)){
            map.put("status","failure");
            map.put("message","账号或密码错误");
            return map;
        }
        String token = UUID.randomUUID().toString();
        map.put("status","success");
        map.put("name",userName);
        map.put("token",token);
        redisService.set(token,user);
        return map;
    }

    @Override
    public void importFile(MultipartFile file) {
        try {
            ImportResult importResult = ExcelUtils.importExcel(User.class, userMapper, file);
            if(importResult.getStatus()){
                List<User> data = importResult.getData();
                userMapper.insertList(data);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String insertUser(User userDto) {
        //消息队列
        jmsMessagingTemplate.convertAndSend("user", JSONObject.toJSONString(userDto));
        return "success";
    }

    //事物测试 b方法报错回滚  a 不受影响
    @Transactional(propagation = Propagation.REQUIRED)
    public String insertUserTestTransactiona(User userDto) {
        User user = new User();
        user.setAge(25);
        user.setName("芸歌");
        userMapper.insert(user);
        try {
            SpringContextHolder.getBean(UserServiceImpl.class).b();
        }catch (Exception e){

        }
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void b() {
        User user = new User();
        user.setName("波波");
        user.setAge(25);
        userMapper.insert(user);
        if(1/0>0){
            logger.error("报错");
        }

    }
}
