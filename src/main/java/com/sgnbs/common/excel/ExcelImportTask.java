package com.sgnbs.common.excel;

import com.sgnbs.common.excel.callback.ExcelCallback;
import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.common.utils.*;
import com.sgnbs.ms.annotation.ListTransf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelImportTask implements Runnable {

    private File excelfile;

    private ExcelCallback excelCallback;

    private Workbook workbook;

    private File logfile;

    private Object model;

    private FileWriter fw;

    public ExcelImportTask(File excelfile, ExcelCallback excelCallback) {
        this.excelfile = excelfile;
        this.excelCallback = excelCallback;
    }

    public ExcelImportTask(File excelfile) {
        this.excelfile = excelfile;
    }

    @Override
    public void run() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        PlatformTransactionManager txManager = SpringUtil.getBean(PlatformTransactionManager.class);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            if(!checkExcel()){
                return;
            }
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                writeLog("sheet【"+(i+1)+"】开始校验数据！,共"+(sheet.getLastRowNum()-2)+"条数据");
                List datalist = importExcel(sheet);
                if(datalist.size()!=sheet.getLastRowNum()-2){
                    writeLog("sheet【"+(i+1)+"】数据未全部校验通过，暂不执行插入操作！");
                    continue;
                }
                writeLog("sheet【"+(i+1)+"】数据校验通过，开始执行插入操作！");
                try {
                    CrudUtil.insertEntitys(datalist);
                } catch (Exception e) {
                    e.printStackTrace();
                    writeLog("sheet【"+(i+1)+"】数据插入错误，联系管理员！");
                    continue;
                }
                if(null!=excelCallback){
                    writeLog("开始执行回调方法！");
                    ExcelImpotResult result = excelCallback.doCallback(datalist);
                    if(!result.getIssuccess()){
                        writeLog("执行回调方法错误，错误信息:"+result.getErrormsg());
                        writeLog("回滚数据！");
                        //目前支持多个不同sheet插入。用删除代替事务回滚
//                        CrudUtil.delEntitys(datalist);
                        txManager.rollback(status);
                    }else{
                        writeLog("回调方法执行成功，sheet【"+(i+1)+"】:数据插入成功！");
                    }
                }else{
                    txManager.commit(status);
                    writeLog("sheet【"+(i+1)+"】数据插入成功！");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
                log.info("关闭日志文件流");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        excelfile.delete();
    }


    private List importExcel(Sheet sheet) {
        List datalist = new ArrayList();
        if(!checkSheet(sheet)){
            return datalist;
        }
        Class modelclz = model.getClass();
        Row frow = sheet.getRow(0);
        Row srow = sheet.getRow(1);
        for (int i = 3; i <=sheet.getLastRowNum(); i++) {
            Object o = null;
            try {
                o = modelclz.newInstance();
            } catch (Exception e) {
            }
            Row datarow = sheet.getRow(i);
            boolean canadd = true;
            for (int j = 0; j <frow.getLastCellNum() ; j++) {
                try {
                    Cell cell = datarow.getCell(j);
                    String cellvalue = tranValue(getValue(cell),getValue(srow.getCell(j)));
                    if(StrUtil.isBlank(cellvalue)){
                        continue;
                    }
                    String fieldname = StrUtil.getCamelCasekey(getValue(frow.getCell(j)));
                    Field field;

                     field =  modelclz.getDeclaredField(fieldname);
                     field.setAccessible(true);
                     Class fieldtype = field.getType();
                     if(Integer.class.equals(fieldtype)){
                         Method m = modelclz.getMethod("set"+StrUtil.firstCharToUpperCase(fieldname),Integer.class);
                         m.invoke(o,Integer.parseInt(cellvalue));
                     }else if(Long.class.equals(fieldtype)){
                         Method m = modelclz.getMethod("set"+StrUtil.firstCharToUpperCase(fieldname),Long.class);
                         m.invoke(o,Long.parseLong(cellvalue));
                     }else if(Double.class.equals(fieldtype)){
                         Method m = modelclz.getMethod("set"+StrUtil.firstCharToUpperCase(fieldname),Double.class);
                         m.invoke(o,Double.parseDouble(cellvalue));
                     }else if (Date.class.equals(fieldtype)){
                         Method m = modelclz.getMethod("set"+StrUtil.firstCharToUpperCase(fieldname),Date.class);
                         try {
                             if(cellvalue.length()==8){
                                 m.invoke(o,DateTools.getDate(cellvalue,"yyyyMMdd"));
                             }else{
                                 m.invoke(o,DateTools.getDate(cellvalue,"yyyyMMddHHmmss"));
                             }
                         } catch (ParseException e) {
                             writeLog("第【"+(i+1)+"】行【"+(j+1)+"】列日期格式错误");
                             canadd = false;
                         }
                     }else{
                         Method m = modelclz.getMethod("set"+StrUtil.firstCharToUpperCase(fieldname),String.class);
                         m.invoke(o,cellvalue);
                     }

                } catch (Exception e) {
                    writeLog("第【"+(i+1)+"】行【"+(j+1)+"】列的数据处理出错！错误详情："+e.getMessage());
                    canadd = false;
                }

            }
            if(canadd)
                datalist.add(o);
        }
        return datalist;
    }

    private boolean checkExcel() throws IOException {
        logfile = new File(SpringUtil.getBean(YmlConfig.class).getFilesavepath()+new Date().getTime()+".txt");
        logfile.createNewFile();
        fw = new FileWriter(logfile);
        boolean success = true;
        writeLog("检查文件格式是否正确！");
        if(excelfile.getName().toLowerCase().endsWith("xls")){
            this.workbook = new HSSFWorkbook(FileUtils.openInputStream(excelfile));
        }else if(excelfile.getName().toLowerCase().endsWith("xlsx")){
            this.workbook = new XSSFWorkbook(FileUtils.openInputStream(excelfile));
        }else{
            success = false;
            writeLog("文档格式不正确!");
        }
        writeLog("检查是否有工作表！");
        if (null!=this.workbook && this.workbook.getNumberOfSheets()<0){
            success = false;
            writeLog("文档中没有工作表!");
        }
        return success;
    }

    private boolean checkSheet(Sheet sheet)  {
        boolean success = true;
        if(sheet.getLastRowNum()<3){
            success = false;
            writeLog("检测到文档没有数据，请检查后重试!");
        }
        Class modelclz = SysCache.model_map.get(StrUtil.getCamelClassname(sheet.getSheetName()));
        if(null==modelclz){
            writeLog("sheet对应的数据库中表不存在，请核对后重试!");
            success = false;
        }
        try {
            model = modelclz.newInstance();
        } catch (InstantiationException e) {
            writeLog("生成表对象失败，请联系管理员!");
            success = false;
        } catch (IllegalAccessException e) {
            writeLog("生成表对象失败，请联系管理员!");
            success = false;

        }
        return success;
    }

    private  String getValue(Cell cell) {
        String returnvalue ="";
        if(null!=cell) {
            if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                returnvalue =  String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                returnvalue = String.valueOf(cell.getNumericCellValue());
            } else if(cell.getCellTypeEnum() == CellType.FORMULA){
                returnvalue = cell.getCellFormula();
            } else if(cell.getCellTypeEnum() == CellType.ERROR){
                returnvalue = String.valueOf(cell.getErrorCellValue());
            } else{
                returnvalue =  cell.getStringCellValue().trim();
            }
        }
        return returnvalue;
    }

    private String tranValue(String value,String cmmond){
        if(StrUtil.isBlank(cmmond))
            return value;
        String tranvalue = value;
        //1.处理枚举项
        if(cmmond.startsWith("E") && cmmond.split("-").length==3) {
            String []eumstr = cmmond.split("-");
            tranvalue = EumUtil.getEumNamevalue(eumstr[1], eumstr[2],value);
        }
        //2.处理器处理
        if(cmmond.startsWith("T") && cmmond.split("-").length==2) {
            String []eumstr = cmmond.split("-");
            Method[] methods = TransFormUtil.class.getMethods();
            for(Method m : methods) {
                ListTransf tstf = m.getAnnotation(ListTransf.class);
                if(null!=tstf && eumstr[1].equalsIgnoreCase(tstf.value())) {
                    try {
                        tranvalue = (String) m.invoke(SpringUtil.getBean(TransFormUtil.class), value);
                    } catch (IllegalAccessException e) {
                        writeLog("数据转换错误，请联系管理员核对模板!");
                    } catch (InvocationTargetException e) {
                        writeLog("数据转换错误，请联系管理员核对模板!");
                    }
                    break;
                }
            }
        }
        return tranvalue;
    }

    private  void writeLog(String msg){
        log.info(msg);
        try {
            fw.write(msg+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
