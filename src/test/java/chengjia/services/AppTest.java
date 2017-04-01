package chengjia.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chengjia.commons.call.CallConfig;
import com.chengjia.commons.exception.CustomException;
import com.chengjia.commons.utils.CallCenterUtils;
import com.chengjia.commons.utils.DateUtils;
import com.chengjia.commons.utils.FileUtils;
import com.chengjia.commons.utils.JsonUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testFileUtils() {
		try {
			FileUtils.delFile("C:\\1d.txt");
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}
	
	public void testCall() throws Exception{
		long t = System.currentTimeMillis();
		CallCenterUtils callCenterUtils = new CallCenterUtils();
		String timeStamp = DateUtils.getDateStrWithoutSeparator(new Date());
		String result = callCenterUtils.login(CallConfig.APPVER,timeStamp,CallConfig.USER_TEST,CallConfig.PWD,CallConfig.token);
		System.out.println("登录返回"+result);
		
		Map j = new HashMap();
		j.put("dn", "0001");
		j.put("tel", "13762368336");
		List data = new ArrayList();	
		data.add(j);
		result = callCenterUtils.addExtensionDn(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST, "4006659949", JsonUtils.toJson(data), "0001", "13762368336");
		System.out.println("添加分机返回"+result);
		
		System.out.println(System.currentTimeMillis()-t);
		result = callCenterUtils.queryExtensionDn(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST, "4006659949", "0001");
		System.out.println("查询分机返回"+result);
		
		result = callCenterUtils.delExtensionDn(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST, "4006659949","0001");
		System.out.println("删除分机返回"+result);
		
		result = callCenterUtils.queryExtensionDn(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST, "4006659949", "0001");
		System.out.println("删除后再查询分机返回"+result);
		
		result = callCenterUtils.batchQueryExtensionDn(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST,"4006659949","10","1");
		System.out.println("批量查询返回"+result);
		
		long c = callCenterUtils.getExtensionDnCount(CallConfig.APPVER, timeStamp, CallConfig.USER_TEST,"4006659949","10","1");
		System.out.println("分机数"+c);
		
	}
}
