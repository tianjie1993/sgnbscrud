package com.sgnbs.ms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface ListDAO {
	
	List<Map<String,String>> findSysMenuList(Map<String,String> map);

	List<Map<String,String>> findFieldcheckList(Map<String,String> map);
	
	List<Map<String,String>> findSysActionList(Map<String,String> map);
	
	List<Map<String,String>> findSysSelectkyList(Map<String,String> map);

	List<Map<String,String>> findSysUserList(Map<String,String> map);
	
	List<Map<String,String>> findSysRoleList(Map<String,String> map);

	List<Map<String,String>> findSysLogList(Map<String,String> map);

}
