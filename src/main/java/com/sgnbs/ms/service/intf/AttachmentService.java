package com.sgnbs.ms.service.intf;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sgnbs.ms.model.Attachment;

public interface AttachmentService {

	Attachment save(Attachment entity);

	void delete(Integer id);

	Attachment findById(Integer id);

	Attachment UploadFile(MultipartFile file);

	List<Attachment> UploadFiles(List<MultipartFile> multifiles);

	List<Attachment> findByIds(String attachmentids);
}