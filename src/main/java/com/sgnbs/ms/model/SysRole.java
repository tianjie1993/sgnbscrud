package com.sgnbs.ms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.Table;


@Table("Sys_role")
public class SysRole{
	
	@ID
	private String id;

    private String name;

    private String menuIds;

    private Date createtime;

    private String roledesc;
    
    private String actionIds;
    
    private Integer type;
    
    private List<SysMenu> menulist;
    
    private String menunames;
    

    public String getMenunames() {
		return menunames;
	}


	public void setMenunames(String menunames) {
		this.menunames = menunames;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<SysMenu> getMenulist() {
    	if(null==menulist)
    		menulist = new ArrayList<SysMenu>();
		return menulist;
	}

    
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public void setMenulist(List<SysMenu> menulist) {
		this.menulist = menulist;
	}


	public String getActionIds() {
		return actionIds;
	}

	public void setActionIds(String actionIds) {
		this.actionIds = actionIds;
	}

	public String getId() {
		return id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds == null ? null : menuIds.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }

}