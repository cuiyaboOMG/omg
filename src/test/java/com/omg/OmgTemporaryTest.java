package com.omg;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.omg.dto.TestUserDTO;
import com.omg.dto.UserDto;
import com.omg.entity.Fee;
import com.omg.entity.User;
import com.omg.enumerate.UserType;
import com.omg.jms.producer.MyProducer;
import com.omg.mapper.FeeMapper;
import com.omg.mapper.UserMapper;
import com.omg.service.StubFactory;
import com.omg.util.CommonUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @Author:         cyb
* @CreateDate:     2018/11/5 11:34
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OmgTemporaryTest {

    @Autowired
    MyProducer myProducer;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FeeMapper feeMapper;
    @Test
    public void try11(){
        Set<String> keySet=new HashSet<>();
        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        keySet.addAll(list);
        for(String key : keySet){
            if("c".equalsIgnoreCase(key)){
                keySet.remove(key);
            }
        }
        while (keySet.iterator().hasNext()){
            if("c".equalsIgnoreCase(keySet.iterator().next())){
                keySet.remove(keySet.iterator().next());
            }
        }
    }

    @Test
    public void completableFuture(){
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusMonths(1);
        System.out.println(yesterday.toString());
        LocalDateTime yesterdayBegin = yesterday.atTime(0,0,0);
        LocalDateTime yesterdayBegin1 = yesterday.atTime(23,59,59);
        System.out.println(yesterdayBegin);
        System.out.println(yesterdayBegin1);
    }

    @Test
    public void map(){
        int a =1;
        double b = 1d;
        float c =1f;
        System.out.println(8^2);//异或 1000 ^ 0010 = 1010
        System.out.println(10^2);//1010 ^ 0010 = 1000
    }

    //冒泡排序 时间复杂度 O(n^2)
    @Test
    public void mao_pao(){
        Integer[] str = new Integer[]{8,2,1,3,6,5};
        for(int i = 0;i<str.length;i++){
            for(int j = i+1;j<str.length;j++){
                if(str[i]>str[j]){
                    str[j] ^=str[i];//0010 ^ 1000  1010
                    str[i] ^=str[j];
                    str[j] ^= str[i];
                }
            }
        }
        for (Integer i :str) {
            System.out.println(i);
        }
    }

    @Test
    public void quictest(){
        Integer [] query = {2,1,4,3,5};
        //test pick2
        BigDecimal s = new BigDecimal(223.33);
        double v = s.doubleValue();
        String s1 = String.valueOf(v);
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(s1);
        if(match.matches()){
            System.out.println(s1);
        }else if(true){
            System.out.println("12222");
        }
    }

    @Test
    public void stream(){
        ArrayList<User> objects = Lists.newArrayList();
        User user1= new User();
        user1.setName("2F");
        User user2= new User();
        user2.setName("11F");
        User user3= new User();
        user3.setName("3F");
        objects.add(user1);
        objects.add(user2);
        objects.add(user3);
        String collect = objects.stream().sorted(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                String var1 = o1.getName();
                String var2 = o2.getName();
                if(StringUtils.isBlank(var1)||StringUtils.isBlank(var2)){
                    return 0;
                }
                if(var1.lastIndexOf("F")>0){
                    var1 = var1.substring(0,var1.lastIndexOf("F"));
                }
                if(var2.lastIndexOf("F")>0){
                    var2 = var2.substring(0,var2.lastIndexOf("F"));
                }
                return Integer.valueOf(var1).compareTo(Integer.valueOf(var2));
            }
        }).filter(CommonUtil.distinctByKey(User::getName)).map(User::getName).collect(Collectors.joining("/"));
        List<String> categoryNameList =objects.stream().filter(CommonUtil.distinctByKey(User::getName)).map(User::getName).collect(Collectors.toList());
        String s = CommonUtil.formatList(categoryNameList, "/");
        System.out.println(collect);
        System.out.println(s);
    }

    public static AtomicReference<User> atomicReference = new AtomicReference();
    @Test
    public void auto(){
        User user = new User();
        user.setAge(15);
        user.setName("张三");
        atomicReference.set(user);
        User updateUser = new User();
        updateUser.setAge(16);
        user.setName("张三");
        atomicReference.compareAndSet(user,updateUser);
        LongAdder longAdder = new LongAdder();
        longAdder.add(10l);
        System.out.println(longAdder.longValue());
    }

    @Test
    public void spi(){
        /*Class<StubFactory> stubFactoryClass = StubFactory.class;
        Class<?>[] interfaces = stubFactoryClass.getInterfaces();
        for (Class c:interfaces) {
            Method create = null;
            try {
                create = c.getMethod("create", String.class);
                create.invoke(c,"222");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        ServiceLoader<StubFactory> load = ServiceLoader.load(StubFactory.class);
        for (StubFactory s:load) {
            System.out.println(s.create("222"));
        }
    }

    @Test
    public void symbolicCalculation(){
        int var1 = 2;//0001
        int var2 = 2;//0001
        int i = var1 & var2;
        int i1 = var1 ^ var2;
        int i2 = var1 << 2;
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);

        String s = buildFloor("B1/1F");
        System.out.println(s);
    }

    private String buildFloor(String target){
        String replace = target.replace("/", " ");
        String[] s = replace.split(" ");
        List<String> result = Lists.newArrayList();
        for (String s1:s) {
            if(s1.substring(0,1).equals("B")){
                result.add(s1);
                continue;
            }
            String floor = s1.substring(0, s1.length()-1);
            String unit = s1.substring(s1.length() - 1);
            s1 = unit+floor;
            result.add(s1);
        }
        return org.apache.commons.lang.StringUtils.join(result, " ");
    }

    @Test
    public void firstCase(){
        String s = "Abc";
        char[] chars = s.toCharArray();
        chars[0]+= 32;
        System.out.println(String.valueOf(chars));

        LocalDate localDate = LocalDate.parse("20200711",DateTimeFormatter.ofPattern("yyyyMMdd"));
       /* LocalDate add = localDate.plusMonths(1l);
        String addformat = add.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        System.out.println(String.format("%s及其之前月份费用已结算，该变更仅对%s及其之后的费用生效，确认是否提交？","2019-02",addformat));*/
        System.out.println(localDate);


    }

    @Test
    public void jdbc(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://47.101.155.4:3306/omg","root","123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()){
                int anInt = resultSet.getInt(1);
                String string = resultSet.getString(2);
                System.out.println(anInt+"name:"+string);
            }
            int row = resultSet.getRow();
            System.out.println(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ex(){
        String ts = "{\"data\":\"{\"condType\":1,\"condValue\":\"286181\"}\",\"format\":\"json\",\"timestamp\":\"2020-03-31 10:31:21\"}";
        String s = StringEscapeUtils.unescapeJava(ts);
        System.out.println(s);

        String s1 = LocalDateTime.now().toString();
        System.out.println(s1);
        int sec =3 ;
        int i = sec & 0x1FFF;
        System.out.println(i);
        myProducer.send();
    }

    @Test
    public void longToS(){
        Long l = 1000l;
        System.out.println(l.toString());
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now().minusHours(1);
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
        System.out.println(format);
        LocalDateTime parse = LocalDateTime.parse("2020-07-22 16:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
        System.out.println(parse);

        LocalDate monthOfLastDate=LocalDate.parse("2020-05-01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd")).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(monthOfLastDate);
    }

    @Test
    public void typeHandler(){
        TestUserDTO byName = userMapper.findNames();
        System.out.println(byName);
        int expireMsecs = (int) TimeUnit.MINUTES.toMillis(1l);
        System.out.println(expireMsecs);
    }

    //mybatis 拦截器实现分表插入
    @Test
    public void mybatisInterceptor(){
        Fee record = new Fee();
        record.setFeeAmt(BigDecimal.TEN);
        record.setFeeDate(new Date());
        Fee fee = feeMapper.selectByPrimaryKey(1l);
        System.out.println(JSON.toJSONString(fee));
        Fee fee1 = feeMapper.selectByPrimaryKey(1l);
    }

    @Test
    public void testSpring() throws NoSuchFieldException, IllegalAccessException {
        Class<DefaultSingletonBeanRegistry> aClass = DefaultSingletonBeanRegistry.class;
        Field mapField = aClass.getDeclaredField("earlySingletonObjects");
        mapField.setAccessible(true);
        Object o = mapField.get("2323");
        System.out.println(o.toString());
        Type mapMainType = mapField.getGenericType();
        // 为了确保安全转换，使用instanceof
        if (mapMainType instanceof ParameterizedType) {
            // 执行强制类型转换
            ParameterizedType parameterizedType = (ParameterizedType)mapMainType;
            // 获取基本类型信息，即Map
            Type basicType = parameterizedType.getRawType();
            System.out.println("基本类型为："+basicType);
            // 获取泛型类型的泛型参数
            Type[] types = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < types.length; i++) {
                System.out.println("第"+(i+1)+"个泛型类型是："+types[i]);
            }
        } else {
            System.out.println("获取泛型类型出错!");
        }
    }

    @Test
    public void retainAll() throws ClassNotFoundException {
        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>(16);
        hashMap.put("xxx",1);
        hashMap.put("xxx1",2);

        HashMap<String,Integer> map = new HashMap<>();
        map.put(null,1);
        Class<User> userClass = User.class;
        Class<?> name = Class.forName("com.omg.entity.User");
        User user = new User();
        Class<? extends User> aClass = user.getClass();
    }

    @Test
    public void lunXun(){
        int i = 0;
        int [] a = new int[]{1,2};
        int[] arr = {10,9,8,7,6,5,4,3,2,1,0};
        int index = 0;
        for (; i < 15; i++) {
            int nextIndex = (index+1) % arr.length;
            System.out.println("取模运算："+nextIndex);
            index = nextIndex;
            System.out.println(arr[index]);
        }
    }

    @Autowired
    private Map<String,StubFactory> map;

    @Test
    public void getIoc(){
        System.out.println(map.size());
    }

    @Test
    public void getOne(){
        Integer[] arr = {10,9,1,7,6,5,4,3,2,1,0};
        List<Integer> list = Lists.newArrayList();
        int a = 0;
        for(int i =0;i<arr.length;i++){
            if(arr[i] != 1 ){
                list.add(arr[i]);
            }else {
                a++;
            }
        }
        for(int j=0;j<a;j++){
            list.add(1);
        }

        System.out.println(list.toString());
    }

    static HashMap<String, Integer > hashMap=new HashMap<String,Integer>();

    //给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
    @Test
    public void zhongfu(){
        String s = "abcabc";
        //"abcabcabcabc";
        int i = (s + s).indexOf(s, 1);
        boolean b = i != s.length();
        System.out.println(b);
    }

    @Test
    public void groupBy(){
        List<UserDto> list = Lists.newArrayList();
        UserDto userDto = new UserDto();
        userDto.setDate(LocalDate.now());
        userDto.setName("张毅");
        userDto.setType(UserType.student);
        UserDto userDto1 = new UserDto();
        userDto1.setDate(LocalDate.now());
        userDto1.setName("张毅");

        UserDto userDto2 = new UserDto();
        userDto2.setDate(LocalDate.now().minusYears(1));
        userDto2.setName("张毅2");

        list.add(userDto);
        list.add(userDto1);
        list.add(userDto2);
        Map<String, List<UserDto>> collect = list.stream().collect(Collectors.groupingBy(l -> getYearMonth(l.getDate(),l.getName())));
        System.out.println(collect);
    }

    private static String getYearMonth(LocalDate localDate,String name) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"))+name;
    }
}
