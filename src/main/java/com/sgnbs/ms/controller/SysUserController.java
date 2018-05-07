package com.sgnbs.ms.controller;


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
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.ms.annotation.SysLogAnno;
import com.sgnbs.ms.model.SysUser;
import com.sgnbs.ms.service.intf.SysLogService;
import com.sgnbs.ms.service.intf.SysUserService;



@Controller
@RequestMapping({"/user"})
public class SysUserController extends BaseController{
	
	@Autowired
	private SysUserService sysUserService;
	
	
	@GetMapping("/checkLogin")
	@ResponseBody
	public AjaxResult checkLogin(HttpServletRequest request){
		String login = request.getParameter("login");
		if(StrUtil.isBlank(login)){
			return AjaxResult.error("登录名不能为空！");
		}else{
			SysUser user = sysUserService.findByLogin(login);
			if(user!=null)
				return AjaxResult.error("登录名已存在，请重新输入！");
		}
		return AjaxResult.success();
		
	}
	
	@GetMapping("/toresetpwd")
	public String toRegister(){
		return "/sysuser/resetpwd";
		
	}
	
	
	
	@PostMapping("/resetpwd")
	public ModelAndView resetpwd(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysuser/resetpwd");
		String oldpwd = request.getParameter("pwd");
		String newpwd = request.getParameter("newpwd");
		String confirmpwd = request.getParameter("confirmpwd");
		String pwd = SysUserUtil.getUser().getPassword();
		if(!pwd.equals(oldpwd)) {
			returnModal(ERROR, "用户密码输入错误！", mv);
		}else if(!newpwd.equals(confirmpwd)) {
			returnModal(ERROR, "两次输入密码不一致，请重新输入", mv);
		}else if(oldpwd.equals(newpwd)) {
			returnModal(ERROR, "新密码不能和旧密码一样，请重新输入", mv);
		}else {
			SysUser user = SysUserUtil.getUser();
			user.setPassword(newpwd);
			sysUserService.save(user);
			returnModal(SUCCESS,"操作成功",mv);
		}
		return mv;

	}
	
	@GetMapping("/toallotrole")
	@RequiresPermissions("user:allotrole")
	public ModelAndView toallotRole(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/sysuser/allotrole");
		String id = request.getParameter("id");
		mv.addObject("userid", id);
		return mv;
		
	}
	
	@PostMapping("/allotrole")
	@ResponseBody
	@SysLogAnno("用户分配角色")
	public AjaxResult allotRole(HttpServletRequest request){
		String ids = request.getParameter("roleids");
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.findById(userid);
		if(user==null){
			return AjaxResult.error("用户不存在！");
		}else{
			user.setRoleIds(ids);
			sysUserService.save(user);
			return AjaxResult.success();
		}
	}
	
	@PostMapping("/deletelog")
	@ResponseBody
	@SysLogAnno("删除操作记录")
	public AjaxResult deletelog(HttpServletRequest request){
		String ids = request.getParameter("id");
//		sysLogService.delete(ids);
		return AjaxResult.success();
	}
}
