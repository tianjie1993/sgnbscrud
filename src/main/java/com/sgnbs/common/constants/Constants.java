package com.sgnbs.common.constants;

public interface Constants {
	
	//用户登录session键
	String SYS_USER_TOKEN = "sys_user_token";
	
	//系统中所有数据删除标识位
	Integer DEL_STATUS = 99;
	
	
	//DAO默认方法名
	String DELETEBYPRIMARYKEY="deleteByPrimaryKey";
	String INSERTSELECTIVE="insertSelective";
	String SELECTBYPRIMARYKEY="selectByPrimaryKey";
	String UPDATEBYPRIMARYKEYSELECTIVE="updateByPrimaryKeySelective";

}
