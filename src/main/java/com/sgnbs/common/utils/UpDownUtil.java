package com.sgnbs.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpDownUtil {
	
	private static Logger logger = LoggerFactory.getLogger(UpDownUtil.class);
	
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response,String filepath) throws IOException{
		ServletOutputStream out = null;
		File file = new File(filepath); 
		if(!file.exists()) {
			throw new IOException("文件不存在");
		}
		String filename = file.getName();
		response.reset();
		// 设置输出的格式
		response.setContentType("multipart/form-data");
		filename= new String(filename.getBytes("utf-8"), "ISO_8859_1"); 
		response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
		try {
			out = response.getOutputStream();
			// 写到输出流
			out.write(getBytes(file));
			out.flush();
			out.close();
			logger.debug("---------------下载完成！");
		} catch (IOException e) {
			logger.debug("提醒：向客户端传输时出现IO异常，但此异常是允许的，有可能客户端取消了下载，导致此异常，不用关心！");
		}
	}
	
	public static void downloadFileOrginname(HttpServletRequest request, HttpServletResponse response,String filepath,String filename) throws IOException{
		ServletOutputStream out = null;
		File file = new File(filepath); 
		if(!file.exists()) {
			throw new IOException("文件不存在");
		}
		response.reset();
		// 设置输出的格式
		response.setContentType("multipart/form-data");
		filename= new String(filename.getBytes("utf-8"), "ISO_8859_1"); 
		response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
		try {
			out = response.getOutputStream();
			// 写到输出流
			out.write(getBytes(file));
			out.flush();
			out.close();
			logger.debug("---------------下载完成！");
		} catch (IOException e) {
			logger.debug("提醒：向客户端传输时出现IO异常，但此异常是允许的，有可能客户端取消了下载，导致此异常，不用关心！");
		}
	}
	
	public static byte[] getBytes(File file){  
        byte[] buffer = null;  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    
}
