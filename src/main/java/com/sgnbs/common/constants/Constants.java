package com.sgnbs.common.constants;

public interface Constants {
	
	//用户登录session键
	static final String SYS_USER_TOKEN = "sys_user_token";
	
	//系统中所有数据删除标识位
	static final Integer DEL_STATUS = 99;
	
	
	//DAO默认方法名
	public static final String DELETEBYPRIMARYKEY="deleteByPrimaryKey";
	public static final String INSERTSELECTIVE="insertSelective";
	public static final String SELECTBYPRIMARYKEY="selectByPrimaryKey";
	public static final String UPDATEBYPRIMARYKEYSELECTIVE="updateByPrimaryKeySelective";

}
