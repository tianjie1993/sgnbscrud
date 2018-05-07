package com.sgnbs.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.syscache.SysCache;
import com.sgnbs.ms.model.Fieldcheck;

public class EumUtil {
	
	public static List<KeyValue> getByTFname(String tablename,String fieldname){
		List<KeyValue> kvs = new ArrayList<KeyValue>();
		for(Fieldcheck fc : SysCache.eumlist) {
			if(fc.getTablename().equalsIgnoreCase(tablename) && fc.getFieldname().equalsIgnoreCase(fieldname)) {
				String keyvaluestr = fc.getEums();
				if(StrUtil.isBlank(keyvaluestr)) {
					continue;
				}
				String  []keyvaluesz = keyvaluestr.split(",");
				for(String kv : keyvaluesz) {
					if(StrUtil.isBlank(kv)) {
						continue;
					}
					String []kvsz = kv.split("=");
					if(kvsz.length!=2) {
						continue;
					}
					if(Constants.DEL_STATUS==Integer.parseInt(kvsz[0]) && "status".endsWith(kvsz[1])) {
						continue;
					}
					kvs.add(new EumUtil().new KeyValue(Integer.parseInt(kvsz[0]),kvsz[1]));
				}
				break;
			}
		}
		return kvs;
	}
	
	public static String getEumValuename(String tablename,String fieldname,String keystr){
		List<KeyValue> kvs = getByTFname(tablename, fieldname);
		for(KeyValue kv : kvs) {
			if(String.valueOf(kv.getKey()).equals(keystr)) {
				return kv.getValue();
			}
		}
		return null;
	}

	
	
	public class KeyValue {
		
		private Integer key;
		
		private String value;
		
		KeyValue(Integer key,String value){
			this.key=key;
			this.value=value;
		}

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
}
