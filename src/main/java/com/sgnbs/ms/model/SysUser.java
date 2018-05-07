package com.sgnbs.ms.model;

import java.util.Date;

import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.Table;

@Table("Sys_user")
public class SysUser{
	@ID
	private String id;
	private String name;
	private String login;
	private String password;
	private Date createtime;
	private String roleIds;
	private Integer status;
	
	private String rolenames;
	
	
	
	public String getRolenames() {
		return rolenames;
	}
	public void setRolenames(String rolenames) {
		this.rolenames = rolenames;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	
	public String getId() {
		return id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
