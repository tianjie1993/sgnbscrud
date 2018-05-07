package com.sgnbs.common.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties()
public class YmlConfig {
	
	private String filevisitpath;
	
	private String filesavepath;
	
	private String databaseType;
	
	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getFilesavepath() {
		return filesavepath;
	}

	public void setFilesavepath(String filesavepath) {
		this.filesavepath = filesavepath;
	}

	public String getFilevisitpath() {
		return filevisitpath;
	}

	public void setFilevisitpath(String filevisitpath) {
		this.filevisitpath = filevisitpath;
	}

	
}