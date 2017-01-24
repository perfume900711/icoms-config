package com.hikvision.icoms.config.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 * 
 * @author jiangyue3
 * @date 2016年12月28日
 * @since 5.2.0
 * @revision [修订记录] By jiangyue3 @ 2016年12月28日
 */
public class FileUtils {

	public static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	/**
	 * 文件复制方法
	 * 
	 * @param srcFilePath
	 *            内容来源文件
	 * @param dstFileName
	 *            目标文件
	 * @throws IOException
	 * @author jiangyue3
	 * @date 2016年12月28日下午4:09:51
	 * @since 5.2.0
	 */
	public static void copy(String srcFilePath, String dstFileName)
			throws IOException {

		try (FileInputStream fi = new FileInputStream(new File(srcFilePath));
				FileOutputStream fo = new FileOutputStream(
						new File(dstFileName));
				FileChannel in = fi.getChannel();
				FileChannel out = fo.getChannel();) {
			in.transferTo(0, in.size(), out);
		}

	}

	/**
	 * 保存xml类型的属性参数
	 * 
	 * @param filePath
	 *            xml文件路径
	 * @param attrPath
	 *            属性参数在文件内容地位置 比如：//Configuration/NMSConfig/Cods/@ip<br>
	 *            Configuration为根标签，NMSConfig和Cods为中间标签，ip为属性名称
	 * @param attrValue
	 *            属性值
	 * @throws Exception
	 * @author jiangyue3
	 * @throws FileNotFoundException
	 * @date 2016年12月28日下午4:15:52
	 * @since 5.2.0
	 */
	public static void saveXmlAttr(String filePath, String attrPath,
			String attrValue) throws FileNotFoundException {
		File xmlFile = new File(filePath);
		if (!xmlFile.exists()) {
			throw new FileNotFoundException("文件不存在，" + filePath);
		}
		logger.info("开始保存xml,文件路径" + filePath);

		try (FileInputStream fis = new FileInputStream(xmlFile);) {
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(fis);
			Node node = doc.selectSingleNode(attrPath);
			node.setText(attrValue);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filePath),
					format);
			xmlWriter.write(doc);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束保存xml");
	}

	/**
	 * 修改web.xml配置文件（主要6.X使用）
	 * 
	 * @param webXmlPath
	 * @param paramName
	 * @param paramValue
	 * @throws DocumentException
	 * @throws IOException
	 * @author jiangyue3
	 * @date 2016年12月28日下午5:28:36
	 * @since 5.2.0
	 */
	@SuppressWarnings("unchecked")
	public static void configWebXml(String webXmlPath, String paramName,
			String paramValue) throws DocumentException, IOException {
		List<Element> nodes = new ArrayList<Element>();
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(webXmlPath);
		nodes = doc
				.selectNodes("/*[name()='web-app']/*[name()='filter']/*[name()='init-param']");
		for (Element node : nodes) {
			List<Node> initParamNodes = node.content();
			if (null == initParamNodes || initParamNodes.size() == 0) {
				continue;
			}
			boolean flag = false;
			for (Node initParamNode : initParamNodes) {
				if ("Text".equals(initParamNode.getNodeTypeName())) {
					continue;
				}
				if ("param-name".equals(initParamNode.getName())
						&& paramName.equals(initParamNode.getText())) {
					flag = true;
				}
				if (flag && "param-value".equals(initParamNode.getName())) {
					initParamNode.setText(paramValue);
				}
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(webXmlPath),
				format);
		xmlWriter.write(doc);
		xmlWriter.close();
	}

	/**
	 * 从product配置文件中解析需要执行的初始化sql
	 * 
	 * @param product
	 *            product.xml配置文件路径
	 * @param sqlType
	 *            数据库类型
	 * @return
	 * @throws Exception
	 * @author jiangyue3
	 * @date 2016年12月29日下午3:06:54
	 * @since 5.2.0
	 */
	@SuppressWarnings("unchecked")
	public static List<String> readSqlList(String product, String sqlType)
			throws Exception {
		List<String> list = new ArrayList<String>();
		SAXReader saxReader = new SAXReader();
		FileInputStream fis = new FileInputStream(product);
		Document doc = saxReader.read(fis);
		Element root = doc.getRootElement();
		if (null == root) {
			return list;
		}
		List<Element> sqlTypeList = root.elements();
		for (Element sqlTypeEle : sqlTypeList) {
			if (sqlTypeEle.getName().equals(sqlType)) {
				List<Element> sqlList = sqlTypeEle.elements();
				for (Element sqlEle : sqlList) {
					list.add(sqlEle.getText());
				}
			}
		}
		return list;
	}

	/**
	 * 解析postgresql数据库初始化文件
	 * 
	 * @param fileName
	 *            文件路径
	 * @param sqlList
	 *            sql列表
	 * @author jiangyue3
	 * @date 2016年12月29日下午2:54:26
	 * @since 5.2.0
	 */
	public static void readPostgresSqlFile(String fileName, List<String> sqlList) {
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "utf-8"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if (str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				if ((temp.indexOf(";") != -1)
						&& (temp.indexOf("DECLARE") == -1)
						&& (temp.indexOf(" FUNCTION ") == -1)
						&& (temp.indexOf(" PROCEDURE ") == -1)) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((temp.indexOf("plpgsql") != -1)
						&& (((temp.indexOf("DECLARE") != -1)
								|| (temp.indexOf(" FUNCTION ") != -1) || (temp
								.indexOf(" PROCEDURE ") != -1)))) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((temp.indexOf(";") != -1)
						&& (temp.indexOf(" TRIGGER ") != -1)) {// 需要确认创建触发器是否只有一个分号
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else {
					temp.append(System.getProperty("line.separator"));
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 解析postgresql数据库初始化文件
	 * 
	 * @param fileName
	 *            文件路径
	 * @param sqlList
	 *            sql列表
	 * @author jiangyue3
	 * @date 2016年12月29日下午2:54:26
	 * @since 5.2.0
	 */
	public static List<String> readPostgresSqlFile(String fileName) {
		List<String> sqlList = new ArrayList<String>();
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "utf-8"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty() || str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				if ((temp.indexOf(";") != -1)
						&& (temp.indexOf("DECLARE") == -1)
						&& (temp.indexOf(" FUNCTION ") == -1)
						&& (temp.indexOf(" PROCEDURE ") == -1)) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((temp.indexOf("plpgsql") != -1)
						&& (((temp.indexOf("DECLARE") != -1)
								|| (temp.indexOf(" FUNCTION ") != -1) || (temp
								.indexOf(" PROCEDURE ") != -1)))) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((temp.indexOf(";") != -1)
						&& (temp.indexOf(" TRIGGER ") != -1)) {// 需要确认创建触发器是否只有一个分号
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else {
					temp.append(System.getProperty("line.separator"));
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sqlList;
	}

	/**
	 * 读取oracle数据库初始化sql文件
	 * 
	 * @param fileName
	 * @param sqlList
	 * @author jiangyue3
	 * @date 2016年12月29日下午2:57:36
	 * @since 5.2.0
	 */
	public static void readOracleFile(String fileName, List<String> sqlList) {
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "GBK"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if (str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				if ((temp.indexOf(";") != -1)
						&& (temp.indexOf("declare") == -1)
						&& (temp.indexOf(" function ") == -1)
						&& (temp.indexOf(" procedure ") == -1)
						&& (temp.indexOf(" package ") == -1)
						&& (temp.indexOf("begin") == -1)) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((str.equals("/"))
						&& (((temp.indexOf("declare") != -1)
								|| (temp.indexOf(" function ") != -1)
								|| (temp.indexOf(" procedure ") != -1)
								|| (temp.indexOf(" package ") != -1) || (temp
								.indexOf("begin") != -1)))) {
					sqlList.add(temp.substring(0, temp.length() - 2));
					temp = new StringBuffer();
				} else {
					temp.append('\n');
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 读取oracle数据库初始化sql文件
	 * 
	 * @param fileName
	 * @param sqlList
	 * @author jiangyue3
	 * @date 2016年12月29日下午2:57:36
	 * @since 5.2.0
	 */
	public static List<String> readOracleFile(String fileName) {
		List<String> sqlList = new ArrayList<String>();
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "GBK"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if (str.startsWith("--")) {
					continue;
				}
				temp.append(str);
				if ((temp.indexOf(";") != -1)
						&& (temp.indexOf("declare") == -1)
						&& (temp.indexOf(" function ") == -1)
						&& (temp.indexOf(" procedure ") == -1)
						&& (temp.indexOf(" package ") == -1)
						&& (temp.indexOf("begin") == -1)) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else if ((str.equals("/"))
						&& (((temp.indexOf("declare") != -1)
								|| (temp.indexOf(" function ") != -1)
								|| (temp.indexOf(" procedure ") != -1)
								|| (temp.indexOf(" package ") != -1) || (temp
								.indexOf("begin") != -1)))) {
					sqlList.add(temp.substring(0, temp.length() - 2));
					temp = new StringBuffer();
				} else {
					temp.append('\n');
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sqlList;
	}

	/**
	 * 读取授权初始化sql文件
	 * 
	 * @param fileName
	 * @param sqlList
	 * @author jiangyue3
	 * @date 2016年12月29日下午3:00:54
	 * @since 5.2.0
	 */
	public static void readGrantFile(String fileName, List<String> sqlList) {
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "UTF-8"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if ((str.startsWith("--")) || (str.indexOf("\\r") != -1))
					continue;
				if (str.indexOf("\\n") != -1) {
					continue;
				}
				temp.append(str);
				if (temp.indexOf(";") != -1) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else {
					temp.append(System.getProperty("line.separator"));
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 读取授权初始化sql文件
	 * 
	 * @param fileName
	 * @param sqlList
	 * @author jiangyue3
	 * @date 2016年12月29日下午3:00:54
	 * @since 5.2.0
	 */
	public static List<String> readGrantFile(String fileName) {
		List<String> sqlList = new ArrayList<String>();
		StringBuffer temp = new StringBuffer();
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getResourceAsStream(fileName), "UTF-8"));

			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty()) {
					continue;
				}
				if ((str.startsWith("--")) || (str.indexOf("\\r") != -1))
					continue;
				if (str.indexOf("\\n") != -1) {
					continue;
				}
				temp.append(str);
				if (temp.indexOf(";") != -1) {
					sqlList.add(temp.substring(0, temp.length() - 1));
					temp = new StringBuffer();
				} else {
					temp.append(System.getProperty("line.separator"));
				}
			}
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sqlList;
	}

	/**
	 * 读取文件
	 * 
	 * @param resource
	 *            文件地址
	 * @return
	 * @author jiangyue3
	 * @date 2016年12月29日下午2:55:16
	 * @since 5.2.0
	 */
	private static InputStream getResourceAsStream(String resource) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(new File(resource));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(resource + " not found");
		}
		return stream;
	}

}
