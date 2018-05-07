package com.sgnbs.ms.model;

import java.util.Date;

import com.sgnbs.ms.annotation.Table;

@Table("Attachment")
public class Attachment {

	private Integer id;
	/**
	 * 文件原始�?
	 */
	private String orginname;

	/**
	 * 文件�?
	 */
	private String filename;

	/**
	 * 文件访问路径
	 */
	private String path;

	/**
	 * 文件上传时间
	 */
	private Date createtime;

	/**
	 * 上传�?
	 */
	private String userId;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrginname() {
		return orginname;
	}
	public void setOrginname(String orginname) {
		this.orginname = orginname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
