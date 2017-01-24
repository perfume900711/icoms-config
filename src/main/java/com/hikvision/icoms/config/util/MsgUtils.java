package com.hikvision.icoms.config.util;

import javax.swing.JOptionPane;

/**
 * 提示信息工具类
 * 
 * @author     jiangyue3
 * @date       2017年1月3日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2017年1月3日
 */
public class MsgUtils {
	
	/**
	 * 提示信息
	 * 
	 * @param message 提示消息
	 * @author jiangyue3
	 * @date  2017年1月3日下午7:39:12
	 * @since  5.2.0
	 */
	public static void alert(String message) {
		JOptionPane.showMessageDialog(null, message, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 错误信息
	 * 
	 * @param message 错误信息
	 * @author jiangyue3
	 * @date  2017年1月3日下午7:39:28
	 * @since  5.2.0
	 */
	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "提示", JOptionPane.ERROR_MESSAGE);
	}

}
