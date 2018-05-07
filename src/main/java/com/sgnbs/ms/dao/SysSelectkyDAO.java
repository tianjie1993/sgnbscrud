package com.sgnbs.ms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.SysSelectky;

@Mapper
public interface SysSelectkyDAO{
  
	
	SysSelectky findOne(SysSelectky t);
	
	int deleteByPrimaryKey(Integer id);

    int insert(SysSelectky record);

    int insertSelective(SysSelectky record);

    SysSelectky selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysSelectky record);

    int updateByPrimaryKey(SysSelectky record);
}