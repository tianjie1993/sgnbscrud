package com.sgnbs.ms.model;


import com.sgnbs.common.utils.EumUtil;
import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.IsEum;
import com.sgnbs.ms.annotation.Table;

import java.util.List;

@Table("sys_permission")
public class SysPermission {

    @ID
    private Integer id;

    private String permission;

    private String remark;

    private String parentid;

    @IsEum("typekvs")
    private Integer type;

    private String name;

    private List<EumUtil.KeyValue> typekvs;

    public List<EumUtil.KeyValue> getTypekvs() {
        return typekvs;
    }

    public void setTypekvs(List<EumUtil.KeyValue> typekvs) {
        this.typekvs = typekvs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}