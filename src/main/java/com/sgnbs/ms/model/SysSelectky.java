package com.sgnbs.ms.model;

import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.Table;

@Table("Sys_selectky")
public class SysSelectky {
	@ID
    private Integer id;

    private String qsql;

    private String fields;

    private String fieldnames;

    private String keyto;

    private String valueto;

    private String searchinfo;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQsql() {
		return qsql;
	}

	public void setQsql(String qsql) {
		this.qsql = qsql;
	}

	public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields == null ? null : fields.trim();
    }

    public String getFieldnames() {
        return fieldnames;
    }

    public void setFieldnames(String fieldnames) {
        this.fieldnames = fieldnames == null ? null : fieldnames.trim();
    }

    public String getKeyto() {
        return keyto;
    }

    public void setKeyto(String keyto) {
        this.keyto = keyto == null ? null : keyto.trim();
    }

    public String getValueto() {
        return valueto;
    }

    public void setValueto(String valueto) {
        this.valueto = valueto == null ? null : valueto.trim();
    }

    public String getSearchinfo() {
        return searchinfo;
    }

    public void setSearchinfo(String searchinfo) {
        this.searchinfo = searchinfo == null ? null : searchinfo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}