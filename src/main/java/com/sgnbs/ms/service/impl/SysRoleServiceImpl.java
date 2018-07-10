package com.sgnbs.ms.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgnbs.ms.dao.SysPermissionDAO;
import com.sgnbs.ms.model.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.constants.Eum;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.ms.dao.SysMenuDAO;
import com.sgnbs.ms.dao.SysRoleDAO;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.service.intf.SysRoleService;



@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Autowired
	private SysMenuDAO sysMenuDAO;

	@Autowired
	private SysPermissionDAO sysPermissionDAO;

	@Override
	public SysRole save(SysRole entity) {
		if(null!=entity) {
			if(StrUtil.notBlank(entity.getId())) {
				sysRoleDAO.updateByPrimaryKeySelective(entity);
			}else {
				entity.setId(StrUtil.getUUID());
				sysRoleDAO.insert(entity);
			}
		}
		return entity;
	}

	@Override
	public void delete(String id) {
		if(StrUtil.notBlank(id)) {
			sysRoleDAO.deleteByPrimaryKey(id);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page findPage(SysRole role, Page page) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		Page<SysRole> rolepage = (Page)sysRoleDAO.findList(role);
		for(SysRole o : rolepage.getResult()){
			String roleids = StrUtil.isBlank(o.getMenuIds())?"":o.getMenuIds();
			List<String> idlist = new ArrayList<String>();
			if(StrUtil.notBlank(roleids)){
				for(String id : roleids.split(",")){
					idlist.add(id);
				}
			}
			List<SysMenu> menus = sysMenuDAO.findByIds(idlist);
			SysMenu temp = new SysMenu();
			temp.setIsshow(Eum.SysMenu.isshow.MORENZHANSHI);
			List<SysMenu> showmenus = sysMenuDAO.findList(temp);
			menus.addAll(showmenus);
			Map<String,SysMenu> psmenumap  =new HashMap<>();
			for(SysMenu menu : menus){
				if(menu.getParentid().equals("0")){
					menu.setSemenus(new ArrayList<SysMenu>());
					psmenumap.put(String.valueOf(menu.getId()), menu);
					
				}
			}
			for(SysMenu menu : menus){
				if(menu.getParentid()!=0 && 0!=menu.getId()){
					SysMenu pmenu = psmenumap.get(String.valueOf(menu.getParentid()));
					if(pmenu!=null){
						pmenu.getSemenus().add(menu);
					}
				}
			}
			String menunames = "";
			for(String id : psmenumap.keySet()){
				SysMenu pmenu = psmenumap.get(id);
				menunames +="【"+pmenu.getName()+"：";
				List<SysMenu> secmenus = pmenu.getSemenus();
				for(int i=0;i<secmenus.size();i++){
					if(i==secmenus.size()-1)
						menunames+=secmenus.get(i).getName();
					else
						menunames+=secmenus.get(i).getName()+",";

				}
				menunames +="】</br>";
			}
			
			o.setMenunames(menunames);
		}
		return rolepage;
	}

	@Override
	public List<SysRole> getRolesByIds(String roleIds) {
		List<SysRole> rolelist = new ArrayList<SysRole>();
		if(StrUtil.isBlank(roleIds)) {
			return rolelist;
		}
		List<String> idlist =  Arrays.asList(roleIds.split(","));
		rolelist = sysRoleDAO.findByIds(idlist);
		return rolelist;
	}

	@Override
	public SysRole findById(String id) {
		return sysRoleDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<SysRole> findAll() {
		return sysRoleDAO.findAll();
	}

	@Override
	public void deleteByIds(String ids) {
		List<String> idlist = Arrays.asList(ids.split(","));
		sysRoleDAO.deleteByIds(idlist);
	}

	@Override
	public List<SysPermission> findAllPermission() {
		return sysPermissionDAO.findAll();
	}


}
