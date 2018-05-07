package com.sgnbs.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.ms.annotation.RltsField;

/**
 * 通过对象里的id 获取目标对象里的名称
 * @author tianj
 *
 */
public class GetNamesUtil {
	
	/**
	 * 
	 * @param o
	 * @param idsfieldname 
	 * @param namesfield
	 * @param namsContain
	 * @return
	 * @throws Exception
	 */
	public static void setNames(Object o,String idsfieldname,String namesfield,String namsContain){
		try {
			Field field = o.getClass().getDeclaredField(idsfieldname);
			RltsField anno = field.getAnnotation(RltsField.class);
			String names = "";
			if(null!=anno) {
				Method getidsMethod = o.getClass().getMethod("get"+StrUtil.firstCharToUpperCase(idsfieldname));
				Object ids =  getidsMethod.invoke(o);
				if(StrUtil.notBlank(String.valueOf(ids))) {
					for(String id : String.valueOf(ids).split(",")) {
						Class<?> mbclass = anno.value();
						Class<?> idtype = MyReflectUtil.getIdType(mbclass);
						Class<?> mbdao = SysCache.dao_map.get(mbclass.getSimpleName());
						Object dao = SpringUtil.getBean(mbdao);
						if(Integer.class==idtype) {
							Method querymethod = mbdao.getMethod(Constants.SELECTBYPRIMARYKEY, Integer.class);
							Object model = querymethod.invoke(dao, Integer.parseInt(id));
							Method getnamemethod = model.getClass().getMethod("get"+StrUtil.firstCharToUpperCase(namesfield));
							Object name = getnamemethod.invoke(model);
							names +=name+",";
						}else {
							Method querymethod = mbdao.getMethod(Constants.SELECTBYPRIMARYKEY, String.class);
							Object model = querymethod.invoke(dao, id);
							Method getnamemethod = model.getClass().getMethod("get"+StrUtil.firstCharToUpperCase(namesfield));
							Object name = getnamemethod.invoke(model);
							names +=name+",";
						}
					}
				}
				if(names.endsWith(",")) {
					names = names.substring(0,names.length()-1);
				}
				Method setnames = o.getClass().getMethod("set"+StrUtil.firstCharToUpperCase(namsContain), String.class);
				setnames.invoke(o, names);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
