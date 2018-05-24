package com.sgnbs.common.excel;

public class ExcelImpotResult {

	private Boolean issuccess;

	private String errormsg;

	public Boolean getIssuccess() {
		return issuccess;
	}

	public void setIssuccess(Boolean issuccess) {
		this.issuccess = issuccess;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public ExcelImpotResult() {
	}

	public ExcelImpotResult(Boolean issuccess, String errormsg) {
		this.issuccess = issuccess;
		this.errormsg = errormsg;
	}
}
