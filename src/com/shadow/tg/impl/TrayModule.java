package com.shadow.tg.impl;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadow.tg.ApplicationManager;
import com.shadow.tg.constant.IApplicationConst;
import com.shadow.tg.constant.IResourceConst;
import com.shadow.tg.itf.IApplicationModule;
import com.shadow.tg.util.ResourceUtil;
import com.shadow.tg.util.ResourceUtil.IconSize;

/**
 * 系统托盘模块
 *
 * @author hippolee
 * @create 2017年2月22日
 * @version 1.0
 */
public class TrayModule implements IApplicationModule {

	/** Logger */
	public static Logger logger = LoggerFactory.getLogger(TrayModule.class);

	/** 显示/隐藏主窗口 */
	public static final String AC_TOGGLE_MAIN = "AC_TOGGLE_MAIN";
	/** 退出程序 */
	public static final String AC_QUIT = "AC_QUIT";

	@Override
	public void initModule() {
		if (!SystemTray.isSupported()) {
			return;
		}

		// 获得系统托盘的实例
		SystemTray systemTray = SystemTray.getSystemTray();
		// 设置托盘的图标
		TrayIcon trayIcon = new TrayIcon(ResourceUtil.getIconImage(IResourceConst.ICON_APPLICATION, IconSize.ICON_48),
				IApplicationConst.APPLICATION_NAME);
		trayIcon.setImageAutoSize(true);
		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			logger.error("设置系统托盘图标失败");
		}

		// 创建弹出菜单
		PopupMenu popupMenu = new PopupMenu();
		// 显示/隐藏主窗口
		MenuItem menuItem1 = new MenuItem("显示/隐藏主窗口");
		menuItem1.setActionCommand(AC_TOGGLE_MAIN);
		popupMenu.add(menuItem1);
		// 分隔符
		popupMenu.addSeparator();
		// 退出
		MenuItem menuItem2 = new MenuItem("退出");
		menuItem2.setActionCommand(AC_QUIT);
		popupMenu.add(menuItem2);
		// 事件处理
		popupMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 显示/隐藏主窗口
				if (AC_TOGGLE_MAIN.equals(e.getActionCommand())) {
					JFrame mainFrame = ApplicationManager.getInstance().getMainFrame();
					if (mainFrame.isVisible()) {
						mainFrame.dispose();
					} else {
						mainFrame.setVisible(true);
						mainFrame.toFront();
					}
				} else if (AC_QUIT.equals(e.getActionCommand())) {// 退出
					System.exit(0);
				}
			}
		});
		// 为托盘图标加弹出菜单
		trayIcon.setPopupMenu(popupMenu);
	}
}
