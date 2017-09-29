package com.alibaba.dingtalk.openapi.springbootdemo.integration.message;


public class TextMessage extends Message {
	
	public String content;
	
	public TextMessage(String content) {
		super();
		this.content = content;
	}
	
	@Override
	public String type() {
		return "text";
	}
}
