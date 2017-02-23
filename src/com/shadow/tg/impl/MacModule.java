package com.shadow.tg.impl;

import com.apple.eawt.Application;
import com.apple.eawt.AboutHandler;
import com.apple.eawt.PreferencesHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.shadow.tg.constant.IResourceConst;
import com.shadow.tg.itf.IApplicationModule;
import com.shadow.tg.util.ApplicationUtil;
import com.shadow.tg.util.ResourceUtil;
import com.shadow.tg.util.ResourceUtil.IconSize;

/**
 * Mac系统适配模块
 *
 * @author hippolee
 * @create 2017年2月23日
 * @version 1.0
 */
public class MacModule implements IApplicationModule {

	@Override
	public void initModule() {
		if (!ApplicationUtil.isMac()) {
			return;
		}

		Application application = Application.getApplication();
		application.setDockIconImage(ResourceUtil.getIconImage(IResourceConst.ICON_APPLICATION, IconSize.ICON_96));
		application.setAboutHandler(new AboutHandler() {

			@Override
			public void handleAbout(AboutEvent e) {

			}

		});
		application.setPreferencesHandler(new PreferencesHandler() {

			@Override
			public void handlePreferences(PreferencesEvent arg0) {

			}

		});
	}

}
