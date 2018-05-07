package com.sgnbs.ms.service.intf;

import java.util.List;

import com.sgnbs.ms.model.Fieldcheck;

public interface FieldcheckService {

	List<Fieldcheck> findByTablenameAndField(String tablename,String field);

}