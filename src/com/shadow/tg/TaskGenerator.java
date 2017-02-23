package com.shadow.tg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadow.tg.impl.MacModule;
import com.shadow.tg.impl.TrayModule;

/**
 * 任务生成器
 *
 * @author hippolee
 * @create 2017年2月16日
 * @version 1.0
 */
public class TaskGenerator {

	/** Logger */
	public static Logger logger = LoggerFactory.getLogger(TaskGenerator.class);

	public static void main(String[] args) {
		// 注册Mac模块
		ApplicationManager.getInstance().registerModule(new MacModule());
		// 注册系统托盘模块
		ApplicationManager.getInstance().registerModule(new TrayModule());
		// 启动应用
		ApplicationManager.getInstance().initApplication();
	}

}
