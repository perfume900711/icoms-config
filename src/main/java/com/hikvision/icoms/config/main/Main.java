package com.hikvision.icoms.config.main;

import com.hikvision.icoms.config.observer.BackUpPro;
import com.hikvision.icoms.config.observer.ComsConfigPro;
import com.hikvision.icoms.config.observer.ComsNetDomainPro;
import com.hikvision.icoms.config.observer.ComsWebXml;
import com.hikvision.icoms.config.observer.InitDbAction;
import com.hikvision.icoms.config.util.MsgUtils;

/**
 * 主方法，打开配置界面
 * 
 * @author     jiangyue3
 * @date       2016年4月21日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年4月21日
 */
public class Main {
	
	public static void main(String[] args){
		MainForm frame = new MainForm();
		frame.setTitle("运维系统配置工具");
		Manager manager = new Manager(frame);
		ComsConfigPro comsConfigPro = new ComsConfigPro(manager);
		ComsNetDomainPro comsNetDomainPro = new ComsNetDomainPro(manager);
		ComsWebXml comsWebXml = new ComsWebXml(manager);
		BackUpPro backUpPro = new BackUpPro(manager);
		InitDbAction initDbAction = new InitDbAction(manager);
		try {
			manager.openForm();
		} catch (Exception e) {
			e.printStackTrace();
			MsgUtils.error(e.getMessage());
			System.exit(1);
		}
	}
	
}
