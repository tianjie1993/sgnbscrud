package com.sgnbs.ms.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sgnbs.common.constants.AjaxResult;
import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.constants.LayuiAjaxData;
import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.common.utils.*;
import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.dao.ListDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 增删改查通用处理C
 * @author tianj
 *
 */
@Controller
@RequestMapping("/crud")
@Slf4j
public class CrudController extends BaseController{
	
	@Autowired
	private ListDAO listDAO;
	/**
	 * 通用到列表页面
	 * @param classname
	 * @return
	 */
	@GetMapping("/tolist/{classname}")
	public ModelAndView toList(@PathVariable("classname") String classname){
		classname = classname.replace("_", "").toLowerCase();
		ModelAndView mv = new ModelAndView("/"+classname+"/list");
		return mv;
	}
	
	/**
	 * 通用到新增修改页面
	 * @param classname
	 * @return
	 */
	@GetMapping("/tosave/{classname}")
	public ModelAndView toSave(@PathVariable("classname") String classname,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/"+classname.replace("_", "").toLowerCase()+"/save");
		String modelname = StrUtil.getCamelClassname(classname);
		Class<?> model = SysCache.model_map.get(modelname);
		Object model_ins = model.newInstance();
		String id = request.getParameter("id");
		if(StrUtil.notBlank(id)) {
			model_ins = CrudUtil.getEntity(model, id);
		}else {
			CrudUtil.setSaveNew();
		}
		MyReflectUtil.addAnnoData(model_ins);
		//2.添加个性化返回内容
		SysCache.ClassMethod clzm_detail = SysCache.tosave_map.get(modelname);
		if(null!=clzm_detail) {
			clzm_detail.getMethod().invoke(SpringUtil.getBean(clzm_detail.getClz()),mv, model_ins);
		}
		mv.addObject("entity", model_ins);
		CrudUtil.removeIsNew();
		return mv;
	}
	
	/**
	 * 通用到详情页面
	 * @param classname
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@GetMapping("/todetail/{classname}")
	public ModelAndView toDetail(@PathVariable("classname") String classname,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView();
		String modelname = StrUtil.getCamelClassname(classname);
		Class<?> model = SysCache.model_map.get(modelname);
		Object model_ins = model.newInstance();
		String id = request.getParameter("id");
		if(StrUtil.notBlank(id)) {
			model_ins = CrudUtil.getEntity(model, id);
		}
		MyReflectUtil.addAnnoData(model_ins);
		//2.添加个性化返回内容
		SysCache.ClassMethod clzm_detail = SysCache.todetail_map.get(modelname);
		if(null!=clzm_detail) {
			clzm_detail.getMethod().invoke(SpringUtil.getBean(clzm_detail.getClz()),mv, model_ins);
		}
		mv.addObject("entity", model_ins);
		if(StrUtil.notBlank(CrudUtil.getViewName())) {
			mv.setViewName(CrudUtil.getViewName());
			CrudUtil.removeViewName();
		}else {
			mv.setViewName("/"+classname.replace("_", "").toLowerCase()+"/detail");
		}
		return mv;
	}
	
	/**
	 * ajax获取多个详情
	 * @param classname
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@GetMapping("/jsondetail/{classname}")
	@ResponseBody
	public AjaxResult getJsonDetail(@PathVariable("classname") String classname,HttpServletRequest request) throws Exception{
		String modelname = StrUtil.getCamelClassname(classname);
		String fields = request.getParameter("fields");
		Class<?> model = SysCache.model_map.get(modelname);
		String id = request.getParameter("id");
		Object list = null;
		if(StrUtil.notBlank(id)) {
			list = CrudUtil.getEntitys(model, id);
		}
		//1.反射添加常量值
		if(StrUtil.notBlank(fields)) {
			MyReflectUtil.addAnnoData(list,true,fields.split(","));
		}else {
			MyReflectUtil.addAnnoData(list);
		}
		return AjaxResult.success(list);
	}
	
	/**
	 * ajax获取多个详情
	 * @param classname
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@GetMapping("/singledetail/{classname}")
	@ResponseBody
	public AjaxResult getSingleDetail(@PathVariable("classname") String classname,HttpServletRequest request) throws Exception{
		String modelname = StrUtil.getCamelClassname(classname);
		String fields = request.getParameter("fields");
		Class<?> model = SysCache.model_map.get(modelname);
		String id = request.getParameter("id");
		Object o = null;
		if(StrUtil.notBlank(id)) {
			o = CrudUtil.getEntity(model, id);
		}
		//1.反射添加常量值
		if(StrUtil.notBlank(fields)) {
			MyReflectUtil.addAnnoData(o,true,fields.split(","));
		}else {
			MyReflectUtil.addAnnoData(o);
		}
		return AjaxResult.success(o);
	}

	/**
	 * 通用获取listdata接口
	 * @param classname
	 * @param map
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/list/{classname}")
	@ResponseBody
	public LayuiAjaxData getListData(@PathVariable("classname") String classname,@RequestParam Map<String,String>  map) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException{
		String fixedprams  = map.get("fixedprams");
		if(StrUtil.notBlank(map.get("page")) && StrUtil.notBlank(map.get("limit"))) {
			PageHelper.startPage(Integer.parseInt(map.get("page")), Integer.parseInt(map.get("limit")));
		}
		map.remove("page");
		map.remove("limit");
		map.remove("fixedprams");
		//处理固定参数
		if(StrUtil.notBlank(fixedprams)) {
			String []keyvalues = fixedprams.split(",");
			for(String kv : keyvalues) {
				String []kav = kv.split("=");
				if(kav.length==2) {
					map.put(kav[0], kav[1]);
				}
			}
		}
		//添加个性化变量
		String modelname = StrUtil.getCamelClassname(classname);
		SysCache.ClassMethod clzm = SysCache.listdo_map.get(modelname);
		if(null!=clzm) {
			clzm.getMethod().invoke(SpringUtil.getBean(clzm.getClz()), map);
		}
		String methodname = "find"+StrUtil.getCamelClassname(classname)+"List";
		Method querymethod = listDAO.getClass().getMethod(methodname, Map.class);
		Page page = (Page)querymethod.invoke(listDAO, map);
		List<Map<String,String>> list = page.getResult();
		ListDataUtil.transformData(list);
		return LayuiAjaxData.success(page.getResult(), page.getTotal());
	}
	
	
	
	/**
	 * 通用物理删除。接口地址/crud/physicald/#表名#
	 * 支持批量
	 * @param classname
	 * @param request
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@PostMapping("/physicald/{classname}")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public AjaxResult physicalDelete(@PathVariable("classname") String classname,HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NumberFormatException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String ids = request.getParameter("id");
		Class<?> model = SysCache.model_map.get(StrUtil.getCamelClassname(classname));;
  		SysCache.ClassMethod clzm = SysCache.deldo_map.get(StrUtil.getCamelClassname(classname));
  		if(null!=clzm) {
  			clzm.getMethod().invoke(SpringUtil.getBean(clzm.getClz()), request);
  		}
  		String msg = CrudUtil.getErrorMsg();
  		if(StrUtil.notBlank(msg)) {
  			CrudUtil.removeErrorMsg();
  		    return AjaxResult.error(msg);
  		}
  		CrudUtil.delEntitys(model, ids);
	    return AjaxResult.success();
	}
	
	/**
	 * 通用逻辑删除。接口地址/crud/logicald/#表名#
	 * 支持批量
	 * 会设置表status=99.没有属性跑出异常
	 * @param classname
	 * @param request
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@PostMapping("/logicald/{classname}")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public AjaxResult logicalDelete(@PathVariable("classname") String classname,HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NumberFormatException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String ids = request.getParameter("id");
		String [] idsz = ids.split(",");
		classname = StrUtil.getCamelClassname(classname);
		Class<?> model = SysCache.model_map.get(classname);
		Object o = model.newInstance();
		Field[] declaredFields = model.getDeclaredFields();
	    Method setStatusMethod = model.getMethod("setStatus", Integer.class);
	    Class<?> idtype = Integer.class;
	    String idste = "id";
        for (Field field : declaredFields) {
        	if(null != field.getAnnotation(ID.class)) {
        		idtype = field.getType();
        		idste = field.getName();
        		break;
        	}
        }
        SysCache.ClassMethod clzm = SysCache.deldo_map.get(classname);
  		if(null!=clzm) {
  			clzm.getMethod().invoke(SpringUtil.getBean(clzm.getClz()), request);
  		}
  		String msg = CrudUtil.getErrorMsg();
  		if(StrUtil.notBlank(msg)) {
  			CrudUtil.removeErrorMsg();
  		    return AjaxResult.error(CrudUtil.getErrorMsg());
  		}
        Method setIdMethod = model.getMethod("set"+StrUtil.firstUppercase(idste), idtype);
		Class<?> modeldao = SysCache.dao_map.get(classname);
		Object dao = SpringUtil.getBean(modeldao);
		Method updatestatus = modeldao.getMethod(Constants.UPDATEBYPRIMARYKEYSELECTIVE, model);
		for(String id : idsz) {
			if(idtype.equals(Integer.class)) {
				setIdMethod.invoke(o, Integer.parseInt(id));
			}else {
				setIdMethod.invoke(o, id);
			}
			setStatusMethod.invoke(o,Constants.DEL_STATUS);
			updatestatus.invoke(dao, o);
		}
	    return AjaxResult.success();
	}
	
	/**
	 * 数据保存通过入口
	 * @param classname
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/save/{classname}")
	@Transactional(rollbackFor=Exception.class)
	public ModelAndView saveEntity(@PathVariable("classname") String classname,@RequestParam Map<String,String>  map) throws Exception {
		ModelAndView mv = new ModelAndView();
		String modelname = StrUtil.getCamelClassname(classname);
		Class<?> model = SysCache.model_map.get(modelname);
		Object model_ins = model.newInstance();
		save(model_ins,map);
		//3.对实体类中的枚举项、文件进行填充。
		MyReflectUtil.addAnnoData(model_ins);
		//4.对应个性化主动反映的错误信息设置
		String errormsg = CrudUtil.getErrorMsg();
		if(StrUtil.notBlank(errormsg)) {
			returnModal(ERROR, errormsg, mv);
			CrudUtil.removeErrorMsg();
		}else {
			CrudUtil.updateEntity(model_ins);
			returnModal(SUCCESS, "操作成功！", mv);
		}
		mv.addObject("entity", model_ins);
		CrudUtil.removeIsNew();
		if(StrUtil.notBlank(CrudUtil.getViewName())) {
			mv.setViewName(CrudUtil.getViewName());
			CrudUtil.removeViewName();
		}else {
			mv.setViewName("/"+classname.replace("_", "").toLowerCase()+"/save");
		}
		return mv;
	}
	
	/**
	 * 数据保存通过入口
	 * @param classname
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/comsave/{classname}")
	@Transactional(rollbackFor=Exception.class)
	public ModelAndView comsaveEntity(@PathVariable("classname") String classname,@RequestParam Map<String,String>  map) throws Exception {
		ModelAndView mv = new ModelAndView();
		String modelname = StrUtil.getCamelClassname(classname);
		Class<?> model = SysCache.model_map.get(modelname);
		Object model_ins = model.newInstance();
		save(model_ins,map);
		String errormsg = CrudUtil.getErrorMsg();
		if(StrUtil.notBlank(errormsg)) {
			returnError(errormsg, mv);
			CrudUtil.removeErrorMsg();
		}else {
			CrudUtil.updateEntity(model_ins);
			returnSuccess("操作成功！",mv);
		}
		CrudUtil.removeIsNew();
		if(StrUtil.notBlank(CrudUtil.getViewName())) {
			mv.setViewName(CrudUtil.getViewName());
			CrudUtil.removeViewName();
		}else {
			mv.setViewName("/"+classname.replace("_", "").toLowerCase()+"/save");
		}
		return mv;
	}
	
	private void save(Object model_ins,Map<String,String> map)  throws Exception{
		String modelname = model_ins.getClass().getSimpleName();
		Class<?> model = model_ins.getClass();
		Set<String> keys = map.keySet();
		String idstr = "id";
		Class<?> idtype = Integer.class;
		boolean isnew = false;
		Field []fields = model.getDeclaredFields();
		for(Field f : fields) {
			ID id = f.getAnnotation(ID.class);
			if(null!=id) {
				idstr = f.getName();
				idtype = f.getType();
				break;
			}
		}
		for(String key : keys) {
			Field field = null;
			try {
				field = model.getDeclaredField(key);
			} catch (NoSuchFieldException e) {
			}
			if(null!=field) {
				Method  m = model.getMethod("set"+StrUtil.firstUppercase(key), field.getType());
				if(field.getType().equals(Integer.class) && StrUtil.notBlank(map.get(key))) {
					m.invoke(model_ins, Integer.parseInt(map.get(key)));
				}
				if(field.getType().equals(String.class)) {
					m.invoke(model_ins, map.get(key));
				}
				if(field.getType().equals(Date.class) && StrUtil.notBlank(map.get(key))) {
					String dateStr = map.get(key);
					Date date = null;
					if(dateStr.length()==DateTools.YYYYMMDD.length()) {
						date = DateTools.getDate(map.get(key), DateTools.YYYYMMDD);	
					}
					if(dateStr.length()==DateTools.YYYYMMDDHHMMSS.length()) {
						date = DateTools.getDate(map.get(key), DateTools.YYYYMMDDHHMMSS);	
					}
					m.invoke(model_ins, date);
				}
				if(field.getType().equals(Long.class) && StrUtil.notBlank(map.get(key))) {
					m.invoke(model_ins, Long.parseLong(map.get(key)));
				}
			}
			if(key.equals(idstr) && StrUtil.isBlank(map.get(key))) {
				isnew = true;
				CrudUtil.setSaveNew();
				if(idtype.equals(String.class)){
					Method m_setid = model.getMethod("set"+StrUtil.firstCharToUpperCase(idstr), String.class);
					m_setid.invoke(model_ins, StrUtil.getUUID());
				}
			}
		}
		if(idtype.equals(Integer.class)) {
			Method m_getid=  model.getMethod("get"+StrUtil.firstCharToUpperCase(idstr));
			if(null==m_getid.invoke(model_ins)) {
				isnew = true;
				CrudUtil.setSaveNew();
			}
		}
		if(isnew) {
			model_ins = CrudUtil.insertEntity(model_ins);
		}
		//1.保存表单时处理个性化需求
		SysCache.ClassMethod clzm = SysCache.savedo_map.get(modelname);
		if(null!=clzm) {
			clzm.getMethod().invoke(SpringUtil.getBean(clzm.getClz()), model_ins);
		}
		
	}
}
