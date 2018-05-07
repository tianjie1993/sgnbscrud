package com.sgnbs.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgnbs.ms.annotation.ListTransf;

public class ListDataUtil {
	
	/**
	 * 对通用查询列表数据进行处理
	 * @param list
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void  transformData(List<Map<String,String>> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Map<String,String>> newlist = new ArrayList<Map<String,String>>();
		for(Map<String,String> model : list) {
			Map<String,String> newmodel = new HashMap<String,String>();
			for(String key : model.keySet()) {
				String newkey = key;
				String newvalue = String.valueOf(model.get(key));
				//1.处理枚举项
				if(key.startsWith("E") && key.split("-").length==3) {
					String []eumstr = key.split("-");
					newvalue = EumUtil.getEumValuename(eumstr[1], eumstr[2],newvalue);
					newkey =  eumstr[2];
				}
				//2.处理器处理
				if(key.startsWith("T") && key.split("-").length==3) {
					String []eumstr = key.split("-");
					Method [] methods = TransFormUtil.class.getMethods();
					for(Method m : methods) {
						ListTransf tstf = m.getAnnotation(ListTransf.class);
						if(null!=tstf && eumstr[1].equalsIgnoreCase(tstf.value())) {
							newvalue = (String) m.invoke(SpringUtil.getBean(TransFormUtil.class), newvalue);
							break;
						}
					}
					newkey =  eumstr[2];
				}
				newkey = StrUtil.getCamelCasekey(newkey);
				newmodel.put(newkey, newvalue);
			}
			newlist.add(newmodel);
		}
		list.clear();
		list.addAll(newlist);
	}

}
