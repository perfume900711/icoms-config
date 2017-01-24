package com.hikvision.icoms.config.main;

import java.util.Observable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.model.JdbcInfo;
import com.hikvision.icoms.config.model.SystemInfo;
import com.hikvision.icoms.config.util.MsgUtils;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 主题类，从界面获取数据，作为操作的一个总的控制器<br>
 * 总共被配置的文件有:<br>
 * ①运维主配置文件<B>config.properties</B><br>
 * ②配置单点文件<B>net_domain</B><br>
 * ③配置<B>web.xml</B><br>
 * ④配置<B>DAGConfig.xml</B><br>
 * ⑤配置tomcat的<B>server.xml</B><br>
 * ⑥保存备份信息<br>
 * 
 * @author     jiangyue3
 * @date       2016年12月28日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class Manager extends Observable {
	
	private final static Logger logger = LoggerFactory.getLogger(Manager.class);
	
	private JdbcInfo jdbcInfo;
	
	private SystemInfo systemInfo;
	
	private MainForm frame;
	
	/**
	 * 绑定组件的事件 创建一个新的实例Manager.
	 * 
	 * @param frame
	 */
	public Manager(MainForm frame) {
		this.frame = frame;
		this.frame.dbTypeComBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				
			}
		});
		this.frame.btnDbConnTest.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonTestConnActionPerformed(ActionBehavior.TESTCONNECTION);
			}
		});
		this.frame.btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(1);
			}
		});
		this.frame.btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBehave(ActionBehavior.SAVE);
			}
		});
		this.frame.btnDbInit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				actionBehave(ActionBehavior.INITDB);
			}
		});
	}
	
	/**
	 * 连接测试
	 * 
	 * @param evt 按钮点击事件
	 * @author jiangyue3
	 * @date  2016年12月29日上午10:41:51
	 * @since  5.2.0
	 */
	private void jButtonTestConnActionPerformed(ActionBehavior actionBehavior) {
		logger.info("点击链接测试按钮");
		jdbcInfo = new JdbcInfo(frame);
		if (jdbcInfo.testConn()) {
			MsgUtils.alert("连接成功");
		} else {
			MsgUtils.error("连接失败");
		}
	}
	
	/**
	 * 保存配置文件
	 * 
	 * @param evt 按钮点击事件
	 * @author jiangyue3
	 * @date  2016年12月29日上午10:42:22
	 * @since  5.2.0
	 */
	private void actionBehave(ActionBehavior actionBehavior) {
		logger.info("点击保存按钮");
		logger.info("开始保存配置文件");
		try {
			jdbcInfo = new JdbcInfo(frame);
			systemInfo = new SystemInfo(frame);
			String message = jdbcInfo.isValid();
			if (message != null) {
				logger.error(message);
				MsgUtils.error(message);
				frame.mainTabbedPanel.setSelectedIndex(0);
				return;
			}
			
			message = systemInfo.isValid();
			if (message != null) {
				logger.error(message);
				MsgUtils.error(message);
				frame.mainTabbedPanel.setSelectedIndex(0);
				return;
			}
			
			configChanged(actionBehavior);

			MsgUtils.alert("配置保存成功");
		} catch (Exception e) {
			logger.error(e.toString());
			MsgUtils.error("配置保存失败");
		}
	}
	
	/**
	 * 通知观察者进行更新
	 * 
	 * @author jiangyue3
	 * @date  2016年12月28日下午8:28:48
	 * @since  5.2.0
	 */
	private void configChanged(ActionBehavior actionBehavior){
		setChanged();
		notifyObservers(actionBehavior);
	}
	
	/**
	 * 打开可视化界面
	 * 
	 * @author jiangyue3
	 * @date  2017年1月4日下午2:46:41
	 * @since  5.2.0
	 */
	public void openForm(){
		try {
			this.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		jdbcInfo.loadJdbcInfoConfig(frame);
		systemInfo.loadSystemInfoConfig(frame);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * 读入配置文件里面的内容，放到对象里面
	 * 
	 * @author QianHaiguo 2013-6-28 下午1:49:58
	 * @throws Exception
	 */
	public void loadConfig() throws Exception {
		this.loadJdbcConfig();
		this.loadSystemConfig();
	}
	
	/**
	 * 从配置文件中加载jdbc信息
	 * 
	 * @throws Exception
	 * @author jiangyue3
	 * @date  2017年1月4日下午2:45:48
	 * @since  5.2.0
	 */
	public void loadJdbcConfig() throws Exception{
		Properties jdbcPro = PropertyUtil.loadProperty(Constants.fileJdbcPath);
		if(null != jdbcPro){
			jdbcInfo = new JdbcInfo(jdbcPro);
			return;
		}
		jdbcInfo = new JdbcInfo();
	}
	
	/**
	 * 从配置文件中加载system信息
	 * 
	 * @author jiangyue3
	 * @date  2017年1月4日下午2:46:10
	 * @since  5.2.0
	 */
	public void loadSystemConfig(){
		Properties systemPro = PropertyUtil.loadProperty(Constants.fileSystemPath);
		if(null != systemPro){
			systemInfo = new SystemInfo(systemPro);
			return;
		}
		systemInfo = new SystemInfo();
	}
	
	public MainForm getFrame() {
		return frame;
	}

	public void setFrame(MainForm frame) {
		this.frame = frame;
	}

	public JdbcInfo getJdbcInfo() {
		return jdbcInfo;
	}

	public void setJdbcInfo(JdbcInfo jdbcInfo) {
		this.jdbcInfo = jdbcInfo;
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}
	
}
