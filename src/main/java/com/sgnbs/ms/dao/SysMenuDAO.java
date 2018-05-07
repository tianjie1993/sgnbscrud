package com.sgnbs.ms.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.SysMenu;

@Mapper
public interface SysMenuDAO{
	
	SysMenu findOne(SysMenu t);
	
	int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
	
	List<SysMenu>  findByUuidInOrderBySort(Collection<String> args);

	List<SysMenu> findByParentidOrUuid(String parentid,String uuid);

	List<SysMenu> findByIsshow(String isshowShow);


	List<SysMenu> findAll();

	List<SysMenu> findByIds(List<String> idlist);

	List<SysMenu> findList(SysMenu temp);

	List<SysMenu> findCanChoseMenu();
	
	List<SysMenu> findShowMenu();

	List<SysMenu> findByUuidInOrIsshowOrderBySort(List<String> asList, Integer isshowShow);

	

}
