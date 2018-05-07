package com.sgnbs.ms.model;

import java.util.Date;

import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.Table;

@Table("Sys_Log")
public class SysLog {
	@ID
    private Integer id;

    private String userId;

    private String actiondesc;

    private Date actiontime;

    private String sonactionids;

    private Integer type;

    private Integer result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getActiondesc() {
        return actiondesc;
    }

    public void setActiondesc(String actiondesc) {
        this.actiondesc = actiondesc == null ? null : actiondesc.trim();
    }

    public Date getActiontime() {
        return actiontime;
    }

    public void setActiontime(Date actiontime) {
        this.actiontime = actiontime;
    }

    public String getSonactionids() {
        return sonactionids;
    }

    public void setSonactionids(String sonactionids) {
        this.sonactionids = sonactionids == null ? null : sonactionids.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}