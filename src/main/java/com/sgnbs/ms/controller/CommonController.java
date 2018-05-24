package com.sgnbs.ms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sgnbs.common.constants.AjaxResult;
import com.sgnbs.common.excel.ExcelDataImportUtil;
import com.sgnbs.common.utils.SpringUtil;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.common.utils.UpDownUtil;
import com.sgnbs.common.utils.YmlConfig;
import com.sgnbs.ms.dao.FastListDAO;
import com.sgnbs.ms.dao.SysSelectkyDAO;
import com.sgnbs.ms.model.Attachment;
import com.sgnbs.ms.model.SysSelectky;
import com.sgnbs.ms.service.intf.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping({"/common"})
public class CommonController extends BaseController{


	@Autowired
	private SysSelectkyDAO sysSelectkyDAO;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private YmlConfig  ymlConfig;
	
	@Autowired
	private FastListDAO fastListDAO;


	/**
	 * 在多表链接查询时针对外键选择keyvalue值
	 * tablename 需要查询的表
	 * fields 列表需要查询的字段
	 * fieldnames 需要查询的字段名称
	 * searchinfo查询输入框名称格式：name:名称,code:编码
	 * key 两个值，逗号隔开,前面是原表中的key，后面是当前页面赋值的字段
	 * value 两个值，逗号隔开。前面是原表中的字段。后面是当前页面赋值的字段
	 * @param request
	 * @return
	 */
	@GetMapping("/tosysselectky")
	public ModelAndView toSysSelectky(SysSelectky select,HttpServletRequest request){
		select = sysSelectkyDAO.selectByPrimaryKey(select.getId());
		ModelAndView mv = new ModelAndView("/common/selectkeyvalue");
		String key = select.getKeyto();
		String fields = select.getFields();
		String fieldnames = select.getFieldnames();
		String value = select.getValueto();
		String searchinfo= select.getSearchinfo();
		String tablename = select.getQsql();
		List<Map<String,String>> searchinfolist = new ArrayList<Map<String,String>>();
		if(StrUtil.notBlank(searchinfo)){
			String []searchinfos = searchinfo.split(",");
			for(String search : searchinfos){
				Map<String,String> searchmap= new HashMap<String,String>();
				String []temp = search.split(":");
				searchmap.put("searchfield", temp[0]);
				searchmap.put("searchname",temp[1]);
				searchinfolist.add(searchmap);
			}
		}
		mv.addObject("tablename", tablename);
		mv.addObject("searchlist", searchinfolist);
		mv.addObject("key", key);
		mv.addObject("value", value);
		mv.addObject("fields",fields);
		mv.addObject("fieldnames",fieldnames);
		return  mv;
	}
	
	@GetMapping("/tosysselectkyd")
	public ModelAndView toSysSelectkyD(SysSelectky select,HttpServletRequest request){
		select = sysSelectkyDAO.selectByPrimaryKey(select.getId());
		ModelAndView mv = new ModelAndView("/common/selectkeyvalued");
		String key = select.getKeyto();
		String fields = select.getFields();
		String fieldnames = select.getFieldnames();
		String value = select.getValueto();
		String searchinfo= select.getSearchinfo();
		String tablename = select.getQsql();
		List<Map<String,String>> searchinfolist = new ArrayList<Map<String,String>>();
		if(StrUtil.notBlank(searchinfo)){
			String []searchinfos = searchinfo.split(",");
			for(String search : searchinfos){
				Map<String,String> searchmap= new HashMap<String,String>();
				String []temp = search.split(":");
				searchmap.put("searchfield", temp[0]);
				searchmap.put("searchname",temp[1]);
				searchinfolist.add(searchmap);
			}
		}
		mv.addObject("tablename", tablename);
		mv.addObject("searchlist", searchinfolist);
		mv.addObject("key", key);
		mv.addObject("value", value);
		mv.addObject("fields",fields);
		mv.addObject("fieldnames",fieldnames);
		return  mv;
	}
	@GetMapping("/tosysselectkydd")
	public ModelAndView toSysSelectkyDD(SysSelectky select,HttpServletRequest request){
		select = sysSelectkyDAO.selectByPrimaryKey(select.getId());
		ModelAndView mv = new ModelAndView("/common/selectkeyvaluedd");
		String key = select.getKeyto();
		String fields = select.getFields();
		String fieldnames = select.getFieldnames();
		String value = select.getValueto();
		String searchinfo= select.getSearchinfo();
		String tablename = select.getQsql();
		List<Map<String,String>> searchinfolist = new ArrayList<Map<String,String>>();
		if(StrUtil.notBlank(searchinfo)){
			String []searchinfos = searchinfo.split(",");
			for(String search : searchinfos){
				Map<String,String> searchmap= new HashMap<String,String>();
				String []temp = search.split(":");
				searchmap.put("searchfield", temp[0]);
				searchmap.put("searchname",temp[1]);
				searchinfolist.add(searchmap);
			}
		}
		mv.addObject("tablename", tablename);
		mv.addObject("searchlist", searchinfolist);
		mv.addObject("key", key);
		mv.addObject("value", value);
		mv.addObject("fields",fields);
		mv.addObject("fieldnames",fieldnames);
		return  mv;
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/sysselectky")
	@ResponseBody
	public AjaxResult SysSelectky(@RequestParam Map<String,String>  map) throws Throwable{
		int pageno = Integer.parseInt(map.get("page"));
		int pagesize = Integer.parseInt(map.get("pagesize"));
		PageHelper.startPage(pageno, pagesize);
		String tablename = map.get("tablename");
		map.remove("page");
		map.remove("pagesize");
		map.remove("tablename");
		Method queryMethod = fastListDAO.getClass().getMethod(tablename, Map.class);
		List<Map<String,String>> list = (List) queryMethod.invoke(fastListDAO, map);
		return AjaxResult.success(new PageInfo<>(list));
	}
	
	@RequestMapping("/main")
	public String toMain() {
		return "main";
	}
	
	@RequestMapping(value = "fileupload")
	@ResponseBody
	public AjaxResult fileupload(HttpServletRequest request) {
		MultipartHttpServletRequest mulreq = (MultipartHttpServletRequest) request;
		List<MultipartFile> multifiles = mulreq.getFiles("file");
		List<Attachment> attas = attachmentService.UploadFiles(multifiles);
		return AjaxResult.success(attas);
	}
	
	@RequestMapping(value = "filedown")
	public ModelAndView fileDown(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String id=  request.getParameter("id");
		Attachment attach = attachmentService.findById(Integer.parseInt(id));
		String temppath = ymlConfig.getFilesavepath()+attach.getFilename();
		File file = new File(temppath);
		if(!file.exists()) {
			throw new IOException("下载文件不存在！");
		}
		UpDownUtil.downloadFileOrginname(request, response, temppath,attach.getOrginname());
		return null;
		
	}

}
