package com.sgnbs.ms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.sgnbs.common.constants.AjaxResult;
import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.servlet.ValidateCodeServlet;
import com.sgnbs.common.utils.SpringUtil;
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.common.utils.YmlConfig;
import com.sgnbs.ms.annotation.SysLogAnno;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysUser;

@Controller
public class LoginController extends BaseController {

	@RequestMapping("/toLogin")
	public ModelAndView toLogin() {
		ModelAndView mv = new ModelAndView("/login");
		return mv;

	}

	@GetMapping("/toIndex")
	public ModelAndView toIndex(HttpSession session) {
		ModelAndView mv = new ModelAndView("/index");
		List<SysMenu> usermenulist = new ArrayList<>();
		Map<Integer, SysMenu> menumap = new TreeMap<>();
		usermenulist = SysUserUtil.getUserMenus();
		for (SysMenu menu : usermenulist) {
			if (1==menu.getParentid()) {
				menumap.put(menu.getId(), menu);
			}
		}
		for (SysMenu menu : usermenulist) {
			if (menumap.get(menu.getParentid()) != null && 1!=menu.getParentid()) {
				menumap.get(menu.getParentid()).getSemenus().add(menu);
			}
		}
		for (Integer id : menumap.keySet()) {
			// 二级菜单排序
			if(null!=menumap.get(id).getSemenus()) {
				Collections.sort(menumap.get(id).getSemenus());
			}
		}
		usermenulist = new ArrayList<SysMenu>(menumap.values());
		// 一级菜单排序
		Collections.sort(usermenulist);

		mv.addObject("user", session.getAttribute(Constants.SYS_USER_TOKEN));
		mv.addObject("menus", usermenulist);
		mv.addObject("active", active);
		return mv;

	}

	@PostMapping("/login")
	@ResponseBody
	@SysLogAnno("登录系统")
	public AjaxResult Login(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		AjaxResult ar = null;
		String type = request.getParameter("remeberMe");
		boolean checkres = false;
		if (StringUtils.isNotBlank(type) && "true".equals(type)) {
			checkres = true;
		}
		// 验证码
		String vcode = request.getParameter("vcode");
		if (StringUtils.isBlank(vcode)) {
			ar = AjaxResult.error("验证码不能为空");
			return ar;
		}
		String v = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		if (!vcode.equalsIgnoreCase(v)) {
			ar = AjaxResult.error("验证码填写错误");
			return ar;
		}
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(request.getParameter("login"),
				request.getParameter("password"), checkres);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录
			SysUser user = (SysUser) subject.getPrincipal();
			session.setAttribute(Constants.SYS_USER_TOKEN, user);
			ar = AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			ar = AjaxResult.error("用户名或密码错误！");
		}
		return ar;
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "/login";
	}

}
