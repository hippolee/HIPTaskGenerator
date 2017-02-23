package com.shadow.tg.util;

/**
 * 应用工具类
 *
 * @author hippolee
 * @create 2017年2月23日
 * @version 1.0
 */
public class ApplicationUtil {

	/** 系统名称 */
	private static String OSName = System.getProperty("os.name").toLowerCase();

	/**
	 * 系统平台信息
	 *
	 * @author hippolee
	 * @create 2017年2月23日
	 * @version 1.0
	 */
	public enum PlatformEnum {

		PLATFORM_WINDOWS("Windows"), PLATFORM_LINUX("Linux"), PLATFORM_MAC("Mac OS"), PLATFORM_OTHERS("Others");

		private String description;

		private PlatformEnum(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	/**
	 * 获得当前系统平台
	 * 
	 * @return
	 * @see http://lopica.sourceforge.NET/os.html
	 */
	public static PlatformEnum getPlatform() {
		if (isWindows()) {
			return PlatformEnum.PLATFORM_WINDOWS;
		} else if (isMac()) {
			return PlatformEnum.PLATFORM_MAC;
		} else if (isLinux()) {
			return PlatformEnum.PLATFORM_LINUX;
		} else {
			return PlatformEnum.PLATFORM_OTHERS;
		}
	}

	/**
	 * Linux
	 * 
	 * @return
	 */
	public static boolean isLinux() {
		return OSName.indexOf("linux") >= 0;
	}

	/**
	 * Mac OS(7.x 8.x 9.x)
	 * 
	 * @return
	 */
	public static boolean isMacOS() {
		return OSName.indexOf("mac") >= 0 && OSName.indexOf("os") > 0 && OSName.indexOf("x") < 0;
	}

	/**
	 * MAC OS X(10.x)
	 * 
	 * @return
	 */
	public static boolean isMacOSX() {
		return OSName.indexOf("mac") >= 0 && OSName.indexOf("os") > 0 && OSName.indexOf("x") > 0;
	}

	/**
	 * MAC OS
	 * 
	 * @return
	 */
	public static boolean isMac() {
		return OSName.indexOf("mac") >= 0 && OSName.indexOf("os") > 0;
	}

	/**
	 * Windows
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		return OSName.indexOf("windows") >= 0;
	}

}
