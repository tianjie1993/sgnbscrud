package com.sgnbs.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.common.utils.EumUtil.KeyValue;
import com.sgnbs.ms.annotation.ID;
import com.sgnbs.ms.annotation.IsEum;
import com.sgnbs.ms.annotation.RltsField;
import com.sgnbs.ms.annotation.Table;

public class MyReflectUtil {
	
	public static  void  addAnnoData(Object o){
		if(null==o)  return;
		try {
			if(o instanceof Collection) {
				Collection<?> os = (Collection<?>) o;
				for(Object temp : os) {
					commAddAnnoData(temp,temp.getClass().getDeclaredFields(),true);
				}
			}else {
				commAddAnnoData(o,o.getClass().getDeclaredFields(),true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static  void  addAnnoData(Object o,String ...fieldnames) {
		if(null==o)  return;
		try {
			if(o instanceof Collection) {
				Collection<?> os = (Collection<?>) o;
				for(Object temp : os) {
					Class<?> model = temp.getClass();
					Field[] fields = new Field[fieldnames.length];
					for(int i=0;i<fieldnames.length;i++) {
						fields[i] = model.getDeclaredField(fieldnames[i]);
					}
					commAddAnnoData(temp,fields,false);
				}
			}else {
				Class<?> model = o.getClass();
				Field[] fields = new Field[fieldnames.length];
				for(int i=0;i<fieldnames.length;i++) {
					fields[i] = model.getDeclaredField(fieldnames[i]);
				}
				commAddAnnoData(o,fields,false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param o
	 * @param deepin 若递归下一级model rlst 多过两个 。不建议使用 deepin = true; 会造成很大额外开销
	 * @param fieldnames
	 */
	public static  void  addAnnoData(Object o,boolean deepin,String ...fieldnames) {
		if(null==o)  return;
		try {
			if(o instanceof Collection) {
				Collection<?> os = (Collection<?>) o;
				for(Object temp : os) {
					Class<?> model = temp.getClass();
					Field[] fields = new Field[fieldnames.length];
					for(int i=0;i<fieldnames.length;i++) {
						fields[i] = model.getDeclaredField(fieldnames[i]);
					}
					commAddAnnoData(temp,fields,deepin);				}
			}else {
				Class<?> model = o.getClass();
				Field[] fields = new Field[fieldnames.length];
				for(int i=0;i<fieldnames.length;i++) {
					fields[i] = model.getDeclaredField(fieldnames[i]);
				}
				commAddAnnoData(o,fields,deepin);			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static  void  addAnnoData(Object o,boolean deepin){
		if(null==o)  return;
		try {
			if(o instanceof Collection) {
				Collection<?> os = (Collection<?>) o;
				for(Object temp : os) {
					commAddAnnoData(temp,temp.getClass().getDeclaredFields(),deepin);
				}
			}else {
				commAddAnnoData(o,o.getClass().getDeclaredFields(),deepin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void  commAddAnnoData(Object o,Field []fields,boolean deepin) throws Exception{
		Class<?> model = o.getClass();
		for(Field field : fields) {
			IsEum eum = field.getAnnotation(IsEum.class);
			if(null!=eum) {
				String listkvname = eum.value();
				List<KeyValue> kys = EumUtil.getByTFname(o.getClass().getAnnotation(Table.class).value(), StrUtil.getKeyByCamel(field.getName()));
				Method kvmethod = model.getMethod("set"+StrUtil.firstCharToUpperCase(listkvname), List.class);
				kvmethod.invoke(o, kys);
			}
			
			RltsField rltsField = field.getAnnotation(RltsField.class);
			if(null!=rltsField) {
				Method getidmethod = model.getMethod("get"+StrUtil.firstCharToUpperCase(field.getName()));
				Class<?> idtypeclass = getIdType(rltsField.value());
				Class<?> modeldao = SysCache.dao_map.get(rltsField.value().getSimpleName());
				Object dao = SpringUtil.getBean(modeldao);
				boolean isotm = rltsField.otm();
				String ids = String.valueOf(getidmethod.invoke(o));
				if(isotm) {
					List rltsmodels = new ArrayList();
					if(StrUtil.notBlank(ids)) {
						for(String id : ids.split(",")) {
							if(idtypeclass.equals(Integer.class)) {
								Method m = modeldao.getMethod(Constants.SELECTBYPRIMARYKEY, Integer.class);
								rltsmodels.add(m.invoke(dao, Integer.parseInt(id)));
							}else {
								Method m = modeldao.getMethod(Constants.SELECTBYPRIMARYKEY, String.class);
								rltsmodels.add(m.invoke(dao, id));
							}
						}
					}
					if(deepin) {
						for(Object temp : rltsmodels) {
							addAnnoData(temp);
						}
					}
					Method savemethod = model.getMethod("set"+StrUtil.firstCharToUpperCase(rltsField.result_container()), List.class);
					savemethod.invoke(o, rltsmodels);
				}else {
					Object modelvalue = new Object();
					Method savemethod = model.getMethod("set"+StrUtil.firstCharToUpperCase(rltsField.result_container()), rltsField.value());
					if(StrUtil.notBlank(ids)) {
						if(idtypeclass.equals(Integer.class)) {
							Method m = modeldao.getMethod(Constants.SELECTBYPRIMARYKEY, Integer.class);
							modelvalue = m.invoke(dao, Integer.parseInt(ids));
						}else {
							Method m = modeldao.getMethod(Constants.SELECTBYPRIMARYKEY, String.class);
							modelvalue = m.invoke(dao, ids);
						}
						if(deepin) {
							addAnnoData(modelvalue);
						}
						savemethod.invoke(o, modelvalue);
					}
				}
			}
		}
	}
	
	public static Class<?> getIdType(Object o){
		Field[] declaredFields = null;
		if(o instanceof Class) {
			Class<?> clz = (Class<?>)o;
			declaredFields = clz.getDeclaredFields();
		}else {
			declaredFields = o.getClass().getDeclaredFields();
		}
        for (Field field : declaredFields) {
        	if(null != field.getAnnotation(ID.class)) {
        		 return  field.getType();
        	}
        }
        return null;
	}

	public static String getIdName(Object o){
		Field[] declaredFields = null;
		if(o instanceof Class) {
			Class<?> clz = (Class<?>)o;
			declaredFields = clz.getDeclaredFields();
		}else {
			declaredFields = o.getClass().getDeclaredFields();
		}
		for (Field field : declaredFields) {
			if(null != field.getAnnotation(ID.class)) {
				return  field.getName();
			}
		}
		return null;
	}

}
