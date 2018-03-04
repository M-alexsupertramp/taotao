package com.taotao.web.test;

import java.util.HashMap;
import java.util.Map;
import com.taotao.web.service.XmlApiService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:spring/applicationContext*.xml")
public class ApiServiceTest {
	
	//@Autowired
	private XmlApiService xmlApiService;
	
	
	public void testDoGetString() throws Exception{
		String content=xmlApiService.doGet("http://www.baidu.com");
		System.out.println(content);
	}
	
    public void testDoGetStringMapOfStringString() throws Exception{
		Map<String,String>params=new HashMap<String,String>();
		params.put("categoryId", "44");
		params.put("rows", "6");
		params.put("callback", "func");
		String content = xmlApiService.doGet("http://manage.taotao.com/rest/api/content", params);
		System.out.println(content);
	}

    public void testDoPostStringMapOfStringString() throws Exception {
		Map<String,String>params=new HashMap<String,String>();
		params.put("scope", "project");
		params.put("q", "java");
		params.put("fromerr", "8bDnUWwC");
		
		String content = xmlApiService.doPost("http://www.oschina.net/search",params);
		System.out.println(content);
	}
	
	public void testDoPostString() throws Exception{
		String content = xmlApiService.doPost("http://www.oschina.net/search",null);
		System.out.println(content);
	}
}
