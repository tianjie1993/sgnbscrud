package com.sgnbs.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;


public class AttachUtil {
	/**
	 * 从前台产来MultipartFile 对象。保存文件。并返回文件对象
	 * @param tempfile
	 * @param uploadPath
	 * @return
	 * @throws IOException 
	 */
	public static String saveAttachFile(MultipartFile tempfile,String uploadPath) throws IOException{
		String oldName=tempfile.getOriginalFilename();
		String suffix = "";
		if(oldName.indexOf(".") != -1){
			suffix = oldName.substring(oldName.lastIndexOf("."));
		}
		String newFileName = generarteRandom()+suffix;
		File file = new File(uploadPath+newFileName);
	    InputStream ins = tempfile.getInputStream();
	    OutputStream os = new FileOutputStream(file);
	    int bytesRead = 0;
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
	    	os.write(buffer, 0, bytesRead);
	    }
	    os.close();
	    ins.close();
		return newFileName;
	}
	
	/**
	 * 文件名生成规则 System.currentTimeMillis()+6位随机数
	 * 
	 * 
	 * @return
	 */
	public static String generarteRandom() {
		return String.format("%s%s", new Object[] { System.currentTimeMillis(), random(6) });
	}

	public static String random(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(AttachUtil.generarteRandom());
	}
	
}
