package com.hikvision.icoms.config.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.main.MainForm;


/**
 * 数据库配置对象 
 * 
 * @author     jiangyue3
 * @date       2016年4月21日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年4月21日
 */
public class JdbcInfo {
	
	private static final Logger logger = LoggerFactory.getLogger(JdbcInfo.class);

	private String dbType = "postgresql";
	private String dbName = "coms";
	private String dbUrl = "127.0.0.1";
	private String dbPort = "5432";
	private String userName = "postgres";
	private String userPwd = "123456";

	public JdbcInfo() {
		super();
	}

	/**
	 * 根据备份配置文件生成新的JdbcInfo实例
	 * @param properties
	 */
	public JdbcInfo(Properties properties) {
		this.userName = properties.getProperty("db.username");
		this.userPwd = properties.getProperty("db.password");
		String jdbcUrl = properties.getProperty("db.url");
		if (jdbcUrl.contains("oracle")) {
			// jdbc:oracle:thin:@10.64.52.20:1521:orcl
			this.dbType = "oracle";
			String s = jdbcUrl.split("@")[1];
			String[] arr = s.split(":");
			this.dbName = arr[2];
			this.dbUrl = arr[0];
		}
		if (jdbcUrl.contains("mysql")) {
			// jdbc:mysql://10.64.52.28/cms2?Unicode=true&characterEncoding=utf-8
			this.dbType = "mysql";
			String s = jdbcUrl.split("\\//")[1];
			s = s.split("\\?")[0];
			String[] arr = s.split("/");
			this.dbName = arr[1];
			this.dbUrl = arr[0];
		}
		if (jdbcUrl.contains("postgresql")) {
			// jdbc:postgresql://10.21.48.151:5432/coms3_down?useUnicode=true&characterEncoding=utf-8
			this.dbType = "postgresql";
			String s = jdbcUrl.split("\\//")[1];
			String[] sArr = s.split("/");
			String[] arr = sArr[0].split(":");
			String[] dbArr = sArr[1].split("\\?");
			this.dbName = dbArr[0];
			this.dbUrl = arr[0];
			this.dbPort = arr[1];
		}
	}

	/**
	 * 根据界面的配置生成对象，创建新的JdbcInfo实例
	 * @param frame
	 */
	public JdbcInfo(MainForm frame) {
		this.dbType = frame.dbTypeComBox.getSelectedItem().toString();
		this.dbName = frame.dbNameText.getText();
		this.dbUrl = frame.dbUrlText.getText();
		this.dbPort = frame.dbPortText.getText();
		this.userName = frame.dbUserText.getText();
		this.userPwd = new String(frame.dbPwdText.getPassword());
	}

	/**
	 * 把内容设置到界面展示
	 * 
	 * @param frame
	 * @author jiangyue3
	 * @date  2016年12月29日下午4:46:28
	 * @since  5.2.0
	 */
	public void loadJdbcInfoConfig(MainForm frame) {
		frame.dbNameText.setText(this.dbName);
		frame.dbTypeComBox.setSelectedItem(this.dbType);
		frame.dbPortText.setText(this.dbPort);
		frame.dbPwdText.setText(this.userPwd);
		frame.dbUrlText.setText(this.dbUrl);
		frame.dbUserText.setText(this.userName);
	}
	
	/**
	 * 连接测试
	 * 
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月29日下午4:32:25
	 * @since  5.2.0
	 */
	public boolean testConn(){
		Connection connection = null;
		try {
			Class.forName(this.getDriverClass()).newInstance();
			connection = DriverManager.getConnection(this.getJdbcUrl(),this.getUserName(), this.getUserPwd());
			connection.close();
			logger.info("链接测试成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return false;
		} 
		
	}
	
	/**
	 * 创建用户
	 * 
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月29日下午4:33:08
	 * @since  5.2.0
	 */
	public boolean createUser(){
		return false;
	}
	
	/**
	 * 初始化数据库
	 * 
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月29日下午4:33:43
	 * @since  5.2.0
	 */
	public boolean initDataBase(){
		
		return false;
	}
	
	
	
	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getDialect() {
		if (this.dbType.equals("mysql")) {
			return "org.hibernate.dialect.MySQL5InnoDBDialect";
		} else if (this.dbType.equals("oracle")) {
			return "org.hibernate.dialect.Oracle9iDialect";
		} else if (this.dbType.equals("postgresql")) {
			return "org.hibernate.dialect.PostgreSQLDialect";
		}
		return null;
	}

	public String getDriverClass() {
		if (this.dbType.equals("mysql")) {
			return "com.mysql.jdbc.Driver";
		} else if (this.dbType.equals("oracle")) {
			return "oracle.jdbc.driver.OracleDriver";
		} else if (this.dbType.equals("postgresql")) {
			return "org.postgresql.Driver";
		}
		return null;
	}

	public String getJdbcUrl() {
		if (this.dbType.equals("mysql")) {
			return "jdbc:mysql://" + this.dbUrl + "/" + this.dbName + "?Unicode=true&characterEncoding=utf-8";
		} else if (this.dbType.equals("oracle")) {
			return "jdbc:oracle:thin:@" + this.dbUrl + ":1521:" + this.dbName;
		} else if (this.dbType.equals("postgresql")) {
			return "jdbc:postgresql://" + this.dbUrl + ":"+ this.dbPort +"/" + this.dbName + "?useUnicode=true&characterEncoding=utf-8";
		}
		return null;
	}
	
	public String getDataSourceUrl(){
		return "jdbc:postgresql://"+this.dbUrl+":"+this.dbPort+"/"+this.dbName+"?useUnicode=true&characterEncoding=utf-8";
	}
	
	public String getSystemJdbcUrl(){
		return "jdbc:postgresql://${install.db.ip}:${install.db.port}/"+this.dbName+"?useUnicode=true&characterEncoding=utf-8";
	}

	public String isValid() {
		if ( null == this.dbName  || this.dbName.trim().isEmpty()) {
			return "数据库名称不能为空";
		}
		if ( null == this.userPwd || this.userPwd.trim().isEmpty()) {
			return "数据库密码不能为空";
		}
		if ( null == this.dbUrl || this.dbUrl.trim().isEmpty()) {
			return "数据库IP地址不能为空";
		}
		if ( null == this.userName || this.userName.trim().isEmpty()) {
			return "数据库用户名不能为空";
		}
		if( null == this.dbPort || this.dbPort.trim().isEmpty()){
			return "数据库端口不能为空";
		}
		return null;
	}
	
}
