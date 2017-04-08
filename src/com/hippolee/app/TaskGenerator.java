package com.hippolee.app;

import com.hippolee.app.manager.ApplicationManager;
import com.hippolee.app.module.MacModule;
import com.hippolee.app.module.MenuModule;
import com.hippolee.app.module.TaskTableModule;
import com.hippolee.app.module.TrayModule;

/**
 * 任务生成器
 *
 * @author hippolee
 * @create 2017年2月16日
 * @version 1.0
 */
public class TaskGenerator {

	public static void main(String[] args) {
		// 注册Mac模块
		ApplicationManager.getInstance().registerModule(new MacModule());
		// 注册系统托盘模块
		ApplicationManager.getInstance().registerModule(new TrayModule());
		// 注册系统菜单模块
		ApplicationManager.getInstance().registerModule(new MenuModule());
		// 任务表模块
		ApplicationManager.getInstance().registerModule(new TaskTableModule());
		// 启动应用
		ApplicationManager.getInstance().initApplication();
	}

}
