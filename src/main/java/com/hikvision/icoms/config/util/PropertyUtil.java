package com.hikvision.icoms.config.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);


	/**
	 * 获取项目部署路径
	 * 
	 * @return 项目部署路径，比如"D:\\Hikvision\\iVMS9310"
	 * @author jiangyue3
	 * @date  2016年12月28日下午7:28:32
	 * @since  5.2.0
	 */
	public static String getRootFilePath(){
		String path = null;
		URL url = PropertyUtil.class.getProtectionDomain().getCodeSource().getLocation();
		try{
			path = URLDecoder.decode(url.getPath(), "utf-8");
		}catch(Exception e){
			logger.error(e.toString());
		}
		if(path.endsWith(".jar")){
			path = path.substring(0, path.lastIndexOf("/")); 
		}
		//到项目部署的根目录COMS
		path = path.substring(1, path.lastIndexOf("/"));
		logger.info("项目部署路径:"+path);
		return path;
		
	}
	
	public static Properties loadProperty(String fileName) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(fileName));
			props.load(in);
			in.close();
			return props;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
	
}
