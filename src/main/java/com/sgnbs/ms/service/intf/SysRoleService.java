package com.sgnbs.ms.service.intf;


import java.util.List;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysRole;


public interface SysRoleService {
	
	void delete(String id);
	
	Page<SysRole> findPage(SysRole role,Page page);

	List<SysRole> getRolesByIds(String roleIds);
	
	SysRole findById(String id);

	List<SysRole> findAll();

	SysRole save(SysRole role);

	void deleteByIds(String ids);


}
