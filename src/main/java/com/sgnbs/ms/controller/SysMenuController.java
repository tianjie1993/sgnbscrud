package com.sgnbs.ms.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.sgnbs.common.constants.AjaxResult;
import com.sgnbs.common.constants.Eum;
import com.sgnbs.ms.annotation.SysLogAnno;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.service.intf.SysActionService;
import com.sgnbs.ms.service.intf.SysMenuService;
import com.sgnbs.ms.service.intf.SysRoleService;


@Controller
@RequestMapping({"/menu"})
public class SysMenuController extends BaseController{
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysActionService sysActionService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	
	@RequestMapping("/LoadTree")
	@ResponseBody
	public List<Map<String,Object>> loadTree(HttpServletRequest request){
		String roleid = request.getParameter("id");
		SysRole role = sysRoleService.findById(roleid);
		String menuids = null==role.getMenuIds()?"":role.getMenuIds();
		String []menu_ids = menuids.split(",");
		List<Map<String,Object>> returnlist = new ArrayList<>();
		List<SysMenu> menulist = sysMenuService.findAll();
		for(SysMenu menu : menulist){
			if(0==menu.getId()){
				continue;
			}
			Map<String,Object> treemap = new HashMap<String,Object>();
			treemap.put("id", menu.getId());
			treemap.put("pid", menu.getParentid());
			treemap.put("name", menu.getName());
			if(Arrays.asList(menu_ids).contains(String.valueOf(menu.getId()))){
				treemap.put("checked", true);
			}
			if(Eum.SysMenu.isshow.MORENZHANSHI==menu.getIsshow()) {
				treemap.put("name", menu.getName()+"(默认显示)");
				treemap.put("checked", true);
				treemap.put("chkDisabled", true);
			}
			returnlist.add(treemap);
		}
		return returnlist;
	}
	
	@Profile("dev")
	@GetMapping("/index")
	public String toIndex(){
		return "/sys/menu/menumag";
	}
	
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult getroleList(HttpServletRequest request,SysMenu menu){
		int pageno = Integer.parseInt(request.getParameter("page"));
		int pagesize = Integer.parseInt(request.getParameter("pagesize"));
		Page page = new Page(pageno,pagesize);
		return AjaxResult.success(sysMenuService.findPage(menu, page));
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public  AjaxResult delrole(SysMenu menu){
		if(null!=menu.getId()){
			sysMenuService.delete(menu.getId());
			return AjaxResult.success();
		}else{
			return AjaxResult.error("标识不能为空！");
		}
	}
	
	@GetMapping("/save")
	@SysLogAnno("保存菜单")
	public ModelAndView saverole(SysMenu menu){
		ModelAndView mv = new ModelAndView("/sys/menu/menusave");
		menu = sysMenuService.save(menu);
		mv.addObject("menu", menu);
		returnModal(SUCCESS,"操作成功!",mv);
		return mv;
	}
	

	/**
	 * 菜单通用入口
	 * @param menu
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/entrance")
	public ModelAndView menuntrancenew(SysMenu menu,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		menu = sysMenuService.findById(menu.getId());
		mv.setViewName(menu.getUrl());
		mv.addObject("fixedprams", menu.getFixedprams());
		mv.addObject("actions", sysActionService.findByMenuId(menu.getId()));
		return mv;
	}
}
