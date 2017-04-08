package com.hippolee.app.util;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import com.hippolee.app.constant.IResourceConst;

/**
 * 资源加载工具类
 *
 * @author hippolee
 * @create 2017年2月23日
 * @version 1.0
 */
public class ResourceUtil implements IResourceConst {

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
	}

	/**
	 * 获取资源名
	 * 
	 * @param prefix
	 * @return
	 */
	public static String getIconSrcName(String iconName, IconSize iconSize) {
		return iconName + "_" + iconSize.getSize() + ICON_SUFFIX_PNG;
	}

	/**
	 * 根据尺寸获得应用图标
	 * 
	 * @param iconName
	 * @param iconSize
	 * @return
	 */
	public static Image getIconImage(String iconName, IconSize iconSize) {
		String iconFileName = getIconSrcName(iconName, iconSize);
		URL url = ClassLoader.getSystemClassLoader().getResource("img/" + iconFileName);
		ImageIcon imageIcon = new ImageIcon(url);
		return imageIcon.getImage();
	}

	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	public static String getConfigDir() {
		String userDir = getUserDir();
		String configDir = userDir + File.separator + "config";
		File file = new File(configDir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		return configDir;
	}

	public static String getResourceDir() {
		String userDir = getUserDir();
		String configDir = userDir + File.separator + "config";
		File file = new File(configDir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		return configDir;
	}

}
