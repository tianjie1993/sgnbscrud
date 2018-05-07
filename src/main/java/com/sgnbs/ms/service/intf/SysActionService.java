package com.sgnbs.ms.service.intf;


import java.util.List;

import com.github.pagehelper.Page;
import com.sgnbs.ms.model.SysAction;

public interface SysActionService {
	
	SysAction save(SysAction entity);
	
	List<SysAction> findByMenuIdOrIsshow(Integer id, Integer isshowShow);

	List<SysAction> findByMenuId(Integer id);

	List<SysAction> findByIsshow(Integer isshowShow);



}
