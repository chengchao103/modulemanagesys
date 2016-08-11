package com.chinamobile.iot.model.common;

public class CommonResult {
	public CommonResult(){
		this.ret = 0;
		this.msg = "succ";
	}

	//返回码
	private Integer ret;
	
	//返回消息
	private String msg;
	
	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
