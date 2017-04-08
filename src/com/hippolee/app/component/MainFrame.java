package com.hippolee.app.component;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import com.hippolee.app.constant.IApplicationConst;
import com.hippolee.app.constant.IResourceConst;
import com.hippolee.app.util.ResourceUtil;
import com.hippolee.app.util.ResourceUtil.IconSize;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() throws HeadlessException {
		super(IApplicationConst.APPLICATION_NAME);
		// 初始化界面
		this.initFrame();
	}

	public MainFrame(GraphicsConfiguration gc) {
		super(IApplicationConst.APPLICATION_NAME, gc);
		// 初始化界面
		this.initFrame();
	}

	public MainFrame(String title) throws HeadlessException {
		super(title);
		// 初始化界面
		this.initFrame();
	}

	public MainFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// 初始化界面
		this.initFrame();
	}

	private void initFrame() {
		// 窗口大小
		this.setSize(800, 600);
		// 居中
		this.setLocationRelativeTo(null);
		// 图标
		this.setIconImage(ResourceUtil.getIconImage(IResourceConst.ICON_APPLICATION, IconSize.ICON_48));
		// 设置关闭按钮
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 自动请求焦点
		this.setAutoRequestFocus(true);
	}

}
