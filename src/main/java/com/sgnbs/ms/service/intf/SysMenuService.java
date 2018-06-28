package com.sgnbs.ms.service.intf;


import java.util.List;
import java.util.Set;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysMenu;



public interface SysMenuService {
	
	SysMenu save(SysMenu user);
	
	void delete(Integer uuid);
	
	Page<SysMenu> findPage(SysMenu Menu, Page page) ;

	List<SysMenu> getMenusByIds(String menuIds);

	List<SysMenu> findAll();

	SysMenu findById(Integer uuid);


	List<SysMenu> findByShowMenu(Integer morenzhanshi);

	Set<SysMenu> getMenusByIdsAndIsshow(String menuIds, Integer morenzhanshi);

}
