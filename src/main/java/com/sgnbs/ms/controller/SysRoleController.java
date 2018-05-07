package com.sgnbs.ms.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sgnbs.common.constants.AjaxResult;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.ms.annotation.SysLogAnno;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.model.SysUser;
import com.sgnbs.ms.service.intf.SysRoleService;
import com.sgnbs.ms.service.intf.SysUserService;



@Controller
@RequestMapping({"/role"})
public class SysRoleController extends BaseController{
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@GetMapping("/toallotmenu")
	@RequiresPermissions("role:allotmenu")
	public ModelAndView toallotMenu(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysrole/allotmenu");
		String id = request.getParameter("id");
		mv.addObject("roleid", id);
		return mv;
	}
	
	@GetMapping("/toallotbtn")
	@RequiresPermissions("role:allotbtn")
	public ModelAndView toallotBtn(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysrole/allotbtn");
		String id = request.getParameter("id");
		mv.addObject("roleid", id);
		return mv;
	}
	
	
	
	@PostMapping("/allotmenu")
	@ResponseBody
	@SysLogAnno("角色分配菜单")
	public AjaxResult allotmenu(HttpServletRequest request){
		String ids = request.getParameter("menuids");
		String roleid = request.getParameter("roleid");
		SysRole role = sysRoleService.findById(roleid);
		if(role==null){
			return AjaxResult.error("用户不存在！");
		}else{
			role.setMenuIds(ids);
			sysRoleService.save(role);
			return AjaxResult.success();
		}
	}
	

	@PostMapping("/allotbtn")
	@ResponseBody
	@SysLogAnno("角色分配按钮")
	public AjaxResult allotbtn(HttpServletRequest request){
		String ids = request.getParameter("btnids");
		String roleid = request.getParameter("roleid");
		SysRole role = sysRoleService.findById(roleid);
		if(role==null){
			return AjaxResult.error("用户不存在！");
		}else{
			role.setActionIds(ids);
			sysRoleService.save(role);
			return AjaxResult.success();
		}
	}
	
	@GetMapping("/LoadTree")
	@ResponseBody
	public List<Map<String,Object>> loadTree(HttpServletRequest request){
		String userid = request.getParameter("id");
		SysUser user = sysUserService.findById(userid);
		String roleids = null==user.getRoleIds()?"":user.getRoleIds();
		String []role_ids = roleids.split(",");
		List<Map<String,Object>> returnlist = new ArrayList<>();
		List<SysRole> rolellist = sysRoleService.findAll();
		for(SysRole role : rolellist){
			Map<String,Object> treemap = new HashMap<String,Object>();
			treemap.put("id", role.getId());
			treemap.put("name", role.getName());
			if(Arrays.asList(role_ids).contains(String.valueOf(role.getId()))){
				treemap.put("checked", true);
			}
			returnlist.add(treemap);
		}
		return returnlist;
	}
	
}
