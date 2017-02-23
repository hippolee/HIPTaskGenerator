package com.shadow.tg.impl;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;


public class MenuModule {

	public static void genMenu(JFrame mainFrame) {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem menuItem = new MenuItem("Open Excel");
		menu.add(menuItem);
		menuBar.add(menu);
		Menu helpMenu = new Menu("Help");
		menuBar.setHelpMenu(helpMenu);
		
		mainFrame.setMenuBar(menuBar);
	}

}
