package com.hikvision.icoms.config.observer;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.FileUtils;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * tomcat的server.xml配置文件修改<br>
 * 运维需要配置协议，<B>http或者https</B>
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class TomcatServerXml implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(TomcatServerXml.class);
	
	private final static String path = "COMS\\conf";
	
	private final static String name = "server.xml";
	
	private final static String name_http = "server_http.xml";
	
	private final static String name_https = "server_https.xml";
	
	private final static String filePath = path + File.separator + name;
	
	private Manager manager;
	
	public TomcatServerXml(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("tomcat's server.xml文件配置");
			saveTomcatServerXml(((Manager) o).getSystemInfo());
		}
	}
	
	private void saveTomcatServerXml(SystemInfo systemInfo){
		String srcFilePath = PropertyUtil.getRootFilePath()+File.separator+path+File.separator;
		if(Constants.protocolHttps.equals(systemInfo.getProtocol())){
			srcFilePath += name_https;
		}else{
			srcFilePath += name_http;
		}
		try {
			FileUtils.copy(srcFilePath,	PropertyUtil.getRootFilePath()+filePath);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
