package com.sgnbs.ms.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgnbs.common.utils.CrudUtil;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.ms.annotation.DelDo;
import com.sgnbs.ms.annotation.SaveDo;
import com.sgnbs.ms.annotation.ToDetail;
import com.sgnbs.ms.annotation.ToSave;
import com.sgnbs.ms.dao.SysMenuDAO;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.service.intf.SysMenuService;



@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDAO sysMenuDAO;
	

	@Override
	public SysMenu save(SysMenu entity) {
		if(null!=entity) {
			if(null!=entity.getId()) {
				sysMenuDAO.updateByPrimaryKeySelective(entity);
			}else {
				sysMenuDAO.insert(entity);
			}
		}
		return entity;
	}

	@Override
	public void delete(Integer id) {
		if(null!=id) {
			sysMenuDAO.deleteByPrimaryKey(id);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<SysMenu> findPage(SysMenu Menu, Page page) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		Page<SysMenu> Menupage = (Page)sysMenuDAO.findList(Menu);
		return Menupage;
	}

	@Override
	public List<SysMenu> getMenusByIds(String menuIds) {
		List<SysMenu> menulist = new ArrayList<SysMenu>();
		if(StrUtil.notBlank(menuIds)){
			String []ids = menuIds.split(",");
 			for(String id : ids){
 				menulist.add(sysMenuDAO.selectByPrimaryKey(Integer.parseInt(id)));
			}
		}
		return menulist;
	}
	
	@Override
	public List<SysMenu> findAll() {
		return sysMenuDAO.findAll();
	}

	@Override
	public SysMenu findById(Integer uuid) {
		return sysMenuDAO.selectByPrimaryKey(uuid);
	}

	@Override
	public List<SysMenu> findByParentMenu() {
		return sysMenuDAO.findByParentidOrUuid("0","0");
	}


	@Override
	public List<SysMenu> findByShowMenu(Integer isshowShow) {
		SysMenu menu = new SysMenu();
		menu.setIsshow(isshowShow);
		return sysMenuDAO.findList(menu);
	}

	@Override
	public Set<SysMenu> getMenusByIdsAndIsshow(String menuIds, Integer isshowShow) {
		Set<SysMenu> menus = new HashSet<SysMenu>();
		for(String id : menuIds.split(",")) {
			if(StrUtil.notBlank(id)) {
				menus.add(sysMenuDAO.selectByPrimaryKey(Integer.parseInt(id)));
			}
		}
		menus.addAll(sysMenuDAO.findShowMenu());
		return menus;
	}

	@ToSave(SysMenu.class)
	@ToDetail(SysMenu.class)
	public void toSave(ModelAndView mv, SysMenu menu) {
		mv.addObject("menus", sysMenuDAO.findCanChoseMenu());
	}
	
}
