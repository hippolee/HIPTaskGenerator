package com.shadow.app.module;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import com.shadow.app.manager.ApplicationManager;
import com.shadow.app.manager.PrintManager;
import com.shadow.app.manager.TaskDataManager;

/**
 * 菜单模块
 *
 * @author hippolee
 * @create 2017年2月24日
 * @version 1.0
 */
public class MenuModule implements IApplicationModule {

	/** MENU_ACTION_CMD_INPUT */
	private static final String MENU_ACTION_CMD_INPUT = "MENU_ACTION_CMD_INPUT";
	/** MENU_ACTION_CMD_OUTPUT */
	private static final String MENU_ACTION_CMD_OUTPUT = "MENU_ACTION_CMD_OUTPUT";
	/** MENU_ACTION_CMD_IO */
	private static final String MENU_ACTION_CMD_IO = "MENU_ACTION_CMD_IO";
	/** MENU_ACTION_CMD_PRINT */
	private static final String MENU_ACTION_CMD_PRINT = "MENU_ACTION_CMD_PRINT";

	private MenuActionListener listener;

	@Override
	public void initModule() {
		// 菜单栏
		MenuBar menuBar = new MenuBar();
		// 菜单-文件
		Menu menu = new Menu("File");
		// 打开Excel
		MenuItem menuItemInput = new MenuItem("Import Excel");
		menuItemInput.setActionCommand(MENU_ACTION_CMD_INPUT);
		menuItemInput.setShortcut(new MenuShortcut(KeyEvent.VK_I, false));
		menuItemInput.addActionListener(getMenuActionListener());
		menu.add(menuItemInput);
		// 导出PPT
		MenuItem menuItemOutput = new MenuItem("Export PPT");
		menuItemOutput.setActionCommand(MENU_ACTION_CMD_OUTPUT);
		menuItemOutput.setShortcut(new MenuShortcut(KeyEvent.VK_E, false));
		menuItemOutput.addActionListener(getMenuActionListener());
		menu.add(menuItemOutput);
		// 读取Excel并打印
		MenuItem menuItemIO = new MenuItem("Import Excel & Print");
		menuItemIO.setActionCommand(MENU_ACTION_CMD_IO);
		menuItemIO.setShortcut(new MenuShortcut(KeyEvent.VK_G, false));
		menuItemIO.addActionListener(getMenuActionListener());
		menu.add(menuItemIO);
		// 打印
		MenuItem menuItemPrint = new MenuItem("Print");
		menuItemPrint.setActionCommand(MENU_ACTION_CMD_PRINT);
		menuItemPrint.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
		menuItemPrint.addActionListener(getMenuActionListener());
		menu.add(menuItemPrint);

		menuBar.add(menu);
		// 菜单-帮助
		Menu helpMenu = new Menu("Help");
		menuBar.setHelpMenu(helpMenu);

		ApplicationManager.getInstance().getMainFrame().setMenuBar(menuBar);
	}

	private ActionListener getMenuActionListener() {
		if (listener == null) {
			listener = new MenuActionListener();
		}
		return listener;
	}

	private class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// action command
			String actionCommand = e.getActionCommand();
			// input
			if (actionCommand.equals(MENU_ACTION_CMD_INPUT)) {
				TaskDataManager.getInstance().importExeclFileDefault();
			} else if (actionCommand.endsWith(MENU_ACTION_CMD_OUTPUT)) {// output
				TaskDataManager.getInstance().exportPowerpointFileDefault();
			} else if (actionCommand.endsWith(MENU_ACTION_CMD_IO)) {// io
				Boolean result = TaskDataManager.getInstance().importExeclFileDefault();
				if (!result) {
					return;
				}
				// TaskDataManager.getInstance().exportPowerpointFileDefault();
				PrintManager.getInstance().printTaskData();
			} else if (actionCommand.endsWith(MENU_ACTION_CMD_PRINT)) {// print
				PrintManager.getInstance().printTaskData();
			}
		}

	}

}
