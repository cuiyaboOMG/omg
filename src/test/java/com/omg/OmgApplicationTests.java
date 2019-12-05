package com.omg;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashMultimap;
import com.omg.XmlBean.Header;
import com.omg.XmlBean.PolicyList;
import com.omg.XmlBean.Request;
import com.omg.config.CodeMsgConfig;
import com.omg.design.strategy.Standard;
import com.omg.design.strategy.StandardOne;
import com.omg.entity.User;
import com.omg.enumerate.UserType;
import com.omg.jms.producer.MyProducer;
import com.omg.mapper.UserMapper;
import com.omg.mytest.PayAssemble;
import com.omg.mytest.WxPayService;
import com.omg.mytest.arithmetic.SelectionSort;
import com.omg.service.impl.UserServiceImpl;
import com.omg.util.DateUtils;
import com.omg.util.XmlUtil;
import org.apache.commons.collections.MultiMap;
import org.apache.poi.util.IOUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OmgApplicationTests {
	@Autowired
	private MyProducer myProducer;
	@Autowired
	private MinStackTest minStackTest;
    @Autowired
    private RedisTemplate redisTemplate;
	@Autowired
    private UserMapper userMapper;
	@Autowired
	private CodeMsgConfig codeMsgConfig;
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void contextLoads() throws InterruptedException {
		String a = "aaa";
		String b = "aa" + "a";
		System.out.println(a == b);
		System.out.println(a.equals(b));
            String[] str = new String[]{"a", "b", "c", "b", "a", "b","a", "b", "c", "b", "a", "b","a", "b", "c", "b", "a", "b"};
		//按次数出现顺序排序并打印
        long beginTime = System.currentTimeMillis();
        HashMap<String, Integer> map = new HashMap<>(6);
		for (String s : str) {
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			} else {
				map.put(s, 1);
			}
		}
		List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
		entries.sort((Map.Entry<String, Integer> t1,Map.Entry<String, Integer> t2)-> t1.getKey().compareTo(t2.getKey()));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-beginTime));
        for (Map.Entry<String, Integer> t : entries) {
			System.out.println(t.getKey());
		}
		//辅助栈
	}

	@Test
	public void imoprt() throws InstantiationException, IllegalAccessException {
		File file = new File("D:/test.xlsx");
		try {
			FileInputStream input = new FileInputStream(file);
			MultipartFile multipartFile  = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			/*Workbook wb = getWorkbook(multipartFile);
			List<User> users = ExcelUtils.importExcel(User.class, wb);
			System.out.println(users);*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void xml(){
		Header header = new Header();
		header.setVersion("1.0");
		header.setErrorCode("0");
		String s = XmlUtil.convertToXml(header, "UTF-8");
		System.out.println(s);
	}

	@Test
	public void xml1(){
		BigDecimal bigDecimal = new BigDecimal(10.00);
        System.out.println(bigDecimal.toString());
		Request request = new Request();
		request.setCode(1);
		request.setTransactionId(bigDecimal.multiply(new BigDecimal(1)).toPlainString());
		PolicyList policyList = new PolicyList();
		List<String> policynumber = new ArrayList<>();
		policynumber.add("1111111111111");
		policynumber.add("2222222222222");
		policyList.setPolicynumber(policynumber);
		request.setPolicyList(policyList);
		Object o = JSONObject.toJSON(request);
		System.out.println(o.toString());
		String s = XmlUtil.convertToXml(request, "UTF-8");
		System.out.println(s);

		System.out.println(bigDecimal.setScale(2));
		System.out.println(bigDecimal.multiply(new BigDecimal(0)).toPlainString());
	}

	@Test
	public void base64(){
		String key ="<?xml version='1.0'?>\n" +
				"<XML_DATA>\n" +
				"\t<Header>\n" +
				"\t\t<Version>1.0</Version>\t\t\t\t\t //版本，目前为1.0\n" +
				"\t\t<TransactionType>Cancellation</TransactionType> // NSell表示新单购买,Cancellation表示取消，GenerateDoc表示获取pdf保单\n" +
				"\t\t<TransactionId>2011010100001</TransactionId>//合作伙伴系统中的唯一编号\n" +
				"\t\t<CallbackUrl><![CDATA[http://st.com/back]]></CallbackUrl>\n" +
				"\t</Header>\n" +
				"\t<Segment>\n" +
				"\t\t<PolicyIn>\n" +
				"\t\t    <PolicyNumber>AAA00001</PolicyNumber>  //保单号码\n" +
				"\t\t    <PolicyHolder>张三</PolicyHolder>  //投保人姓名\n" +
				"\t\t\t<TransactionApplDate>2011-01-01 15:00:00</TransactionApplDate>  //取消保单的时间\n" +
				"\t\t</PolicyIn>\n" +
				"\t</Segment>\n" +
				"</XML_DATA>";
		String trim = new BASE64Encoder().encodeBuffer(key.getBytes()).trim();
		System.out.println(trim);
	}

	@Test
	public void getMessage(){
		myProducer.send();
	}

	@Test
	public void getMin(){
		minStackTest.push(3);
		minStackTest.push(3);
		minStackTest.push(1);
		minStackTest.push(2);
		minStackTest.pop();
		minStackTest.pop();
		System.out.println("最小值："+minStackTest.getMin());
	}

	@Test
	public void math(){
		int i = 1;
		double d = 1.2d;
		float f = 1.32344465f;
		Integer a = 1;
		Integer b = 1;
		System.out.println(f);
		System.out.println(3*0.1==0.3);
		System.out.println(d==f);
		System.out.println(a==1);
		System.out.println(i+0.1);
		System.out.println(d+0.1);
	}
	@Test
	public void listTest(){
		ArrayList<String> objects = Lists.newArrayList();
		objects.add("a");
		objects.add("b");
		Iterator<String> iterator = objects.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
		String s = UUID.randomUUID().toString();
		System.out.println(s);
	}

	@Test
	public void payTest(){
		PayAssemble payAssemble = new PayAssemble(new WxPayService());
		System.out.println(payAssemble.payStart());
	}

	@Test
	public void asyn(){
		String [] str = {"a","b","c"};
		List<CompletableFuture> futures = new ArrayList<>();
		for (String s:str
			 ) {
			futures.add(CompletableFuture.supplyAsync(()->str).thenApply(e ->s+"wqq"));
		}
		System.out.println(futures.get(0));
		//test qwe
	}

	@Test
	public void dataTest(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		Date date1 = new Date();
		try {
			date = sdf.parse("2018-01-01 06:30:00");
			date1 = sdf.parse("2019-05-15 01:37:46");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date date21 = org.apache.commons.lang3.time.DateUtils.addMonths(new Date(), 2);
		System.out.println(sdf.format(date21));
		int i = DateUtils.DayDifference(date21, date1);
		System.out.println(i);
	}

	@Test
	public void LRU(){
		//LinkedHashMap实现LRU算法
		LinkedHashMap<String,String> map = new LinkedHashMap<>(16,0.75F,true);
		map.put("one","one");
		map.put("two","two");
		map.put("third","third");
		map.put("four","four");
		System.out.println(map);
		map.get("one");
		map.get("third");
		System.out.println(map);
	}

	@Test
	public void insertUser(){
		List<User> list = Lists.newArrayList();
		String[] firstName = {"张","赵","钱","孙","李","周","吴","杨","夏","崔","庄","徐","鲁","袁","陈"};
		for(int a=0;a<1000;a++){
			Random random=new Random();
			int i = random.nextInt(firstName.length - 1);
			User user = new User();
			user.setAge(random.nextInt(50));
			user.setName(firstName[i]+getChinese()+getChinese());
			user.setPassword("e10adc3949ba59abbe56e057f20f883e");
			list.add(user);
		}
        List<User> list1 =list.subList(0,500);
        userMapper.insertList(list1);
        userMapper.insertList(list.subList(500,1000));
		System.out.println();
	}

	@Test
    public void update(){
        List<User> list = Lists.newArrayList();
        User user1 = new User();
        user1.setAge(18);
        user1.setId(27);
        User user2 = new User();
        user2.setAge(18);
        user2.setId(27);
        list.add(user1);
        list.add(user2);
        userMapper.updateBatch(list);
    }

	public static String getChinese() {
		String str = null;
		int highPos, lowPos;
		Random random = new Random();
		highPos = (176 + Math.abs(random.nextInt(71)));//区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
		random=new Random();
		lowPos = 161 + Math.abs(random.nextInt(94));//位码，0xA0打头，范围第1~94列

		byte[] bArr = new byte[2];
		bArr[0] = (new Integer(highPos)).byteValue();
		bArr[1] = (new Integer(lowPos)).byteValue();
		try {
			str = new String(bArr, "GB2312");	//区位码组合成汉字
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Test
    public void jedis(){
        RedisAtomicLong test = new RedisAtomicLong("test", redisTemplate.getConnectionFactory());
        long andIncrement = test.incrementAndGet();
        System.out.println(andIncrement);
        redisTemplate.opsForValue().set("test",2);
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void childName(){
        Random random = new Random();
        String[] secondName = {"月","雪","明","秀","潞","芸","敏","灵","之","兰","佳"};
        String[] thirdName = {"怡","晓","灵","秀","潞","歌","敏","兰","佳"};
        int i = random.nextInt(secondName.length - 1);
        Random random1 = new Random();
        int j = random1.nextInt(thirdName.length - 1);
        System.out.println("李"+secondName[i]+secondName[j]);
        System.out.println(TimeUnit.MINUTES.toSeconds(300));
    }

    @Test
	public void childNameNew(){
    	String secondName = "炳段祋俊怜亮拉厘俐律娜柰南泰畋亭歪炫映昱昭者祉致籽";
    	String thirdName = "撤陈撑道灯琏撩龙卢陆录诺陶熹晓鸯晔璋";
		Random random = new Random();
		int i = random.nextInt(secondName.length());
		System.out.println(i);
		Random random1 = new Random();
		int j = random1.nextInt(thirdName.length());
		System.out.println("李"+secondName.charAt(i-1)+thirdName.charAt(j-1));
	}

	@Test
	public void childNameAll(){
		String secondName = "炳段祋俊怜亮拉厘俐律娜柰南泰畋亭歪炫映昱昭者祉致籽";
		String thirdName = "撤陈撑道灯琏撩龙卢陆录诺陶熹晓鸯晔璋璇";
		for(int i=0;i<secondName.length()-1;i++){
			for(int j=0;j<thirdName.length();j++){
				StringBuffer sb = new StringBuffer(3);
				sb.append("李");
				sb.append(secondName.charAt(i));
				sb.append(thirdName.charAt(j));
				System.out.println(sb.toString());
			}
		}
	}

	@Test
	public void dateTest() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm");
		Date parse = simpleDateFormat.parse("2019" + "-" + "01");
		System.out.println(parse);
	}

	@Test
	public void mapperTest(){
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("age",16);
		User user =new User();
		user.setPassword("123");
		userMapper.updateByExampleSelective(user,example);
	}

	@Test
	public void setCodeMsgConfig(){
		String msg = codeMsgConfig.getMsg("500");
		System.out.println(msg);
	}

	@Test
	public void enumTest(){
		BigDecimal amount = new BigDecimal(1000);
		BigDecimal taxRate = new BigDecimal(20);
		BigDecimal multiply = amount.multiply(taxRate.divide(new BigDecimal(100))).divide((taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1))),2, BigDecimal.ROUND_HALF_UP);
		System.out.println(multiply);
		BigDecimal divide = amount.multiply(taxRate.divide(new BigDecimal(100))).divide((new BigDecimal(1).subtract(taxRate.divide(new BigDecimal(100)))), 2, BigDecimal.ROUND_HALF_UP);
		System.out.println(divide);
		byte s1 = 127;
		s1 = (byte)(s1+3);
		System.out.println(s1);
		short s = 1;
		s+=1;
		System.out.println(s);
		long l = 1;
		l+=1;
		System.out.println(l);
		BigDecimal ss = new BigDecimal(92.12);
		System.out.println(ss.divide(new BigDecimal(100),4,BigDecimal.ROUND_HALF_DOWN));
		System.out.println("test:"+ss);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		System.out.println(numberFormat.format(new BigDecimal(10000)));
		BigDecimal a = new BigDecimal(0);
		if(BigDecimal.ZERO.compareTo(a)==0){
			System.out.println("进来了");
		}
	}

	@Test
	public void test1(){
		String filePath = "D:\\code\\aalib/static/official/平安车险(1)_445fba298bc04937b6027ea6cdda8d6c.xls";
		System.out.println(filePath.substring(filePath.indexOf("official")));

		User user1 = User.builder().age(50).build();
		User user2 = User.builder().age(50).build();
		Integer a = 128;
		Integer b =128 ;
		System.out.println(user2.getAge()==user1.getAge());
		System.out.println(a==b);
		System.out.println(TimeUnit.MILLISECONDS.toMinutes(1552632606636l-System.currentTimeMillis()));
		System.out.println(TimeUnit.MILLISECONDS.toHours(1552632606636l-1547777338717l));
		SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm");
		String format = sdf.format(new Date());
		System.out.println(format);
	}

	@Test
	public void WeakReferenceTest(){
		User user = new User();
		user.setType(UserType.student);
		WeakReference<User> reference = new WeakReference<>(user);
		long start = System.currentTimeMillis();
		while (true){
			if(reference.get()==null){
				long end = System.currentTimeMillis();
				System.out.println(end-start);
			}
		}
	}
	
	@Test
	public void method(){
		try {
			Class<?> selectionSort = Class.forName("com.omg.mytest.arithmetic.SelectionSort");
			Method main = selectionSort.getDeclaredMethod("test",String.class,int.class,User.class);
			main.invoke((SelectionSort)selectionSort.newInstance(),"test",1,new User());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void turn(){
		String format = String.format("我的世界", "hah");
		System.out.println(format);

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher("my\n");
		System.out.println(m.replaceAll(""));;
        System.out.println(LocalDate.now().toString());
	}

	@Test
	public void tran(){
		User user = new User();
		userService.insertUserTestTransactiona(user);
		short a = 128;
		byte b = (byte)a;
		System.out.println(a);
		System.out.println(b);
	}

	@Test
	public void design(){
		Standard standardOne = new StandardOne();
		System.out.println(standardOne.test("omg001"));
        List<String> floorIndex = Lists.newArrayList(new String []{"B1","B2","B3","B4","B5","B6","B7","B8","1F","2F","3F","4F","5F","6F","7F","8F","9F","10F","11F","12F","13F","14F","15F","16F"});
        HashMap<Long, String> test = new HashMap<>();
        test.put(1l,"一");
        String s = test.get(1l);
        System.out.println(s);
    }
}