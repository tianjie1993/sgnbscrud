package com.sgnbs.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysRole;


@Mapper
public interface SysRoleDAO{
	
	SysRole findOne(SysRole t);
	
	int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

	List<SysRole> findByIds(List<String> idlist);
	
	List<SysRole> findAll();

	void deleteByIds(List<String> idlist);

	Page findList(SysRole role);
	

}
