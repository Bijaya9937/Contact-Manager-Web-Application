package com.smart.message;

public class MyMessage {
	
	String content;
	String type;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MyMessage(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}

}
