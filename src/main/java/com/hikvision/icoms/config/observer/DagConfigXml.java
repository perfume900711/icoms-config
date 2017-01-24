package com.hikvision.icoms.config.observer;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.FileUtils;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 配置DAGConfig配置文件
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class DagConfigXml implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(DagConfigXml.class);
	
	private final static String path = "DAG";
	
	private final static String name = "DAGConfig.xml";
	
	private final static String filePath = path + File.separator + name;
	
	private Manager manager;
	
	public DagConfigXml(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("配置DAGConfig.xml信息");
			try {
				saveDagConfig(((Manager) o).getSystemInfo());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}
	}
	
	/**
	 * 配置DAGConfig文件
	 * 
	 * @param systemInfo
	 * @throws Exception
	 * @author jiangyue3
	 * @date  2016年12月28日下午7:51:00
	 * @since  5.2.0
	 */
	private void saveDagConfig(SystemInfo systemInfo) throws Exception{
		String configFilePath = PropertyUtil.getRootFilePath() + File.separator + filePath;
		FileUtils.saveXmlAttr(configFilePath, "//Configuration/NMSConfig/Cods/@ip", "127.0.0.1");
		FileUtils.saveXmlAttr(configFilePath, "//Configuration/NMSConfig/Cods/@port", "9090");
		FileUtils.saveXmlAttr(configFilePath, "//Configuration/NMSConfig/Cods/@context", "coms/dc");
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
