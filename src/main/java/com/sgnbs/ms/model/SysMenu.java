package com.sgnbs.ms.model;

import java.util.ArrayList;
import java.util.List;

import com.sgnbs.common.utils.EumUtil.KeyValue;
import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.IsEum;
import com.sgnbs.ms.annotation.Table;


@Table("sys_menu")
public class SysMenu implements Comparable<SysMenu>{
	
	@ID
    private Integer id;

	private String name;

	private String url;

	private Integer parentid;

	private Integer sort;

	private String icon;

	@IsEum("shows")
	private Integer isshow;
	
	private String fixedprams;

    private List<SysMenu> semenus;
    
    private List<KeyValue> shows;
    
    
	public String getFixedprams() {
		return fixedprams;
	}

	public void setFixedprams(String fixedprams) {
		this.fixedprams = fixedprams;
	}

	public List<KeyValue> getShows() {
		return shows;
	}

	public void setShows(List<KeyValue> shows) {
		this.shows = shows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}


	public List<SysMenu> getSemenus() {
    	if(null==semenus)
    		semenus = new ArrayList<SysMenu>();
		return semenus;
	}

	public void setSemenus(List<SysMenu> semenus) {
		this.semenus = semenus;
	}

	@Override
	public int compareTo(SysMenu o) {
		return (int) (this.sort-o.getSort());
	}
}