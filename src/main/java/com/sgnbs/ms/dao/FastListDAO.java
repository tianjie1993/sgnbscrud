package com.sgnbs.ms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface FastListDAO {
	
	
	List<Map<String,String>> findSysMenuList(Map<String,String> map);



}
