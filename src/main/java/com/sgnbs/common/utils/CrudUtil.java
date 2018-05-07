package com.sgnbs.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.syscache.SysCache;

public class CrudUtil {
	
	private static final ThreadLocal<String> ERROR_MSG = new ThreadLocal<String>();
	
	private static final ThreadLocal<Boolean> ISNEW = new ThreadLocal<Boolean>();
	
	private static final ThreadLocal<String> VIEWNAME = new ThreadLocal<String>();


	public static void addErrorMsg(String msg) {
		ERROR_MSG.set(msg);
	}
	
	public static String getErrorMsg() {
		return ERROR_MSG.get();
	}
	
	public static void removeErrorMsg() {
		ERROR_MSG.remove();
	}
	
	public static Boolean isNew() {
		if(null==ISNEW.get()) {
			return false;
		}else {
			return  ISNEW.get();
		}
	}
	public static void removeIsNew() {
		ISNEW.remove();
	}
	
	public static void setSaveNew() {
		 ISNEW.set(true);
	}
	
	public static void setViewName(String viewname) {
		VIEWNAME.set(viewname);
	}
	
	public static String getViewName() {
		return VIEWNAME.get();
	}
	
	public static void removeViewName() {
		VIEWNAME.remove();
	}
	
	public static Object insertEntity(Object o) {
		try {
			Class<?> daoclz = SysCache.dao_map.get(o.getClass().getSimpleName());
			if(null!=daoclz) {
				Object dao = SpringUtil.getBean(daoclz);
				Method insertMethod = dao.getClass().getMethod(Constants.INSERTSELECTIVE, o.getClass());
				insertMethod.invoke(dao, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static Object updateEntity(Object o){
		try {
			Class<?> daoclz = SysCache.dao_map.get(o.getClass().getSimpleName());
			if(null!=daoclz) {
				Object dao = SpringUtil.getBean(daoclz);
				Method insertMethod = dao.getClass().getMethod(Constants.UPDATEBYPRIMARYKEYSELECTIVE, o.getClass());
				insertMethod.invoke(dao, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static void delEntitys(Class<?> clz,String ids){
		try {
			Class<?> daoclz = SysCache.dao_map.get(clz.getSimpleName());
			Class<?> idtype = MyReflectUtil.getIdType(clz);
			if(null!=daoclz) {
				Object dao = SpringUtil.getBean(daoclz);
				Method insertMethod = dao.getClass().getMethod(Constants.DELETEBYPRIMARYKEY, idtype);
				for(String id : ids.split(",")) {
					if(idtype==Integer.class) {
						insertMethod.invoke(dao, Integer.parseInt(id));
					}else {
						insertMethod.invoke(dao, id);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Object getEntity(Class<?> clz,Object id){
		return getEntity(clz,id,false);
	}

	
	public static Object getEntity(Class<?> clz,Object id,boolean addannodata){
		Object o = null;
		try {
			Class<?> daoclz = SysCache.dao_map.get(clz.getSimpleName());
			Class<?> idtype = MyReflectUtil.getIdType(clz);
			if(null!=daoclz) {
				Object dao = SpringUtil.getBean(daoclz);
				Method insertMethod = dao.getClass().getMethod(Constants.SELECTBYPRIMARYKEY, idtype);
				if(Integer.class==idtype) {
					o = insertMethod.invoke(dao, Integer.parseInt(String.valueOf(id)));
				}else {
					o = insertMethod.invoke(dao, String.valueOf(id));
				}
			}
			if(null!=o && addannodata) MyReflectUtil.addAnnoData(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return o;
	}
	public static List<Object> getEntitys(Class<?> clz,String ids){
		return getEntitys(clz,ids,false);
	}

	public static List<Object> getEntitys(Class<?> clz,String ids,boolean addannodata){
		List<Object> os = new ArrayList<Object>();
		if(StrUtil.isBlank(ids)) return os;
		try {
			Class<?> daoclz = SysCache.dao_map.get(clz.getSimpleName());
			Class<?> idtype = MyReflectUtil.getIdType(clz);
			for(String id : ids.split(",")) {
				Object model = null;
				if(null!=daoclz) {
					Object dao = SpringUtil.getBean(daoclz);
					Method insertMethod = dao.getClass().getMethod(Constants.SELECTBYPRIMARYKEY, idtype);
					if(Integer.class==idtype) {
						model =	insertMethod.invoke(dao, Integer.parseInt(String.valueOf(id)));
					}else {
						model = insertMethod.invoke(dao, String.valueOf(id));
					}
				}
				if(null!=model && addannodata) MyReflectUtil.addAnnoData(model);
				os.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return os;
	}
	
	/**
	 * 执行指定实体类中的dao方法
	 * @param clz model class
	 * @param methodname excute methodname
	 * @param objects parameters
	 * @return
	 */
	public static Object excuteDaoMethod(Class<?> clz,String methodname,Object ...objects) {
		Class<?> dao = SysCache.dao_map.get(clz.getSimpleName());
		if(null==dao) return null;
		try {
			if(null!=objects && objects.length>0) {
				Class<?> [] classtypes = new Class[objects.length];
				for(int i=0;i<objects.length;i++) {
					if(objects[i].getClass()==HashMap.class) {
						classtypes[i] = Map.class;
					}else {
						classtypes[i]=objects[i].getClass();
					}
				}
				Method excuteMethod = dao.getMethod(methodname, classtypes);
				return excuteMethod.invoke(SpringUtil.getBean(dao), objects);
			}else {
				Method excuteMethod = dao.getMethod(methodname);
				return excuteMethod.invoke(SpringUtil.getBean(dao));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行指定实体类中的dao方法
	 * @param clz model class
	 * @param methodname excute methodname
	 * @param objects parameters
	 * @return
	 */
	public static Object excuteDaoMethod(Class<?> clz,String methodname) {
		return excuteDaoMethod(clz,methodname,new Object[] {}) ;
	}
}
