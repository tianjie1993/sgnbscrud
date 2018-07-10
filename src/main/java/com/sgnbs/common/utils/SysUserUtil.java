package com.sgnbs.common.utils;

import java.util.*;

import com.sgnbs.ms.dao.SysPermissionDAO;
import com.sgnbs.ms.model.*;
import org.apache.shiro.SecurityUtils;

import com.sgnbs.common.constants.Eum;
import com.sgnbs.ms.dao.SysMenuDAO;
import com.sgnbs.ms.dao.SysRoleDAO;

public class SysUserUtil {
	
	private static SysRoleDAO  sysRoleDAO = SpringUtil.getBean(SysRoleDAO.class);
	
	private static SysMenuDAO  sysMenuDAO = SpringUtil.getBean(SysMenuDAO.class);

	private static SysPermissionDAO sysPermissionDAO = SpringUtil.getBean(SysPermissionDAO.class);


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
	

	public static Set<SysPermission> getPermissions(){
		Set<SysPermission> set = new TreeSet<>(Comparator.comparing(SysPermission::getId));
		List<SysRole> rolelist = getUserRoles();
		List<SysPermission> permissions = sysPermissionDAO.findAll();
		for(SysPermission permission : permissions){
			if(1==permission.getType()){
				set.add(permission);
			}
		}
		for(SysRole role : rolelist) {
			if(StrUtil.notBlank(role.getActionIds())) {
				String []ids = role.getActionIds().split(",");
				for(String id : ids) {
					SysPermission permission = sysPermissionDAO.selectByPrimaryKey(Integer.parseInt(id));
					if(null!=permission) {
						set.add(permission);
					}
				}
			}
		}
		return set;
	}
}
