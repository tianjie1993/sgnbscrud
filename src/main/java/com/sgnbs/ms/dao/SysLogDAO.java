package com.sgnbs.ms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.SysLog;


@Mapper
public interface SysLogDAO{

	List<Map<String, String>> findAll();
	
	SysLog findOne(SysLog t);
	
	int deleteByPrimaryKey(SysLog id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
