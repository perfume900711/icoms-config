package com.hikvision.icoms.config.common;

/**
 * 点击按钮操作
 * 
 * @author     jiangyue3
 * @date       2017年1月4日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2017年1月4日
 */
public enum ActionBehavior {
	
	INITDB("initDB"),
	TESTCONNECTION("testConnection"),
	SAVE("save"),
	EXIT("exit");
	
	private String code;
	
	private ActionBehavior(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
