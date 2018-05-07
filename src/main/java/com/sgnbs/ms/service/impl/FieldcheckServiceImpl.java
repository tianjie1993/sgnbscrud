package com.sgnbs.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.ms.dao.FieldcheckDAO;
import com.sgnbs.ms.model.Fieldcheck;
import com.sgnbs.ms.service.intf.FieldcheckService;

@Service
public class FieldcheckServiceImpl implements FieldcheckService {

	@Autowired
	private FieldcheckDAO fieldcheckDAO;

	
	@Override
	public List<Fieldcheck> findByTablenameAndField(String tablename, String field) {
		List<Fieldcheck> returnlist = new ArrayList<Fieldcheck>();
		if(SysCache.eumlist.isEmpty()) {
			SysCache.eumlist.addAll(fieldcheckDAO.findAll());
		}
		for(Fieldcheck check : SysCache.eumlist) {
			if(check.getTablename().equalsIgnoreCase(tablename) && check.getFieldname().equalsIgnoreCase(field)) {
				returnlist.add(check);
			}
		}
		return returnlist;
	}
}