package top.hxq.httpclient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HttpServiceTest {
	
	private HttpService httpService;

	@Before
	public void setUp() throws Exception {
		httpService = new HttpService();
	}

	@Test
	public void testDoGetString() throws Exception {
		String url = "http://manage.taotao.com/rest/item/interface/42";
		String string = httpService.doGet(url);
		System.out.println(string);
	}

	@Test
	public void testDoPostStringMapOfStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoPutStringMapOfStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoDeleteString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoDeleteStringMapOfStringObject() {
		fail("Not yet implemented");
	}

}
