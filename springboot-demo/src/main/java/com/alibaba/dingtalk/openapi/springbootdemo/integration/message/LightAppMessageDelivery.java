package com.alibaba.dingtalk.openapi.springbootdemo.integration.message;

public class LightAppMessageDelivery extends MessageDelivery {

	public String touser;
	public String toparty;
	public String agentid;
	
	public LightAppMessageDelivery(String toUsers, String toParties, String agentId) {
		this.touser = toUsers;
		this.toparty = toParties;
		this.agentid = agentId;
	}
	
}
