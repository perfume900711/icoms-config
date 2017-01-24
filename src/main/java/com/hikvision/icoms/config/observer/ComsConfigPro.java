package com.hikvision.icoms.config.observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.JdbcInfo;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 观察者，配置运维config.properties文件
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class ComsConfigPro implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(ComsConfigPro.class);
	
	private final static String path = "COMS\\webapps\\coms\\WEB-INF\\classes";
	
	private final static String name = "config.properties";
	
	private final static String filePath = path + File.separator + name;
	
	private Manager manager;
	
	public ComsConfigPro(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("配置config.properties信息");
			try {
				saveComsConfig(((Manager) o).getJdbcInfo(),((Manager) o).getSystemInfo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * config.properties文件保存
	 * 
	 * @param jdbcInfo
	 * @param systemInfo
	 * @param proType
	 * @throws IOException
	 * @author jiangyue3
	 * @date  2016年4月21日下午2:44:10
	 * @since  5.2.0
	 */
	private void saveComsConfig(JdbcInfo jdbcInfo,SystemInfo systemInfo) throws IOException{
		logger.info("开始保存config.properties");
		String path = PropertyUtil.getRootFilePath() + File.separator + filePath;
		File file = new File(path);
		if(file.exists()){
			logger.info("config-path:"+path);
			Properties configPro = PropertyUtil.loadProperty(path);
			if(null!=configPro){
				Map<String,String> valuesMap = new HashMap<String, String>();
				valuesMap.put("db.ip",jdbcInfo.getDbUrl());
				valuesMap.put("db.port",jdbcInfo.getDbPort());
				valuesMap.put("db.username",jdbcInfo.getUserName());
				valuesMap.put("db.password",jdbcInfo.getUserPwd());
				valuesMap.put("db.dbname",jdbcInfo.getDbName());
				valuesMap.put("db.driverClassName",jdbcInfo.getDriverClass());
				//afc与coms放一起
				valuesMap.put("dock.ip",systemInfo.getVmsUrl());//对接平台地址
				
				valuesMap.put("dock.context","cms");
				valuesMap.put("dock.version",systemInfo.getVmsVersion());
				//使用6.X的端口
				valuesMap.put("install.nms.mq.ip", systemInfo.getMqUrl());
				
				
				if(Constants.protocolHttps.equals(systemInfo.getProtocol())){
					valuesMap.put("dock.port","443");
					valuesMap.put("net.protocol", systemInfo.getProtocol());
				}else{
					valuesMap.put("net.protocol", "http");
					valuesMap.put("dock.port","80");
				}
				
				//单点确认
				if(systemInfo.getSinglePoint()){
					valuesMap.put("cas.ssoEnable","true");
					valuesMap.put("send.data.toplate", "true");
				}else{
					valuesMap.put("cas.ssoEnable","false");
					valuesMap.put("send.data.toplate", "false");
				}
				
				//同步级联数据
				valuesMap.put("sync.cascade.status", "true");
				
				//默认生产模式和默认同步
				//sys.devMode： true为开发模式，false为生产模式
				valuesMap.put("sys.devMode", "false");//默认生产模式
				valuesMap.put("sys.syncEnable", "true");//默认同步
				
				for(String value : valuesMap.keySet()){
					if(null==configPro.get(value)){
						continue;
					}
					configPro.setProperty(value, valuesMap.get(value));
				}
				OutputStream systemFos = new FileOutputStream(path);
				configPro.store(systemFos, null);
			}
		}else{
			throw new FileNotFoundException(filePath + "文件不存在");
		}
		logger.info("config保存结束");
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
