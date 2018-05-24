package com.sgnbs.common.syscache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sgnbs.common.exception.AnnoException;
import com.sgnbs.ms.annotation.DelDo;
import com.sgnbs.ms.annotation.ListDo;
import com.sgnbs.ms.annotation.SaveDo;
import com.sgnbs.ms.annotation.Table;
import com.sgnbs.ms.annotation.ToDetail;
import com.sgnbs.ms.annotation.ToSave;
import com.sgnbs.ms.dao.FieldcheckDAO;
import com.sgnbs.ms.model.Fieldcheck;

/**
 * 系统缓存统一管理处
 * @author tianj
 *
 */
@Component
@Order(value=1)
@Slf4j
public class SysCache implements CommandLineRunner{
	
	@Resource
	private  FieldcheckDAO fieldcheckDAO;

	@Override
	@SuppressWarnings("unchecked")
	public void run(String... args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, AnnoException{
		//加载枚举项放入缓存
		log.info("加载系统枚举项");
		eumlist.addAll(fieldcheckDAO.findAll());
		//读取个性化处理方法放到缓存
		Field field = ClassLoader.class.getDeclaredField("classes");  
        field.setAccessible(true); 
        Vector<Class<?>> classes = new Vector<Class<?>>();
        classes.addAll( (Vector<Class<?>>) field.get(this.getClass().getClassLoader()));
        for(Class<?> clz : classes) {
        	if(!clz.getName().startsWith("com.sgnbs")){
        		continue;
			}
        	if(null!=clz.getAnnotation(Table.class)) {
        		model_map.put(clz.getSimpleName(), clz);
        	}
        	if(null!=clz.getAnnotation(Mapper.class)) {
        		dao_map.put(clz.getSimpleName().replace("DAO", ""), clz);
        	}
        	Method[] methods = clz.getMethods();
        	for(Method method : methods) {
        		ListDo listdo = method.getAnnotation(ListDo.class);
        		SaveDo saveDo = method.getAnnotation(SaveDo.class);
        		ToDetail toDetail = method.getAnnotation(ToDetail.class);
        		ToSave toSave = method.getAnnotation(ToSave.class);
        		DelDo delDo = method.getAnnotation(DelDo.class);
				addToMap(clz, method, null != listdo, listdo_map, null != listdo?listdo.value().getSimpleName():"");
				addToMap(clz, method, null!=saveDo, savedo_map, null != saveDo?saveDo.value().getSimpleName():"");
				addToMap(clz, method, null!=toDetail, todetail_map, null != toDetail?toDetail.value().getSimpleName():"");
				addToMap(clz, method, null!=delDo, todetail_map, null != delDo?delDo.value().getSimpleName():"");
				addToMap(clz, method, null!=toSave, tosave_map, null != toSave?toSave.value().getSimpleName():"");
			}
        }

		log.info("系统枚举项加载成功");
		
	}

	/**
	 * put the table->actions relationship into cache
	 * @param clz
	 * @param method
	 * @param b
	 * @param cache_map
	 * @param simpleName
	 * @throws AnnoException
	 */
	private void addToMap(Class<?> clz, Method method, boolean b, Map<String, ClassMethod> cache_map, String simpleName) throws AnnoException {
		if(b) {
			if(null!=clz.getAnnotation(Service.class)) {
				if(null!= cache_map.get(simpleName)) {
					throw new AnnoException();
				}
				cache_map.put(simpleName, new ClassMethod(clz, method));
			}else {
				throw new AnnoException();
			}
		}
	}


	/**
	 * 枚举项存放list
	 */
	public static final List<Fieldcheck> eumlist = new ArrayList<Fieldcheck>();
	/**
	 * key-驼峰实体类名.value-对应modelclass
	 */
	public static final Map<String,Class<?>> model_map = new HashMap<>();
	/**
	 * key-驼峰实体类名.value-对应daoclass
	 */
	public static final Map<String,Class<?>> dao_map = new HashMap<String,Class<?>>();
	/**
	 * key-驼峰实体类名.value-对应ClassMethod
	 */
	public static final Map<String,ClassMethod> listdo_map = new HashMap<String,ClassMethod>();
	/**
	 * key-驼峰实体类名.value-对应ClassMethod
	 */
	public static final Map<String,ClassMethod> savedo_map = new HashMap<String,ClassMethod>();
	/**
	 * key-驼峰实体类名.value-对应ClassMethod
	 */
	public static final Map<String,ClassMethod> deldo_map = new HashMap<String,ClassMethod>();

	/**
	 * key-驼峰实体类名.value-对应ClassMethod
	 */
	public static final Map<String,ClassMethod> todetail_map = new HashMap<String,ClassMethod>();
	/**
	 * key-驼峰实体类名.value-对应ClassMethod
	 */
	public static final Map<String,ClassMethod> tosave_map = new HashMap<String,ClassMethod>();


	
	 public class ClassMethod {
		 private Class<?> clz;
		 private Method method;
		public Class<?> getClz() {
			return clz;
		}
		public void setClz(Class<?> clz) {
			this.clz = clz;
		}
		public Method getMethod() {
			return method;
		}
		public void setMethod(Method method) {
			this.method = method;
		}
		public ClassMethod(Class<?> clz, Method method) {
			super();
			this.clz = clz;
			this.method = method;
		}
		 
	}
}
