package com.hikvision.icoms.config.common;

import java.util.Vector;

public enum DbType {
	
//	oracle("oracle"),
//	mysql("mysql"),
	postgresql("postgresql");
	
	private String code;
	
	private DbType(String code){
		this.code = code;
	}
	
	/**
	 * 获取DbType的数组
	 * 
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月29日下午4:56:37
	 * @since  5.2.0
	 */
	public static Vector<String> getDbTypes(){
		Vector<String> v = new Vector<String>();
		for(DbType type : DbType.values()){
			v.add(type.getCode());
		}
		return v;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}
