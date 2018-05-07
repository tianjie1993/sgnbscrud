package com.sgnbs.ms.model;

import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.Table;

@Table("Fieldcheck")
public class Fieldcheck {
	@ID
    private Integer id;

    private String tablename;

    private String fieldname;

    private String eums;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname == null ? null : fieldname.trim();
    }

    public String getEums() {
        return eums;
    }

    public void setEums(String eums) {
        this.eums = eums == null ? null : eums.trim();
    }
}