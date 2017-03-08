package com.shadow.app.manager;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;

import com.shadow.app.component.MainFrame;
import com.shadow.app.module.IApplicationModule;

/**
 * 应用管理器
 *
 * @author hippolee
 * @create 2017年2月22日
 * @version 1.0
 */
public class ApplicationManager {

	/** 单例 */
	private static final ApplicationManager instance = new ApplicationManager();

	private ApplicationManager() {

	}

	/** 单例 */
	public static ApplicationManager getInstance() {
		return instance;
	}

	/** 主界面 */
	private JFrame mainFrame;

	/** 模块 */
	private Vector<IApplicationModule> modules = new Vector<>();

	/**
	 * 主界面
	 * 
	 * @return
	 */
	public JFrame getMainFrame() {
		if (mainFrame == null) {
			throw new IllegalStateException("主界面未初始化！");
		}
		return mainFrame;
	}

	/**
	 * 初始化应用
	 */
	public void initApplication() {
		// 初始化主窗体
		initMainFrame();
		// 初始化应用
		initModules();
		// 显示主窗体
		getMainFrame().setVisible(true);
	}

	/**
	 * 初始化主界面
	 */
	private void initMainFrame() {
		// 主窗体
		this.mainFrame = new MainFrame();
	}

	private void initModules() {
		Iterator<IApplicationModule> it = this.modules.iterator();
		while (it.hasNext()) {
			IApplicationModule module = (IApplicationModule) it.next();
			module.initModule();
		}
	}

	/**
	 * 注册模块
	 * 
	 * @param module
	 */
	public void registerModule(IApplicationModule module) {
		modules.add(module);
	}

	/**
	 * 卸载模块
	 * 
	 * @param module
	 */
	public void unRegisterModule(IApplicationModule module) {
		modules.remove(module);
	}

}
