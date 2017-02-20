package com.shadow.tg.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 鼠标事件处理
 *
 * @author litengfei
 * @create 2017年2月16日
 * @version 1.0
 */
public abstract class HIPMouseListener extends MouseAdapter {

	// 用来判断是否已经执行双击事件
	private static boolean flag = false;

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			final MouseEvent me = e;
			// 标记置否
			flag = false;
			// 定义定时器
			Timer timer = new Timer();
			// 定时器开始执行,延时0.2秒后确定是否执行单击事件
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// 双击未执行
					if (!flag) {
						// 单击事件
						HIPMouseListener.this.mouseSingleClicked(me);
					} else {
						this.cancel();
					}
				}
			}, 200);

		} else if (e.getClickCount() == 2) {
			// 标记已双击
			flag = true;
			// 双击事件
			this.mouseDoubleClicked(e);
		} else {
			return;
		}
	}

	/**
	 * 鼠标单击
	 * 
	 * @param e
	 */
	public void mouseSingleClicked(MouseEvent e) {

	}

	/**
	 * 鼠标双击
	 * 
	 * @param e
	 */
	public void mouseDoubleClicked(MouseEvent e) {

	}
}
