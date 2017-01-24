package com.hikvision.icoms.config.observer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 观察者，保存单点配置文件net_domain配置文件
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class ComsNetDomainPro implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(ComsNetDomainPro.class);
	
	private final static String path = "COMS\\webapps\\coms\\WEB-INF\\classes";
	
	private final static String name = "net_domain.properties";
	
	private final static String filePath = path + File.separator + name;
	
	private Manager manager;
	
	public ComsNetDomainPro(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("配置net_domain信息");
			try {
				saveSinglePointConfig(((Manager) o).getSystemInfo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 单点配置
	 * 
	 * @param systemInfo
	 * @throws Exception
	 * @author jiangyue3
	 * @date  2016年12月28日下午7:40:41
	 * @since  5.2.0
	 */
	private void saveSinglePointConfig(SystemInfo systemInfo) throws Exception{
		if(!systemInfo.getSinglePoint()){
			return;
		}
		logger.info("开始配置net_domain");
		String path = PropertyUtil.getRootFilePath() + File.separator + filePath;
		File netDomainFile = new File(path);
		if(netDomainFile.exists()){
			logger.info("net_domain.properties :"+path);
			FileOutputStream fos = new FileOutputStream(netDomainFile);
			String content = systemInfo.getComsUrl()+"="+systemInfo.getVmsUrl()+":80";
			if(Constants.protocolHttps.equals(systemInfo.getProtocol())){
				content = systemInfo.getComsUrl()+"="+systemInfo.getVmsUrl()+":443";
			}
			fos.write(content.getBytes("utf-8"));
			fos.close();
		}
		
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	

}
