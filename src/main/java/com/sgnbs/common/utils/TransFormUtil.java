package com.sgnbs.common.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sgnbs.common.constants.Eum;
import com.sgnbs.ms.annotation.ListTransf;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.service.intf.SysMenuService;
import com.sgnbs.ms.service.intf.SysRoleService;

/**
 * 通用查询字段结果处理类
 * @author tianj
 *
 */
@Component
public class TransFormUtil {
	
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysRoleService sysRoleService;
	
	@ListTransf("Date")
	public String Date(Object value) throws ParseException {
		if(StrUtil.isBlank(String.valueOf(value))){
			return "";
		}
		return DateTools.parseYMDDate(String.valueOf(value), DateTools.YYYYMMDD);
		
	}
	
	@ListTransf("time")
	public String TimeStamp(Object value) throws ParseException {
		if(StrUtil.isBlank(String.valueOf(value))){
			return "";
		}
		return DateTools.parseYMDHSMDate(String.valueOf(value), DateTools.YYYYMMDDHHMMSS);
	}
	
	@ListTransf("userrole")
	public String getUserRole(Object value) throws ParseException {
		String rolenames = "";
		if(StrUtil.notBlank(String.valueOf(value))){
			String []ids = String.valueOf(value).split(",");
			for(int i=0;i<ids.length;i++) {
				if(i==ids.length-1) {
					rolenames +=sysRoleService.findById(ids[i]).getName();
				}else {
					rolenames +=sysRoleService.findById(ids[i]).getName()+",";
				}
			}
		}
		return rolenames;
	}
	
	
	@ListTransf("menusname")
	public String getMenunames(Object value) throws ParseException {
		if(StrUtil.isBlank(String.valueOf(value))){
			return "";
		}
		String menuIds = String.valueOf(value);
		List<String> idlist = new ArrayList<String>();
		if(StrUtil.notBlank(menuIds)){
			for(String id : menuIds.split(",")){
				idlist.add(id);
			}
		}
		List<SysMenu> menus = sysMenuService.getMenusByIds(menuIds);
		List<SysMenu> showmenus = sysMenuService.findByShowMenu(Eum.SysMenu.isshow.MORENZHANSHI);
		menus.addAll(showmenus);
		Map<Integer,SysMenu> psmenumap  =new HashMap<>();
		for(SysMenu menu : menus){
			if(1==menu.getParentid()){
				menu.setSemenus(new ArrayList<SysMenu>());
				psmenumap.put(menu.getId(), menu);
				
			}
		}
		for(SysMenu menu : menus){
			if(menu.getParentid()!=1  && 1!=menu.getId()){
				SysMenu pmenu = psmenumap.get(menu.getParentid());
				if(pmenu!=null){
					pmenu.getSemenus().add(menu);
				}
			}
		}
		String menunames = "";
		for(Integer id : psmenumap.keySet()){
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
		
		return menunames;	
		
	}
	

}
