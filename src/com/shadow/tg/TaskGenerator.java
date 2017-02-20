package com.shadow.tg;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务生成器
 *
 * @author hippolee
 * @create 2017年2月16日
 * @version 1.0
 */
public class TaskGenerator {

	// Logger
	public static Logger logger = LoggerFactory.getLogger(TaskGenerator.class);

	public static void main(String[] args) {

		JFrame mainFrame = new JFrame("TaskGenerator");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridLayout());

		mainFrame.setTitle("任务生成器");

		ImageIcon imageIcon = new ImageIcon("res/img/icon.png");
		mainFrame.setIconImage(imageIcon.getImage());

		MenuBar menuBar = new MenuBar();
		Menu menu2 = new Menu("b");
		menuBar.add(menu2);
		mainFrame.setMenuBar(menuBar);

		// 窗口最小化时软件dispose

		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			// 图标化窗口时调用事件
			public void windowIconified(WindowEvent e) {
				super.windowIconified(e);
				mainFrame.setExtendedState(Frame.ICONIFIED);
			}

			@Override
			// 关闭窗口时调用事件
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// 窗口最小化时dispose该窗口
				mainFrame.dispose();
			}

		});

		if (SystemTray.isSupported()) {
			// 获得系统托盘的实例
			SystemTray systemTray = SystemTray.getSystemTray();
			// 设置托盘的图标
			TrayIcon trayIcon = new TrayIcon(imageIcon.getImage(), "任务生成器");
			trayIcon.setImageAutoSize(true);
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
				logger.error("设置系统托盘图标失败");
			}

			// 创建弹出菜单
			PopupMenu popupMenu = new PopupMenu();
			popupMenu.add(new MenuItem("显示/隐藏主窗口"));
			popupMenu.addSeparator();
			popupMenu.add(new MenuItem("退出"));

			// 为托盘图标加弹出菜弹
			trayIcon.setPopupMenu(popupMenu);
		}

		mainFrame.setVisible(true);
	}

}
