package com.sgnbs.ms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sgnbs.common.utils.AttachUtil;
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.ms.dao.AttachmentDAO;
import com.sgnbs.ms.model.Attachment;
import com.sgnbs.ms.service.intf.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Value("${filesavepath}")
	private String filesavepath;
	@Value("${filevisitpath}")
	private String filevisitpath;

	@Autowired
	private AttachmentDAO attachmentDAO;

	@Override
	public Attachment save(Attachment entity) {
		if(null!=entity) {
			if(null!=entity.getId()) {
				 attachmentDAO.updateByPrimaryKeySelective(entity);
			}else {
				 attachmentDAO.insert(entity);
			}
		}
		return entity;
	}

	@Override
	public void delete(Integer id) {
		attachmentDAO.deleteByPrimaryKey(id);
	}

	@Override
	public Attachment findById(Integer id) {
		return attachmentDAO.selectByPrimaryKey(id);
	}

	@Override
	public Attachment UploadFile(MultipartFile file) {
		Attachment attach = new Attachment();
		attach.setUserId(SysUserUtil.getUser().getId());
		attach.setCreatetime(new Date());
		attach.setOrginname(file.getOriginalFilename());
		String filename;
		try {
			filename = AttachUtil.saveAttachFile(file, filesavepath);
			attach.setFilename(filename);
			attach.setPath(filevisitpath+filename);
			attachmentDAO.insert(attach);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return attach;
	}

	@Override
	public List<Attachment> UploadFiles(List<MultipartFile> multifiles) {
		List<Attachment> lists= new ArrayList<Attachment>();
		for(MultipartFile  file : multifiles) {
			Attachment attach = new Attachment();
			attach.setUserId(SysUserUtil.getUser().getId());
			attach.setCreatetime(new Date());
			attach.setOrginname(file.getOriginalFilename());
			String filename;
			try {
				filename = AttachUtil.saveAttachFile(file, filesavepath);
				attach.setFilename(filename);
				attach.setPath(filevisitpath+filename);
				attachmentDAO.insert(attach);
				lists.add(attach);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return lists;
	}

	@Override
	public List<Attachment> findByIds(String attachmentids) {
		String [] ids = attachmentids.split(",");
		List<Integer> temp = new ArrayList<Integer>();
		for(String id : ids) {
			temp.add(Integer.parseInt(id));
		}
		return attachmentDAO.findByIds(temp);
	}
}