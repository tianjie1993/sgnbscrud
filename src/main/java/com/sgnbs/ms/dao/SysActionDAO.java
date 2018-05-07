package com.sgnbs.ms.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.Fieldcheck;
import com.sgnbs.ms.model.SysAction;


@Mapper
public interface SysActionDAO{
	
	SysAction findOne(SysAction record);
 	
	int deleteByPrimaryKey(Integer id);

    int insert(SysAction record);

    int insertSelective(SysAction record);

    SysAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAction record);

    int updateByPrimaryKey(SysAction record);

	List<SysAction> findList(SysAction temp);
}