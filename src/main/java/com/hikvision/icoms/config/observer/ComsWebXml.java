package com.hikvision.icoms.config.observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.FileUtils;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 保存web.xml配置文件
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class ComsWebXml implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(ComsWebXml.class);
	
	private final static String path = "COMS\\webapps\\coms\\WEB-INF";
	
	private final static String name = "web.xml";
	
	private final static String filePath = path + File.separator + name;
	
	private Manager manager;
	
	public ComsWebXml(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Manager && ActionBehavior.SAVE.equals(arg)){
			logger.info("配置web.xml信息");
			try {
				saveWebXml(((Manager) o).getSystemInfo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 保存web.xml配置文件
	 * 
	 * @param systemInfo
	 * @throws IOException
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:20:07
	 * @since  5.2.0
	 */
	private void saveWebXml(SystemInfo systemInfo) throws IOException{
		logger.info("开始保存web.xml");
		StringBuilder originWebXmlpath = new StringBuilder(PropertyUtil.getRootFilePath()).append("\\ConfigTool\\web\\");
		String targetWebXmlPath = PropertyUtil.getRootFilePath() + File.separator + filePath;
		if(systemInfo.getSinglePoint()){
			originWebXmlpath.append("sso\\web.xml");
		}else{
			originWebXmlpath.append("nsso\\web.xml");
		}
		File originWebFile = new File(originWebXmlpath.toString());
		File targetWebFile = new File(targetWebXmlPath);
		if(!originWebFile.exists() && targetWebFile.exists()){
			throw new FileNotFoundException("web.xml文件不存在");
		}
		FileUtils.copy(originWebXmlpath.toString(), targetWebXmlPath);
		
		//单点修改，单点的服务器
		if(systemInfo.getSinglePoint()){
			String webXmlPath = PropertyUtil.getRootFilePath() + File.separator + filePath;
			File file = new File(webXmlPath);
			if(!file.exists()){
				logger.error("web.xml文件保存失败，文件路径错误！");
				return;
			}
			try {
				
				String webXmlCmsAddr = "http://"+systemInfo.getVmsUrl()+":80/cms";
				String webXmlComsAddr = "http://"+systemInfo.getComsUrl()+":9090/";
				
				if(Constants.protocolHttps.equals(systemInfo.getProtocol())){
					webXmlCmsAddr = "http://"+systemInfo.getVmsUrl()+":8081/cms";
					webXmlComsAddr = "https://"+systemInfo.getComsUrl()+":9090/";
				}
				
				FileUtils.configWebXml(webXmlPath, "casServerUrlPrefix", webXmlCmsAddr);
				FileUtils.configWebXml(webXmlPath, "serverName", webXmlComsAddr);
			} catch (DocumentException e) {
				logger.error("web.xml文件保存失败"+e.toString());
			} catch (IOException e) {
				logger.error("web.xml文件保存失败"+e.toString());
			}
		}
		logger.info("web.xml保存结束");		
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	

}
