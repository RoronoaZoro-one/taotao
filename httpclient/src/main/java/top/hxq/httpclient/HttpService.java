package top.hxq.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import top.hxq.httpclient.vo.HttpResult;

/**
 * httpClient工具类
 * 
 * @author wuzm
 * @date 2020年8月25日 下午4:39:58
 */
public class HttpService {

	private CloseableHttpClient httpClient;

	public HttpService() {
		httpClient = HttpClients.createDefault();
	}

	/**
	 * 发起get请求,携带参数
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url, Map<String, Object> params) throws Exception {
		URIBuilder builder = new URIBuilder(url);

		if (null != params) {// 设置参数
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				builder.setParameter(entry.getKey(), entry.getValue().toString());
			}
		}

		HttpGet httpGet = new HttpGet(builder.build());
		CloseableHttpResponse httpResponse = null;
		try {// 发起请求，处理返回数据
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			}
		} finally {// 关闭资源
			if (null != httpResponse) {
				httpResponse.close();
			}
			if (null != httpClient) {
				httpClient.close();
			}
		}

		return "";
	}

	/**
	 * 发起get请求,不携带参数
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url) throws Exception {
		return doGet(url, null);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		if (null != params) {
			List<NameValuePair> paramters = new ArrayList<NameValuePair>();
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				paramters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(paramters, "utf-8"));
		}

		CloseableHttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine() != null) {
				String content = "";
				if (httpResponse.getEntity() != null) {
					content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
					return new HttpResult(httpResponse.getStatusLine().getStatusCode(), content);
				}
			}
		} finally {
			if (null != httpResponse) {
				httpResponse.close();
			}
			if (null != httpClient) {
				httpClient.close();
			}
		}

		return new HttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * post请求，不带参数
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url) throws Exception {
		return doPost(url, null);
	}

	/**
	 * put请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPut(String url, Map<String, Object> params) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		if (null != params) {
			List<NameValuePair> paramters = new ArrayList<NameValuePair>();
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				paramters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			httpPut.setEntity(new UrlEncodedFormEntity(paramters, "utf-8"));
		}

		CloseableHttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpPut);
			if (httpResponse.getStatusLine() != null) {
				String content = "";
				if (httpResponse.getEntity() != null) {
					content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
					return new HttpResult(httpResponse.getStatusLine().getStatusCode(), content);
				}
			}
		} finally {
			if (null != httpResponse) {
				httpResponse.close();
			}
			if (null != httpClient) {
				httpClient.close();
			}
		}

		return new HttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * put请求，不带参数
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPut(String url) throws Exception {
		return doPut(url, null);
	}

	/**
	 * delete请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResult doDelete(String url) throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpDelete);
			if (httpResponse.getStatusLine() != null) {
				String content = "";
				if (httpResponse.getEntity() != null) {
					content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
					return new HttpResult(httpResponse.getStatusLine().getStatusCode(), content);
				}
			}
		} finally {
			if (null != httpResponse) {
				httpResponse.close();
			}
			if (null != httpClient) {
				httpClient.close();
			}
		}
		return new HttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * delete请求，携带参数
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public HttpResult doDelete(String url, Map<String, Object> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("_method", "delete");
		return doPost(url, params);
	}

}
