package com.sgnbs.common.excel;

import com.sgnbs.common.excel.callback.ExcelCallback;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class ExcelDataImportUtil {

	public ExcelDataImportUtil() {
		if(null==ExcelThreadCache.threadPool) {
			ExcelThreadCache.threadPool = new ThreadPoolExecutor(2, 10, 3,
					TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}
	}

	public void  Import(String filename) {
		ExcelThreadCache.threadPool.execute(new ExcelImportTask(new File(filename)));
	}

	public void  Import(String filename, ExcelCallback excelCallback)  {
		ExcelThreadCache.threadPool.execute(new ExcelImportTask(new File(filename),excelCallback));
	}
	
}
