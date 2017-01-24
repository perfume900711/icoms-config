package com.hikvision.icoms.config.main;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.hikvision.icoms.config.common.Constants;
import com.hikvision.icoms.config.common.DbType;


/**
 * 配置界面类
 * 	包括数据库配置界面和系统参数配置界面
 * 
 * @author     jiangyue3
 * @date       2016年4月21日
 * @since      5.2.0
 * @revision   [修订记录] By jiangyue3 @ 2016年4月21日
 */
public class MainForm extends JFrame {

	private static final long serialVersionUID = 2965317739243172872L;
	//主面板
	public JTabbedPane mainTabbedPanel;
	//数据库配置
	public JPanel dbSettingPanel;
	//系统配置
	public JPanel sysSettingPanel;
	//数据库连接配置
	public JPanel dbPanel;
	//系统配置
	public JPanel sysPanel;
	
	public JLabel dbType;
	
	public JLabel dbName;
	
	public JLabel dbUrl;
	
	public JLabel dbPort;
	
	public JLabel dbUser;
	
	public JLabel dbPwd;
	
	public JTextField dbTypeText;
	
	public JTextField dbNameText;
	
	public JTextField dbUrlText;
	
	public JTextField dbPortText;
	
	public JTextField dbUserText;
	
	public JPasswordField dbPwdText;
	
	/**
	 * 只配置本机地址IP和82地址IP即可
	 */
	//本机地址和IP//本机地址配置
	public JPanel comsPanel;
	public JLabel comsUrl;
	public JTextField comsUrlText;
	//82地址配置
	public JPanel vmsPanel;
	public JLabel vmsUrl;
	public JTextField vmsUrlText;
	public JLabel vmsPort;
	public JTextField vmsPortText;
	public JLabel vmsProtocol;
	public JComboBox<String> vmsProtocolComBox;
	
	public JLabel mqUrlLabel;
	public JTextField mqUrlText;
	
	//退出按钮
	public JButton btnExit;
	//保存按钮
	public JButton btnSave;
	
	public JButton btnDbConnTest;
	
	public JButton btnDbInit;
	
	public JComboBox<String> dbTypeComBox;
	
	public JLabel singlePointLabel;
	
	public JComboBox<String> singlePointComBox;
	
	public JLabel dockVersionLabel;
	
	public JComboBox<String> dockVersionComboBox;
	
	public JLabel descriptionLabel;
	
	public MainForm(){
		initComponents();
	}
	
	private void initComponents(){
		
		mainTabbedPanel = new JTabbedPane();
		dbSettingPanel = new JPanel();
		sysSettingPanel = new JPanel();
		dbPanel = new JPanel();
		sysPanel = new JPanel();
		
		btnExit = new JButton();
		btnSave = new JButton();
		btnDbConnTest = new JButton();
		btnDbInit = new JButton();
		
		dbType = new JLabel();
		dbName = new JLabel();
		dbUrl = new JLabel();
		dbPort = new JLabel();
		dbUser = new JLabel();
		dbPwd = new JLabel();
		
		dbTypeComBox = new JComboBox<String>();
		
		dbTypeText = new JTextField();
		dbNameText = new JTextField();
		dbUrlText = new JTextField();
		dbPortText = new JTextField();
		dbUserText = new JTextField();
		dbPwdText = new JPasswordField();
		
		vmsPanel = new JPanel();
		vmsUrl = new JLabel();
		vmsUrlText = new JTextField();
		vmsPort = new JLabel();
		vmsPortText = new JTextField();
		
		comsPanel = new JPanel();
		comsUrl = new JLabel();
		comsUrlText = new JTextField();
		
		singlePointLabel = new JLabel();
		singlePointComBox = new JComboBox<String>();
		
		dockVersionLabel = new JLabel();
		dockVersionComboBox = new JComboBox<String>();
		
		vmsProtocol = new JLabel();
		vmsProtocolComBox = new JComboBox<String>();
		
		mqUrlLabel = new JLabel();
		mqUrlText = new JTextField();
		
		descriptionLabel = new JLabel();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(500, 500, 500, 500);
		this.setResizable(false);
		
		dbPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"配置运维数据库连接"));
		
		dbType.setText("类型");
		dbName.setText("数据库名");
		dbUrl.setText("地址");
		dbPort.setText("端口");
		dbUser.setText("用户");
		dbPwd.setText("密码");
		
//		dbTypeComBox.setModel(new DefaultComboBoxModel<String>(new String[] { "postgresql" }));//暂时只支持pg
		dbTypeComBox.setModel(new DefaultComboBoxModel<String>(DbType.getDbTypes()));
		
		singlePointLabel.setText("单点登录配置");
		singlePointComBox.setModel(new DefaultComboBoxModel<String>(new String[]{ Constants.SinglePoint , Constants.notSinglePoint }));
		
		btnDbConnTest.setText("连接测试");
		btnDbInit.setText("初始化数据库");
		
		//数据库配置界面
		GroupLayout dbPanelLayout = new GroupLayout(dbPanel);
		dbPanel.setLayout(dbPanelLayout);
		dbPanelLayout.setHorizontalGroup(
				dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,dbPanelLayout.createSequentialGroup()
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(dbPanelLayout.createSequentialGroup().addContainerGap().addComponent(btnDbInit).addGap(110).addComponent(btnDbConnTest))
				.addGroup(dbPanelLayout.createSequentialGroup().addGap(28, 28, 28)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dbName)
						.addComponent(dbType)
						.addComponent(dbUrl)
						.addComponent(dbPort)
						.addComponent(dbUser,GroupLayout.DEFAULT_SIZE,48, Short.MAX_VALUE)
						.addComponent(dbPwd,GroupLayout.PREFERRED_SIZE,41, GroupLayout.PREFERRED_SIZE)).addGap(97, 97, 97)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
						.addComponent(dbUrlText)
						.addComponent(dbPortText)
						.addComponent(dbTypeComBox,0, 157,Short.MAX_VALUE)
						.addComponent(dbNameText))
				.addGroup(GroupLayout.Alignment.LEADING,dbPanelLayout.createSequentialGroup()
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dbPwdText,GroupLayout.Alignment.TRAILING,GroupLayout.DEFAULT_SIZE,157,Short.MAX_VALUE)
						.addComponent(dbUserText,GroupLayout.DEFAULT_SIZE,157,Short.MAX_VALUE))))))
				.addGap(68, 68, 68)));
		
		dbPanelLayout.setVerticalGroup(
				dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(dbPanelLayout.createSequentialGroup().addContainerGap()
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbTypeComBox, GroupLayout.PREFERRED_SIZE, 21,GroupLayout.PREFERRED_SIZE).addComponent(dbType))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbNameText, GroupLayout.PREFERRED_SIZE, 23,GroupLayout.PREFERRED_SIZE).addComponent(dbName))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbUrlText, GroupLayout.PREFERRED_SIZE, 23,GroupLayout.PREFERRED_SIZE).addComponent(dbUrl))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbPortText, GroupLayout.PREFERRED_SIZE, 23,GroupLayout.PREFERRED_SIZE).addComponent(dbPort))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dbUser)
						.addComponent(dbUserText, GroupLayout.PREFERRED_SIZE, 23,GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(dbPwd)
						.addComponent(dbPwdText, GroupLayout.PREFERRED_SIZE, 23,GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
				.addGroup(dbPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(btnDbInit)
						.addComponent(btnDbConnTest))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		
		//数据库配置界面
		GroupLayout dbSettingLayout = new GroupLayout(dbSettingPanel);
		dbSettingLayout.setHorizontalGroup(dbSettingLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING,dbSettingLayout.createSequentialGroup().addContainerGap()
				.addComponent(dbPanel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE).addContainerGap()));
		
		dbSettingLayout.setVerticalGroup(dbSettingLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(dbSettingLayout.createSequentialGroup().addGap(24)
				.addComponent(dbPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(60, Short.MAX_VALUE)));
		
		dbSettingPanel.setLayout(dbSettingLayout);
		//数据库配置面板加入到主面板中
		mainTabbedPanel.addTab("数据库配置", dbSettingPanel);
		
		/*******************************************************************************************************/
		
		//本机地址
		comsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"运维平台地址配置"));
		comsUrl.setText("运维平台地址");
		mqUrlLabel.setText("MQ地址");
		GroupLayout localPanelLayout = new GroupLayout(comsPanel);
		localPanelLayout.setHorizontalGroup(
				localPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING,localPanelLayout.createSequentialGroup().addContainerGap()
				.addComponent(comsUrl).addGap(40)
				.addComponent(comsUrlText, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122))
				.addGroup(Alignment.LEADING,localPanelLayout.createSequentialGroup().addContainerGap()
				.addComponent(singlePointLabel).addGap(40)
				.addComponent(singlePointComBox, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122))
				.addGroup(Alignment.LEADING,localPanelLayout.createSequentialGroup().addContainerGap()
				.addComponent(mqUrlLabel).addGap(72)
				.addComponent(mqUrlText, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122)));
		
		localPanelLayout.setVerticalGroup(
				localPanelLayout.createParallelGroup(Alignment.TRAILING).addGap(500)
					.addGroup(Alignment.LEADING,localPanelLayout.createSequentialGroup().addContainerGap()
					.addGroup(localPanelLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(comsUrl)
							.addComponent(comsUrlText, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(localPanelLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(singlePointLabel)
					.addComponent(singlePointComBox, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(localPanelLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(mqUrlLabel)
					.addComponent(mqUrlText, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));
		comsPanel.setLayout(localPanelLayout);
				
		//修改为对接平台地址
		vmsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"对接平台地址配置"));
		
		dockVersionLabel.setText("对接平台版本");
		dockVersionComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{ Constants.version636 , Constants.version641 }));
		
		vmsUrl.setText("对接平台地址");
		vmsPort.setText("对接平台端口");
		vmsProtocol.setText("平台协议");
		vmsProtocolComBox.setModel(new DefaultComboBoxModel<String>(new String[]{ Constants.protocolHttp , Constants.protocolHttps }));
		
		GroupLayout gaPanelLayout = new GroupLayout(vmsPanel);
		
		gaPanelLayout.setHorizontalGroup(gaPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING,gaPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(dockVersionLabel).addGap(40)
						.addComponent(dockVersionComboBox, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122)).addGap(20)
				.addGroup(Alignment.LEADING,gaPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(vmsUrl).addGap(40)
						.addComponent(vmsUrlText, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122)).addGap(20)
				.addGroup(Alignment.LEADING,gaPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(vmsProtocol).addGap(65)
						.addComponent(vmsProtocolComBox, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addGap(122)).addGap(20)
				.addGroup(Alignment.LEADING,gaPanelLayout.createSequentialGroup().addContainerGap())
						.addComponent(descriptionLabel));
			
		gaPanelLayout.setVerticalGroup(
				gaPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING,gaPanelLayout.createSequentialGroup().addContainerGap()
				.addGroup(gaPanelLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(dockVersionLabel)
					.addComponent(dockVersionComboBox, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(5)
				.addGroup(gaPanelLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(vmsUrl)
					.addComponent(vmsUrlText, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(5)
				.addGroup(gaPanelLayout.createParallelGroup(Alignment.BASELINE).addGap(20)
					.addComponent(vmsProtocol)
					.addComponent(vmsProtocolComBox, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(5)
				.addGroup(gaPanelLayout.createParallelGroup(Alignment.BASELINE).addGap(20)
					.addComponent(descriptionLabel))
				.addContainerGap(76, Short.MAX_VALUE)));
				
		vmsPanel.setLayout(gaPanelLayout);
		
		//系统配置
		sysPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"系统连接配置"));
		GroupLayout sysPanelLayout = new GroupLayout(sysPanel);
		
		sysPanelLayout.setHorizontalGroup(sysPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(sysPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(sysPanelLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(comsPanel, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
							.addComponent(vmsPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
						.addGap(26)));
		sysPanelLayout.setVerticalGroup(sysPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(sysPanelLayout.createSequentialGroup()
						.addGap(22)
						.addComponent(comsPanel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(vmsPanel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(115, Short.MAX_VALUE)));
		
		sysPanel.setLayout(sysPanelLayout);
		
		GroupLayout sysSettingLayout = new GroupLayout(sysSettingPanel);
		sysSettingLayout.setHorizontalGroup(
				sysSettingLayout.createParallelGroup()
				.addGroup(sysSettingLayout.createSequentialGroup()
						.addGroup(sysSettingLayout.createParallelGroup()
						.addComponent(sysPanel))));
		sysSettingLayout.setVerticalGroup(sysSettingLayout.createParallelGroup()
				.addGroup(sysSettingLayout.createSequentialGroup()
						.addComponent(sysPanel)));
		sysSettingPanel.setLayout(sysSettingLayout);
		
		mainTabbedPanel.addTab("系统配置", sysSettingPanel);
		
		/**********************************************************************************************************/
		
		btnExit.setText("退出");
		btnSave.setText("保存");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addComponent(mainTabbedPanel, GroupLayout.PREFERRED_SIZE, 440,GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addGroup(GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(317, Short.MAX_VALUE).addComponent(btnSave)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnExit)
								.addGap(13, 13, 13)));
		
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
				.addComponent(mainTabbedPanel, GroupLayout.PREFERRED_SIZE, 360,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18, 18, 18)
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSave)
								.addComponent(btnExit)).addContainerGap(23, Short.MAX_VALUE)));
		
		mainTabbedPanel.getAccessibleContext().setAccessibleName("数据库配置");
		
		pack();
	}
	
}
