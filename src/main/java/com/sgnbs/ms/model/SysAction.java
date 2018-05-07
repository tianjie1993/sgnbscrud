package com.sgnbs.ms.model;

import java.util.List;

import com.sgnbs.common.utils.EumUtil.KeyValue;
import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.IsEum;
import com.sgnbs.ms.annotation.Table;

@Table("sys_action")
public class SysAction {
	@ID
    private Integer id;

    private String url;

    @IsEum("canbatchkv")
    private Integer canbatch;

    private String btnclass;

    private String name;

    private Integer menuId;

    private Integer sort;
    
    @IsEum("typekv")
    private Integer type;

    @IsEum("isshowkv")
    private Integer isshow;

    private String winxy;

    private String permission;

    @IsEum("needidkv")
    private Integer needid;
    
    private List<KeyValue> canbatchkv;
    private List<KeyValue> typekv;
    private List<KeyValue> isshowkv;
    private List<KeyValue> needidkv;
    
    private String menuname;
    

    public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public List<KeyValue> getCanbatchkv() {
		return canbatchkv;
	}

	public void setCanbatchkv(List<KeyValue> canbatchkv) {
		this.canbatchkv = canbatchkv;
	}

	public List<KeyValue> getTypekv() {
		return typekv;
	}

	public void setTypekv(List<KeyValue> typekv) {
		this.typekv = typekv;
	}

	public List<KeyValue> getIsshowkv() {
		return isshowkv;
	}

	public void setIsshowkv(List<KeyValue> isshowkv) {
		this.isshowkv = isshowkv;
	}

	public List<KeyValue> getNeedidkv() {
		return needidkv;
	}

	public void setNeedidkv(List<KeyValue> needidkv) {
		this.needidkv = needidkv;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getCanbatch() {
        return canbatch;
    }

    public void setCanbatch(Integer canbatch) {
        this.canbatch = canbatch;
    }

    public String getBtnclass() {
        return btnclass;
    }

    public void setBtnclass(String btnclass) {
        this.btnclass = btnclass == null ? null : btnclass.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public String getWinxy() {
        return winxy;
    }

    public void setWinxy(String winxy) {
        this.winxy = winxy == null ? null : winxy.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public Integer getNeedid() {
        return needid;
    }

    public void setNeedid(Integer needid) {
        this.needid = needid;
    }
}