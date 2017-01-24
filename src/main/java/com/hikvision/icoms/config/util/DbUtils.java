package com.hikvision.icoms.config.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikvision.icoms.config.model.JdbcInfo;


/**
 * 数据库操作工具类
 * 
 * @author jiangyue3
 * @date 2016年12月28日
 * @since 5.2.0
 * @revision [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class DbUtils {

	private final static Logger logger = LoggerFactory.getLogger(DbUtils.class);

	private static Statement statement = null;

	private static ResultSet resultset = null;

	private static String sqlText = null;
	
	/**
	 * 获取数据库连接
	 * 
	 * @param jdbcInfo
	 * @return
	 * @author jiangyue3
	 * @date  2016年12月30日下午5:07:07
	 * @since  5.2.0
	 */
	public static Connection getConnection(JdbcInfo jdbcInfo){
		Connection connection = null;
		connection = connectDb(jdbcInfo.getDbType(),jdbcInfo.getDbUrl(), jdbcInfo.getDbName(), jdbcInfo.getUserName(), jdbcInfo.getUserPwd());
		
		return connection;
	}
	
	/**
	 * 数据库初始化主要分为2步1、创建数据库2、初始化数据库
	 */
	/**
	 * 创建数据库
	 * 
	 * @author jiangyue3
	 * @date  2016年12月30日下午5:09:09
	 * @since  5.2.0
	 */
	public static void createDataBase(){
		
	}
	
	/**
	 * 初始化数据库
	 * 
	 * @author jiangyue3
	 * @date  2016年12月30日下午5:08:54
	 * @since  5.2.0
	 */
	public static void initDataBase(){
		
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param dbType
	 * @param dbIp
	 * @param dbSid
	 * @param dbUsername
	 * @param dbpassword
	 * @return
	 * @author jiangyue3
	 * @date 2016年12月28日下午8:39:50
	 * @since 5.2.0
	 */
	public static Connection connectDb(String dbType, String dbIp,
			String dbSid, String dbUsername, String dbpassword) {
		Connection connection = null;
		String url = null;
		try {
			if (dbType == "oracle") {
				url = "jdbc:oracle:thin:@" + dbIp + ":1521:" + dbSid;
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			} else if (dbType == "postgresql") {
				url = "jdbc:postgresql://" + dbIp + ":5432/" + dbSid;
				Class.forName("org.postgresql.Driver").newInstance();
			} else if (dbType == "Mysql") {
				url = "jdbc:mysql://" + dbIp + ":3306/" + dbSid
						+ "?Unicode=true&characterEncoding=utf-8";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}
			connection = DriverManager.getConnection(url, dbUsername,
					dbpassword);
			return connection;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误信息", 0);
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			if (dbType == "Postgresql") {
				logger.error(e.getMessage());
				JOptionPane.showMessageDialog(null, dbType + "数据库："
						+ dbUsername + " 连接失败", "错误信息", 0);
				e.printStackTrace();
			} else {
				logger.error(e.getMessage());
				JOptionPane.showMessageDialog(null, dbType + "数据库："
						+ dbUsername + " 连接失败" + e.getMessage(), "错误信息", 0);
				e.printStackTrace();
			}
			return null;
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误信息", 0);
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误信息", 0);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 * @author jiangyue3
	 * @date  2016年12月29日下午3:22:00
	 * @since  5.2.0
	 */
	public static void closeConn(Connection conn) {
		try {
			if (resultset != null) {
				resultset.close();
				resultset = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 执行sql
	  * 
	  * @param conn
	  * @param fileName
	  * @return
	  * @author jiangyue3
	  * @date  2016年12月29日下午3:30:14
	  * @since  5.2.0
	  */
	 public static boolean excuteSql(Connection conn, String fileName)
	  {
	    Boolean sqlFlag = Boolean.valueOf(true);
	    String sql_text = null;
	    List<String> sqlList = new ArrayList<String>();
	    FileUtils.readOracleFile(fileName, sqlList);
	    try {
	      statement = conn.createStatement();
	    }
	    catch (SQLException e1) {
	      e1.printStackTrace();
	    }
	    for (Integer i = Integer.valueOf(0); i.intValue() < sqlList.size(); i = Integer.valueOf(i.intValue() + 1)) {
	      sql_text = (String)sqlList.get(i.intValue());
	      try {
	        statement.executeQuery(sql_text);
	      }
	      catch (SQLException e) {
	        logger.error(e.getMessage() + " \n sql语句: " + sql_text);
	        sqlFlag = Boolean.valueOf(false);
	        e.printStackTrace();
	      }
	    }

	    return sqlFlag.booleanValue();
	  }
	 
	 /**
	  * postgresql数据库数据库是否已存在
	  * 
	  * @param sysconn
	  * @param dbName
	  * @return
	  * @author jiangyue3
	  * @date  2016年12月29日下午3:33:14
	  * @since  5.2.0
	  */
	 public static boolean postgresDbIsExists(Connection sysconn, String dbName)
	  {
	    try
	    {
	      statement = sysconn.createStatement();
	      sqlText = "select datname from pg_database where datname = '" + dbName.toLowerCase() + "'";
	      resultset = statement.executeQuery(sqlText);
	      if (resultset.next()) 
	    	  return true;
	    }
	    catch (SQLException e)
	    {
	      logger.error(e.getMessage());
	      JOptionPane.showMessageDialog(null, e.getMessage(), "错误信息", 0);
	      e.printStackTrace();
	    }
	    return false;
	  }
	 
		  public static boolean excuteUpdateSql(Connection conn, String fileName) {
		    Boolean sqlFlag = Boolean.valueOf(true);
		    String sql_text = null;
		    List<String> sqlList = new ArrayList<String>();
		    FileUtils.readOracleFile(fileName, sqlList);
		    try {
		      statement = conn.createStatement();
		    }
		    catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		    for (Integer i = Integer.valueOf(0); i.intValue() < sqlList.size(); i = Integer.valueOf(i.intValue() + 1)) {
		      sql_text = (String)sqlList.get(i.intValue());
		      try {
		        statement.executeQuery(sql_text);
		      }
		      catch (SQLException e) {
		    	  logger.error(e.getMessage() + " \n sql语句: " + sql_text);
		        sqlFlag = Boolean.valueOf(false);
		        e.printStackTrace();
		      }
		    }

		    if (!(sqlFlag.booleanValue())) {
		      JOptionPane.showMessageDialog(null, "升级执行过程中出现错误或警告，请查看日志进行确认！", "错误信息", 0);
		    }
		    return sqlFlag.booleanValue();
		  }

		  public static boolean postgresIsExistTable(Connection conn) {
		    try {
		      statement = conn.createStatement();
		      sqlText = "select tablename from pg_tables where schemaname='public'";
		      resultset = statement.executeQuery(sqlText);
		      if (!resultset.next()) 
		    	  return false;
		    }
		    catch (SQLException e)
		    {
		    	logger.error(e.getMessage());
		      e.printStackTrace();
		    }
		    return true;
		  }

		  public static boolean postgresCreateDb(Connection sysconn, String sysUserName, String dbname)
		  {
		    if (!(postgresDbIsExists(sysconn, dbname))) {
		      try
		      {
		        statement = sysconn.createStatement();
		        sqlText = "CREATE DATABASE \"" + dbname + "\" WITH OWNER = " + sysUserName + " ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1 TEMPLATE template0";
		        statement.execute(sqlText);
		      }
		      catch (SQLException e) {
		        logger.error(e.getMessage());
		        e.printStackTrace();
		        return false;
		      }
		    }

		    return true;
		  }

		  public static boolean postgreExcuteSql(Connection conn, String fileName){
		    Boolean sqlFlag = Boolean.valueOf(true);
		    String sql_text = null;
		    List<String> sqlList = new ArrayList<String>();
		    FileUtils.readPostgresSqlFile(fileName, sqlList);
		    try {
		      statement = conn.createStatement();
		    }
		    catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		    for (Integer i = Integer.valueOf(0); i.intValue() < sqlList.size(); i = Integer.valueOf(i.intValue() + 1)) {
		      sql_text = (String)sqlList.get(i.intValue());
		      try {
		        statement.execute(sql_text);
		      } catch (Exception e) {
		        logger.error(e.getMessage() + " \n sql语句: " + sql_text);
		        sqlFlag = Boolean.valueOf(false);
		        e.printStackTrace();
		      }
		    }
		    return sqlFlag.booleanValue();
		  }
		  
		  public static boolean postgreExcuteSqlFor93(Connection conn, String fileName) throws Exception{
			  Boolean sqlFlag = Boolean.valueOf(true);
			  String sql_text = null;
			  List<String> sqlList = new ArrayList<String>();
			  FileUtils.readPostgresSqlFile(fileName, sqlList);
			  statement = conn.createStatement();
			  for (Integer i = Integer.valueOf(0); i.intValue() < sqlList.size(); i = Integer.valueOf(i.intValue() + 1)) {
			    sql_text = (String)sqlList.get(i.intValue());
			      System.out.println("start execute:"+sql_text);
			      statement.execute(sql_text);
			  }
			  return sqlFlag.booleanValue();
		  }

		  public static boolean postgreExcuteUpdateSql(Connection conn, String fileName) {
		    Boolean sqlFlag = Boolean.valueOf(true);
		    String sql_text = null;
		    List<String> sqlList = new ArrayList<String>();
		    FileUtils.readPostgresSqlFile(fileName, sqlList);
		    try {
		      statement = conn.createStatement();
		    }
		    catch (SQLException e1) {
		      e1.printStackTrace();
		    }
		    for (Integer i = Integer.valueOf(0); i.intValue() < sqlList.size(); i = Integer.valueOf(i.intValue() + 1)) {
		      sql_text = (String)sqlList.get(i.intValue());
		      try {
		        statement.execute(sql_text);
		      }
		      catch (SQLException e) {
		        logger.error(e.getMessage() + " \n sql语句: " + sql_text);
		        sqlFlag = Boolean.valueOf(false);
		        e.printStackTrace();
		      }
		    }

		    if (!(sqlFlag.booleanValue())) {
		      JOptionPane.showMessageDialog(null, "升级执行过程中出现错误或警告，请查看日志进行确认！", "错误信息", 0);
		    }
		    return sqlFlag.booleanValue();
		  }

}
