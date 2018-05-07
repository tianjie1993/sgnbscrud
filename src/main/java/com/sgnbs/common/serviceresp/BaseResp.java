package com.sgnbs.common.serviceresp;

public class BaseResp {
	
	private static final String SUCCESS_CODE ="200";
	
	private static final String FALURE_CODE ="201";
	
	private String code;
	
	private String desc;
	
	
	public BaseResp() {}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static BaseResp success(){
		BaseResp res = new BaseResp();
		res.setCode(SUCCESS_CODE);
		res.setDesc("success");
		return res;
	}
	
	public static BaseResp error(String desc){
		BaseResp res = new BaseResp();
		res.setCode(FALURE_CODE);
		res.setDesc(desc);
		return res;
	}
	
}
