package com.sgnbs.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sgnbs.ms.model.Attachment;

@Mapper
public interface AttachmentDAO{

	List<Attachment> findByIds(List<Integer> temp);

	Attachment findOne(Attachment t);
	
	int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);


}