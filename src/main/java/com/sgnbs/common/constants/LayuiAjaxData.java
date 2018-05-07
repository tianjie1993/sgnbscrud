package com.sgnbs.common.constants;

public class LayuiAjaxData {
	
	private static final String SUCCESS_CODE ="0";
	
	private static final String FALURE_CODE ="1";

	
	private  String code;
	
	private String msg;
	
	private Long count;
	
	private Object data;
	
	public LayuiAjaxData(){
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static LayuiAjaxData success(Object o,Long count) {
		LayuiAjaxData data = new LayuiAjaxData();
		data.setCode(SUCCESS_CODE);
		data.setData(o);
		data.setCount(count);
		return data;
	}

}
