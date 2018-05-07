package com.sgnbs.ms.service.intf;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysUser;

public interface SysUserService {
	
	SysUser findByLogin(String login);
	
	SysUser save(SysUser user);
	
	void delete(String uuid);
	
	Page<SysUser> findPage(SysUser user,Page page);
	
	SysUser findById(String id);

	void deleteByIds(String ids);


}
