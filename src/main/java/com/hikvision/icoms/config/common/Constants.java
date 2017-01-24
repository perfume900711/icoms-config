package com.hikvision.icoms.config.common;

/**
 * 常量定义
 * 
 * @author     jiangyue3
 * @date       2016年4月21日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年4月21日
 */
public class Constants {
	
	/**
	 * coms默认端口
	 */
	public static final String comsPort = "9090";
	
	/**
	 * 配置数据缓存文件
	 */
	public static String fileJdbcPath 		= "C:\\comsJdbcBak.properties";
	public static String fileSystemPath 	= "C:\\comsSystemBak.properties";
	
	/**
	 * 单点配置
	 */
	public static final String SinglePoint = "单点登录";
	public static final String notSinglePoint = "非单点登录";
	
	/**
	 * 6.x版本
	 */
	public static final String version636 = "ivms6.3.6";
	public static final String version641 = "ivms6.4.1";
	public static final String version650 = "ivms6.5.0";
	
	/**
	 * 对接协议
	 */
	public static final String protocolHttp = "http";
	public static final String protocolHttps = "https";
	
}
