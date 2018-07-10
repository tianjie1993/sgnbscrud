package com.sgnbs.ms.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sgnbs.common.constants.Eum;
import com.sgnbs.common.utils.CrudUtil;
import com.sgnbs.common.utils.MyReflectUtil;
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysPermission;
import com.sgnbs.ms.service.intf.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.Permission;
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

	@Autowired
	private SysMenuService sysMenuService;


	@GetMapping("/toallotmenu")
	@RequiresPermissions("role:allotmenu")
	public ModelAndView toallotMenu(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysrole/allotmenu");
		String id = request.getParameter("id");
		mv.addObject("roleid", id);
		return mv;
	}
	
	@GetMapping("/toallotper")
	@RequiresPermissions("role:allotper")
	public ModelAndView toallotBtn(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysrole/allotper");
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
		List<Integer> menuids = new ArrayList<>();
		List<Integer> perids = new ArrayList<>();

		if(role==null){
			return AjaxResult.error("角色不存在！");
		}else{
			if(StrUtil.notBlank(ids)){
				for(String id : ids.split(",")){
					if(id.startsWith("m")){
						menuids.add(Integer.parseInt(id.replace("m_","")));
					}else{
						perids.add(Integer.parseInt(id));
					}
				}
				role.setActionIds(StringUtils.join(perids,","));
				role.setMenuIds(StringUtils.join(menuids,","));
				sysRoleService.save(role);
			}
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


	@GetMapping("/topermissionsave")
	public ModelAndView toPermissionsave(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/syspermission/save");
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		SysPermission permission = new SysPermission();
		if(StrUtil.notBlank(id)){
			 permission = (SysPermission) CrudUtil.getEntity(SysPermission.class,id);
		}else{
			permission.setParentid(pid);
		}
		MyReflectUtil.addAnnoData(permission,false);
		mv.addObject("entity", permission);
		return mv;
	}

	@GetMapping("/LoadPermissionTree")
	@ResponseBody
	public List<Map<String,Object>> LoadPermissionTree(HttpServletRequest request){
		String check = request.getParameter("check");
		List<SysRole> roles = SysUserUtil.getUserRoles();
		Set<Integer> menuids = new HashSet<>();
		Set<Integer> perids = new HashSet();
		for(SysRole role : roles){
			if(StrUtil.notBlank(role.getMenuIds())){
				String []ids = role.getMenuIds().split(",");
				for(String id : ids){
					menuids.add(Integer.parseInt(id));
				}
			}
			if(StrUtil.notBlank(role.getActionIds())){
				String []ids = role.getActionIds().split(",");
				for(String id : ids){
					perids.add(Integer.parseInt(id));
				}
			}
		}
		List<SysMenu> menus = sysMenuService.findAll();
		List<SysPermission> permissions = sysRoleService.findAllPermission();
		List<Map<String,Object>> returnlist = new ArrayList<>();
		for(SysMenu menu : menus){
			Map<String,Object> treemap = new HashMap<String,Object>();
			treemap.put("id", "m_"+menu.getId());
			treemap.put("name", menu.getName());
			treemap.put("pid", "m_"+menu.getParentid());

			if(menuids.contains(menu.getId())){
				treemap.put("checked", true);
			}
			if(menu.getIsshow()==Eum.SysMenu.isshow.MORENZHANSHI){
				treemap.put("checked", true);
				treemap.put("chkDisabled", true);
			}
			returnlist.add(treemap);
		}
		for(SysPermission permission : permissions){
			Map<String,Object> treemap1 = new HashMap<String,Object>();
			treemap1.put("id", permission.getId());
			if("1".equals(check)){
				treemap1.put("name", permission.getName());
			}else{
				treemap1.put("name", permission.getName()+"("+permission.getPermission()+")");
			}
			treemap1.put("pid", permission.getParentid());
			if(perids.contains(permission.getId())){
				treemap1.put("checked", true);
			}
			if(permission.getType()==Eum.SysMenu.isshow.MORENZHANSHI){
				treemap1.put("checked", true);
				treemap1.put("chkDisabled", true);
			}
			returnlist.add(treemap1);
		}
		return returnlist;
	}


}
