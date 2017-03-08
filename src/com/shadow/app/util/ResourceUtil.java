package com.shadow.app.util;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import com.shadow.app.constant.IResourceConst;

/**
 * 资源加载工具类
 *
 * @author hippolee
 * @create 2017年2月23日
 * @version 1.0
 */
public class ResourceUtil {

	/** 图标尺寸 */
	public enum IconSize {

		ICON_256(256), // 256 * 256
		ICON_128(128), // 128 * 128
		ICON_96(96), // 96 * 96
		ICON_48(48); // 48 * 48

		/** 图标尺寸 */
		private int size;

		private IconSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		/**
		 * 获取资源名
		 * 
		 * @param prefix
		 * @return
		 */
		public String getIconSrcName(String prefix) {
			return prefix + "_" + getSize() + IResourceConst.ICON_SUFFIX_PNG;
		}

	}

	/**
	 * 根据尺寸获得应用图标
	 * 
	 * @param iconName
	 * @param iconSize
	 * @return
	 */
	public static Image getIconImage(String iconName, IconSize iconSize) {
		ImageIcon imageIcon = new ImageIcon(IResourceConst.ICON_RES_PATH + iconName + File.separator + iconSize.getIconSrcName(iconName));
		return imageIcon.getImage();
	}

}
