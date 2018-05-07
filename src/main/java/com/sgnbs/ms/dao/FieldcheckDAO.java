package com.sgnbs.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.Fieldcheck;

@Mapper
public interface FieldcheckDAO{
	
     List<Fieldcheck> findAll();
     
     Fieldcheck findOne(Fieldcheck record);
 	
 	 int deleteByPrimaryKey(Integer id);

     int insert(Fieldcheck record);

     int insertSelective(Fieldcheck record);

     Fieldcheck selectByPrimaryKey(Integer id);

     int updateByPrimaryKeySelective(Fieldcheck record);

     int updateByPrimaryKey(Fieldcheck record);
}