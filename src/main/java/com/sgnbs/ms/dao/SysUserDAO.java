package com.sgnbs.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysUser;


@Mapper
public interface SysUserDAO{
	
	SysUser findOne(SysUser t);
	
	int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
	
	SysUser findByLogin(String login);

	List<SysUser> findAll(List<String> asList);

	Page findList(SysUser user);

}
