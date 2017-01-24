package com.hikvision.icoms.config.common;

import java.util.HashSet;
import java.util.Set;


/**
 * 对接平台
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public enum DockVersion {
	
	IVMS_625("ivms6.2.5","ivms6.2.5"),
	
	IVMS_636("ivms6.3.6","ivms6.3.6"),
	
	IVMS_641("ivms6.4.1","ivms6.4.1"),
	
	GONGAN("gongan","gongan"),
	
	LOUYU("louyu","louyu"),
	
	JIAOTONG("jiaotong","jiaotong");
	
	/**
	 * 公安版本集
	 */
	public static final Set<DockVersion> GONGAN_SET = new HashSet<DockVersion>() {
		private static final long serialVersionUID = 3400016383884722761L;
		{
			add(GONGAN);
		}
	};
	
	/**
	 * 6.x版本集
	 */
	public static final Set<DockVersion> IVMS6_SET = new HashSet<DockVersion>() {
		private static final long serialVersionUID = 3400016383884722761L;
		{
			add(IVMS_625);
			add(IVMS_636);
			add(IVMS_641);
		}
	};
	
	/**
	 * 楼宇版本集
	 */
	public static final Set<DockVersion> LOUYU_SET = new HashSet<DockVersion>() {
		private static final long serialVersionUID = 3400016383884722761L;
		{
			add(LOUYU);
		}
	};
	
	private String code;
	private String name;
	
	private DockVersion(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 根据编码获取名称
	 * 
	 * @param code
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:12:34
	 * @since  5.2.0
	 */
	public static String getName(String code) {
		// 验证参数是否合法
		if (code == null || code.trim().length() < 1) {
			return "";
		}
		for (DockVersion type : DockVersion.values()) {
			if (code.equals(type.getCode())) {
				return type.name;
			}
		}
		return null;
	}
	
	/**
	 * 根据编码获取枚举实例
	 * 
	 * @param code
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:13:59
	 * @since  5.2.0
	 */
	public static DockVersion toEnumByCode(String code) {
		// 验证参数是否合法
		if (code == null || code.trim().length() < 1) {
			return null;
		}
		for (DockVersion type : DockVersion.values()) {
			if (code.equals(type.code)) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 根据资源名称获取编码
	 * 
	 * @param name
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:14:09
	 * @since  5.2.0
	 */
	public static String getCode(String name) {
		// 验证参数是否合法
		if (name == null || name.trim().length() < 1) {
			return "";
		}
		for (DockVersion type : DockVersion.values()) {
			if (name.equals(type.getName())) {
				return type.code;
			}
		}
		return null;
	}
	
	/**
	 * 根据名称获取枚举实例
	 * 
	 * @param name
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:14:45
	 * @since  5.2.0
	 */
	public static DockVersion toEnumByName(String name) {
		// 验证参数是否合法
		if (name == null || name.trim().length() < 1) {
			return null;
		}
		for (DockVersion type : DockVersion.values()) {
			if (name.equals(type.getName())) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 判断是否公安版本。
	 * 
	 * @param dv 版本枚举
	 * @return true-是；false-否
	 * @author zhiqianye
	 * @date  2016年1月26日下午7:59:31
	 * @since  5.2.1
	 */
	public boolean isGonganVersion() {
		if (GONGAN_SET.contains(this)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否6.x版本。
	 * 
	 * @param dv 版本枚举
	 * @return true-是；false-否
	 * @author zhiqianye
	 * @date  2016年1月26日下午7:59:31
	 * @since  5.2.1
	 */
	public boolean isIvms6Version() {
		if (IVMS6_SET.contains(this)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否楼宇版本。
	 * 
	 * @param dv 版本枚举
	 * @return true-是；false-否
	 * @author zhiqianye
	 * @date  2016年1月26日下午7:59:31
	 * @since  5.2.1
	 */
	public  boolean isLouyuVersion() {
		if (LOUYU_SET.contains(this)) {
			return true;
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
