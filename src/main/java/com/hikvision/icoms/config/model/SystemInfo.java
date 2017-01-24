package com.hikvision.icoms.config.model;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.main.MainForm;


/**
 * 系统参数配置对象
 * 
 * @author     jiangyue3
 * @date       2016年4月21日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年4月21日
 */
public class SystemInfo {
	
	private static Logger logger = LoggerFactory.getLogger(SystemInfo.class);
	
	private String vmsVersion = "ivms6.3.6";
	//82地址配置
	private String vmsUrl = "0.0.0.0";
	
	private String vmsPort = "80";
	
	private String protocol = "http";
	
	private String comsUrl = "127.0.0.1";
	
	private String mqUrl = "127.0.0.1";
	
	private String comsPort = "9090";
	
	private boolean singlePoint = false;
	
	/**
	 * 
	 */
	public SystemInfo() {
		super();
	}

	/**
	 * 通过配置文件加载
	 * @param properties
	 */
	public SystemInfo(Properties properties) {
		this.vmsVersion = properties.getProperty("vmsVersion");
		this.vmsUrl = properties.getProperty("vmsUrl");
		this.vmsPort = properties.getProperty("vmsPort");
		this.comsUrl = properties.getProperty("comsUrl");
		this.mqUrl = properties.getProperty("mqUrl");
		this.protocol = properties.getProperty("protocol");
		String isSinglePoint = properties.getProperty("singlePoint");
		if("true".equals(isSinglePoint)){
			this.singlePoint = true;
		}else{
			this.singlePoint = false;
		}
	}

	/**
	 * 从界面获取
	 * @param frame
	 */
	public SystemInfo(MainForm frame) {
		this.vmsUrl = frame.vmsUrlText.getText();
		this.vmsPort = frame.vmsPortText.getText();
		this.comsUrl = frame.comsUrlText.getText();//端口默认9090
		this.mqUrl = frame.mqUrlText.getText();
		this.protocol = frame.vmsProtocolComBox.getSelectedItem().toString();
		String singleCombox = frame.singlePointComBox.getSelectedItem().toString();
		if(Constants.SinglePoint.equals(singleCombox)){
			this.singlePoint = true;
		}else{
			this.singlePoint = false;
		}
		this.vmsVersion = frame.dockVersionComboBox.getSelectedItem().toString();
	}
	
	/**
	 * 将数据加载到界面上
	 * 
	 * @param frame
	 * @author jiangyue3
	 * @date  2015年12月24日下午1:50:26
	 * @since  5.2
	 */
	public void loadSystemInfoConfig(MainForm frame) {
		logger.info("加载配置文件信息...");
		frame.vmsUrlText.setText(this.vmsUrl);
		frame.comsUrlText.setText(this.comsUrl);
		frame.vmsPortText.setText(this.vmsPort);
		frame.mqUrlText.setText(this.mqUrl);
		frame.vmsProtocolComBox.setSelectedItem(this.protocol);
		if(true == this.singlePoint){
			frame.singlePointComBox.setSelectedItem(Constants.SinglePoint);
		}else{
			frame.singlePointComBox.setSelectedItem(Constants.notSinglePoint);
		}
		frame.dockVersionComboBox.setSelectedItem(this.vmsVersion);
	}

	public String getVmsVersion() {
		return vmsVersion;
	}

	public void setVmsVersion(String vmsVersion) {
		this.vmsVersion = vmsVersion;
	}

	public String getVmsUrl() {
		return vmsUrl;
	}

	public void setVmsUrl(String vmsUrl) {
		this.vmsUrl = vmsUrl;
	}

	public String getVmsPort() {
		return vmsPort;
	}

	public void setVmsPort(String vmsPort) {
		this.vmsPort = vmsPort;
	}

	public String getComsUrl() {
		return comsUrl;
	}

	public void setComsUrl(String comsUrl) {
		this.comsUrl = comsUrl;
	}

	public String getComsPort() {
		return comsPort;
	}

	public void setComsPort(String comsPort) {
		this.comsPort = comsPort;
	}
	
	public boolean getSinglePoint() {
		return singlePoint;
	}

	public void setSinglePoint(boolean singlePoint) {
		this.singlePoint = singlePoint;
	}
	
	public String getComsAddr(){
		return this.comsUrl+":"+this.comsPort;
	}
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getMqUrl() {
		return mqUrl;
	}

	public void setMqUrl(String mqUrl) {
		this.mqUrl = mqUrl;
	}

	public String isValid() {
		if(null == this.comsUrl || this.comsUrl.trim().isEmpty()) {
			return "运维平台地址不能为空";
		}
		if(null == this.vmsUrl || this.vmsUrl.trim().isEmpty()){
			return "集成平台地址不能为空";
		}
		if(null == this.mqUrl || this.mqUrl.trim().isEmpty()){
			return "mq地址不能为空";
		}
//		if(null == this.vmsPort  || this.vmsPort.trim().isEmpty()){
//			return "集成平台端口不能为空";
//		}
		return null;
	}
	
}
