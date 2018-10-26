package com.omg;

import com.omg.jms.producer.MyProducer;
import com.omg.XmlBean.Header;
import com.omg.XmlBean.PolicyList;
import com.omg.XmlBean.Request;
import com.omg.entity.*;
import com.omg.mytest.PayAssemble;
import com.omg.mytest.PaymentService;
import com.omg.mytest.WxPayService;
import com.omg.util.ExcelUtils;
import com.omg.util.XmlUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OmgApplicationTests {
	@Autowired
	private MyProducer myProducer;
	@Autowired
	private MinStackTest minStackTest;

	@Test
	public void contextLoads() {
		String a = "aaa";
		String b = "aa" + "a";
		System.out.println(a == b);
		System.out.println(a.equals(b));
		String[] str = new String[]{"a", "b", "c", "b", "a", "b"};
		//按次数出现顺序排序并打印
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
		for (Map.Entry<String, Integer> t : entries) {
			System.out.println(t.getKey());
		}
	}

	@Test
	public void imoprt() throws InstantiationException, IllegalAccessException {
		File file = new File("D:/test.xlsx");
		try {
			FileInputStream input = new FileInputStream(file);
			MultipartFile multipartFile  = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			Workbook wb = getWorkbook(multipartFile);
			List<User> users = ExcelUtils.importExcel(User.class, wb);
			System.out.println(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Workbook getWorkbook(MultipartFile file) {
		String filePath = file.getOriginalFilename();
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		InputStream stream;
		Workbook wb = null;
		try {
			stream = file.getInputStream();
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				throw new RuntimeException("文件类型错误");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
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
		Request request = new Request();
		request.setTransactionId("2w2w2w2");
		PolicyList policyList = new PolicyList();
		List<String> policynumber = new ArrayList<>();
		policynumber.add("1111111111111");
		policynumber.add("2222222222222");
		policyList.setPolicynumber(policynumber);
		request.setPolicyList(policyList);
		String s = XmlUtil.convertToXml(request, "UTF-8");
		System.out.println(s);
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
		System.out.println(a==b);
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
}