package com.sgnbs.ms.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgnbs.ms.dao.SysActionDAO;
import com.sgnbs.ms.dao.SysMenuDAO;
import com.sgnbs.ms.model.SysAction;
import com.sgnbs.ms.service.intf.SysActionService;



@Service
public class SysActionServiceImpl implements SysActionService {
	
	@Autowired
	private SysActionDAO sysActionDAO;

	@Override
	public List<SysAction> findByMenuIdOrIsshow(Integer Id, Integer isshowShow) {
		SysAction action = new SysAction();
		action.setMenuId(Id);
		action.setIsshow(isshowShow);
		return sysActionDAO.findList(action);
	}

	@Override
	public List<SysAction> findByMenuId(Integer id) {
		SysAction action = new SysAction();
		action.setMenuId(id);
		return sysActionDAO.findList(action);
	}


	@Override
	public List<SysAction> findByIsshow(Integer isshowShow) {
		SysAction action = new SysAction();
		action.setIsshow(isshowShow);
		return sysActionDAO.findList(action);
	}

	@Override
	public SysAction save(SysAction entity) {
		// TODO Auto-generated method stub
		return null;
	}


}
