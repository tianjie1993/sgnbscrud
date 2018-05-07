package com.sgnbs.ms.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sgnbs.common.constants.Eum;
import com.sgnbs.ms.model.SysAction;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.service.intf.SysActionService;
import com.sgnbs.ms.service.intf.SysMenuService;
import com.sgnbs.ms.service.intf.SysRoleService;




@Controller
@RequestMapping({"/action"})
public class SysActionController extends BaseController{
	
	@Autowired
	private SysActionService sysActionService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	
	
	
	
	
	@GetMapping("/LoadTree")
	@ResponseBody
	public List<Map<String,Object>> loadTree(HttpServletRequest request){
		String roleid = request.getParameter("id");
		SysRole role = sysRoleService.findById(roleid);
		String actionids = null==role.getActionIds()?"":role.getActionIds();
		String menuIds = null==role.getMenuIds()?"":role.getMenuIds();
		String []action_ids = actionids.split(",");
		List<Map<String,Object>> returnlist = new ArrayList<>();
		Set<SysMenu> menulist = sysMenuService.getMenusByIdsAndIsshow(menuIds,Eum.SysMenu.isshow.MORENZHANSHI);
		for(SysMenu menu : menulist){
			Map<String,Object> treemap = new HashMap<String,Object>();
			treemap.put("id", "M"+menu.getId());
			treemap.put("name", menu.getName());
			treemap.put("checked", true);
			treemap.put("chkDisabled", true);
			if(Eum.SysMenu.isshow.MORENZHANSHI.intValue()==menu.getIsshow()) {
				treemap.put("name", menu.getName()+"(默认显示)");
			}
			returnlist.add(treemap);
			List<SysAction> actionlist = new ArrayList<SysAction>();
			List<SysAction> actionlist1 = sysActionService.findByMenuId(menu.getId());
			if(null!=actionlist1) {
				actionlist.addAll(actionlist1);
			}
			for(SysAction action : actionlist) {
				Map<String,Object> actionmap = new HashMap<String,Object>();
				actionmap.put("id", action.getId());
				actionmap.put("name", action.getName());
				actionmap.put("pid", "M"+menu.getId());
				if(Arrays.asList(action_ids).contains(action.getId())){
					actionmap.put("checked", true);
				}
				if(Eum.SysMenu.isshow.MORENZHANSHI.intValue() == action.getIsshow()) {
					actionmap.put("name", action.getName()+"(默认显示)");
					actionmap.put("checked", true);
					actionmap.put("chkDisabled", true);
				}
				returnlist.add(actionmap);
			}
		}
		return returnlist;
	}
	
	
	
}
