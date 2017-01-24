package com.hikvision.icoms.config.observer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
 * <B>备份文件</B><br>
 * 记录上一次保存的信息，下一次界面打开载入
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class BackUpPro implements Observer {
	
	private Logger logger = LoggerFactory.getLogger(BackUpPro.class);
	
	private Manager manager;
	
	public BackUpPro(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("配置备份信息信息");
			try {
				saveConfig(((Manager) o).getJdbcInfo(),((Manager) o).getSystemInfo());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}
		
	}
	
	/**
	 * 
	 * Description:保存配置信息
	 * @author: jiangyue3
	 * @Date:  2015-6-25 下午1:41:42
	 * @param jdbcInfo
	 * @param systemInfo
	 *
	 */
	private void saveConfig(JdbcInfo jdbcInfo,SystemInfo systemInfo) throws Exception{
		File jdbcFile = new File(Constants.fileJdbcPath);
		if(!jdbcFile.exists()){
				jdbcFile.createNewFile();
		}
		Properties jdbcPro = PropertyUtil.loadProperty(Constants.fileJdbcPath);
		jdbcPro.setProperty("db.url", jdbcInfo.getJdbcUrl());
		jdbcPro.setProperty("db.username", jdbcInfo.getUserName());
		jdbcPro.setProperty("db.password", jdbcInfo.getUserPwd());
		OutputStream jdbcFos = new FileOutputStream(Constants.fileJdbcPath);
		jdbcPro.store(jdbcFos, null);
		
		File systemFile = new File(Constants.fileSystemPath);
		if(!systemFile.exists()){
			systemFile.createNewFile();
		}
		Properties systemPro = PropertyUtil.loadProperty(Constants.fileSystemPath);
		systemPro.setProperty("vmsVersion", systemInfo.getVmsVersion());
		systemPro.setProperty("vmsUrl", systemInfo.getVmsUrl());
//		systemPro.setProperty("vmsPort", systemInfo.getVmsPort());
		systemPro.setProperty("comsUrl", systemInfo.getComsUrl());
		systemPro.setProperty("mqUrl", systemInfo.getMqUrl());
		if(systemInfo.getSinglePoint()){
			systemPro.setProperty("singlePoint", "true");
		}else{
			systemPro.setProperty("singlePoint", "false");
		}
		systemPro.setProperty("protocol", systemInfo.getProtocol());
		OutputStream systemFos = new FileOutputStream(Constants.fileSystemPath);
		systemPro.store(systemFos, null);
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
