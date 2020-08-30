package top.hxq.httpclient.vo;

import java.io.Serializable;

public class HttpResult implements Serializable{

	
	private int status;
	
	private String content;
	
	public HttpResult(int status) {
		this.status = status;
	}
	
	public HttpResult(int status,String content) {
		this.status = status;
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
