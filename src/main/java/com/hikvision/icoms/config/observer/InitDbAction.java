package com.hikvision.icoms.config.observer;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.common.ActionBehavior;
import com.hikvision.icoms.config.main.Manager;
import com.hikvision.icoms.config.model.JdbcInfo;
import com.hikvision.icoms.config.util.DbUtils;
import com.hikvision.icoms.config.util.FileUtils;
import com.hikvision.icoms.config.util.MsgUtils;
import com.hikvision.icoms.config.util.PropertyUtil;

/**
 * 初始化数据库操作
 * 
 * @author     jiangyue3
 * @date       2017年1月4日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2017年1月4日
 */
public class InitDbAction implements Observer {
	
	private final static Logger logger = LoggerFactory.getLogger(InitDbAction.class);
	
	private Connection conn = null;
	
	private Manager manager;
	
	public InitDbAction(Manager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof Manager && ActionBehavior.INITDB.equals(arg)){
			logger.info(this.getClass().getName()+"数据库初始化开始");
			try {
				if(initDataBase(((Manager) o).getJdbcInfo())){
					MsgUtils.alert("数据库初始化完成");
				}
			} catch (Exception e) {
				MsgUtils.error(e.getMessage());
			}
			logger.info("数据库初始化结束");
		}
		
	}
	
	private boolean initDataBase(JdbcInfo jdbc){
		boolean flag = true;
		try{
			List<String> sqlList = FileUtils.readSqlList(PropertyUtil.getRootFilePath()+"\\ConfigTool\\product.xml", jdbc.getDbType());
			if(!createDB(jdbc)){
				logger.error("数据库创建失败");
				return false;
			}
			for(String sql : sqlList){
				if(!initDB(jdbc,PropertyUtil.getRootFilePath()+"\\"+sql)){
					logger.error("数据库初始化失败");
					System.out.println(sql);
					return false;
				}
			}
		}catch(Exception e){
			flag = false;
			MsgUtils.error(e.getMessage());
		}finally{
			if(null!=conn){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		return flag;
	}
	
	private boolean createDB(JdbcInfo jdbc) throws Exception{
		conn = DbUtils.connectDb(jdbc.getDbType(),jdbc.getDbUrl(), "postgres", jdbc.getUserName(), jdbc.getUserPwd());
		if(null == conn){
			logger.error("数据库连接失败");
			throw new Exception("数据库连接失败");
		}
		if(DbUtils.postgresDbIsExists(conn, jdbc.getDbName())){
			logger.error("数据库"+jdbc.getDbName()+"已经存在，初始化失败");
			if(null != conn){
				conn = null;
			}
			throw new Exception("数据库"+jdbc.getDbName()+"已经存在，初始化失败");
		}
		return DbUtils.postgresCreateDb(conn, "postgres", jdbc.getDbName());
	}
	
	private boolean initDB(JdbcInfo jdbc,String filePath) throws Exception{
		conn = DbUtils.connectDb(jdbc.getDbType(),jdbc.getDbUrl(), jdbc.getDbName(), jdbc.getUserName(), jdbc.getUserPwd());
		if(null == conn){
			logger.error("数据库创建失败");
			throw new Exception("数据库创建失败");
		}
		File sqlFile = new File(filePath);
		if(!sqlFile.exists()){
			logger.error("数据库脚本缺失");
			throw new Exception("数据库脚本缺失");
		}
		return DbUtils.postgreExcuteSqlFor93(conn, filePath);
	}


	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
