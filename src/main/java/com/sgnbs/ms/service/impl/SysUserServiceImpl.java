package com.sgnbs.ms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.ms.dao.SysRoleDAO;
import com.sgnbs.ms.dao.SysUserDAO;
import com.sgnbs.ms.model.SysMenu;
import com.sgnbs.ms.model.SysRole;
import com.sgnbs.ms.model.SysUser;
import com.sgnbs.ms.service.intf.SysUserService;



@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Override
	public SysUser findByLogin(String login) {
		SysUser user = new SysUser();
		user.setLogin(login);
		return sysUserDAO.findOne(user);
	}

	@Override
	public SysUser save(SysUser user) {
		if(null!=user) {
			if(StrUtil.notBlank(user.getId())) {
				sysUserDAO.updateByPrimaryKeySelective(user);
			}else {
				user.setId(StrUtil.getUUID());
				sysUserDAO.insert(user);
			}
		}
		return user;
	}

	@Override
	public void delete(String id) {
		if(StrUtil.notBlank(id)) {
			sysUserDAO.deleteByPrimaryKey(id);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<SysUser> findPage(SysUser user, Page page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		Page<SysUser> userpage = (Page)sysUserDAO.findList(user);
		for(SysUser o : userpage.getResult()){
			String roleids = StrUtil.isBlank(o.getRoleIds())?"":o.getRoleIds();
			List<String> idlist = new ArrayList<String>();
			if(StrUtil.notBlank(roleids)){
				for(String id : roleids.split(",")){
					idlist.add(id);
				}
			}
			List<SysRole> roles = sysRoleDAO.findByIds(idlist);
			String rolenames = "";
			for(SysRole role : roles){
				rolenames +=role.getName()+",";
			}
			if(StrUtil.notBlank(rolenames)){
				rolenames = rolenames.substring(0, rolenames.length()-1);
			}
			
			o.setRolenames(rolenames);
		}
		return userpage;
	}

	@Override
	public SysUser findById(String id) {
		SysUser user = new SysUser();
		user.setId(id);
		return sysUserDAO.findOne(user);
	}

	@Override
	public void deleteByIds(String ids) {
		List<SysUser> users = sysUserDAO.findAll(Arrays.asList(ids.split(",")));
		for(SysUser user : users) {
			user.setStatus(Constants.DEL_STATUS);
			sysUserDAO.updateByPrimaryKeySelective(user);
		}
		
	}


}
