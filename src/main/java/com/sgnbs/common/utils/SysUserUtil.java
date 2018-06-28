package com.sgnbs.common.utils;

import java.util.*;

import org.apache.shiro.SecurityUtils;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.constants.Eum;
import com.sgnbs.ms.dao.SysActionDAO;
import com.sgnbs.ms.dao.SysMenuDAO;
import com.sgnbs.ms.dao.SysRoleDAO;
import com.sgnbs.ms.model.SysAction;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.model.SysUser;

public class SysUserUtil {
	
	private static SysRoleDAO  sysRoleDAO = SpringUtil.getBean(SysRoleDAO.class);
	
	private static SysMenuDAO  sysMenuDAO = SpringUtil.getBean(SysMenuDAO.class);

	private static SysActionDAO  sysActionDAO = SpringUtil.getBean(SysActionDAO.class);

	public static SysUser getUser() {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if(null==user) 
			return new SysUser();
		else
			return user;
			
	}
	
	public static List<SysRole> getUserRoles(){
		List<SysRole> list =  new ArrayList<SysRole>();
		SysUser user = getUser();
		if(StrUtil.notBlank(user.getRoleIds())) {
			list = sysRoleDAO.findByIds(Arrays.asList(user.getRoleIds().split(",")));
		}
		return list;
	}
	
	public static List<SysMenu> getUserMenus(){
		Set<SysMenu> set = new TreeSet<>(Comparator.comparing(SysMenu::getId));
		List<SysMenu> list = new ArrayList<SysMenu>();
		List<SysRole> rolelist = getUserRoles();
		SysMenu temp = new SysMenu();
		temp.setIsshow(Eum.SysMenu.isshow.MORENZHANSHI);
		set.addAll(sysMenuDAO.findList(temp));
		for(SysRole role : rolelist) {
			if(StrUtil.notBlank(role.getMenuIds())) {
				set.addAll(sysMenuDAO.findByIds(Arrays.asList(role.getMenuIds().split(","))));
			}
		}
		list.addAll(set);
		return list;
	}
	
	public static List<SysAction> getUserActions(){
		Set<SysAction> set = new HashSet<SysAction>();
		List<SysAction> list = new ArrayList<SysAction>();
		List<SysRole> rolelist = getUserRoles();
		SysAction temp = new SysAction();
		temp.setIsshow(Eum.SysMenu.isshow.MORENZHANSHI);
		set.addAll(sysActionDAO.findList(temp));
		for(SysRole role : rolelist) {
			if(StrUtil.notBlank(role.getActionIds())) {
				String []ids = role.getActionIds().split(",");
				for(String id : ids) {
					SysAction action = sysActionDAO.selectByPrimaryKey(Integer.parseInt(id));
					if(null!=action) {
						set.add(sysActionDAO.selectByPrimaryKey(Integer.parseInt(id)));
					}
				}
			}
		}
		list.addAll(set);
		return list;
	}
}
