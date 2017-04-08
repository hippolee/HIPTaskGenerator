package com.hippolee.app.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.AppForegroundEvent;
import com.apple.eawt.AppEvent.AppHiddenEvent;
import com.apple.eawt.AppEvent.AppReOpenedEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.hippolee.app.constant.IResourceConst;
import com.hippolee.app.manager.ApplicationManager;
import com.hippolee.app.util.ApplicationUtil;
import com.hippolee.app.util.ResourceUtil;
import com.hippolee.app.util.ResourceUtil.IconSize;
import com.apple.eawt.AppEventListener;
import com.apple.eawt.AppReOpenedListener;
import com.apple.eawt.AppForegroundListener;
import com.apple.eawt.AppHiddenListener;
import com.apple.eawt.Application;
import com.apple.eawt.PreferencesHandler;

/**
 * Mac系统适配模块
 *
 * @author hippolee
 * @create 2017年2月23日
 * @version 1.0
 */
public class MacModule implements IApplicationModule {

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(MacModule.class);

	@Override
	public void initModule() {
		if (!ApplicationUtil.isMac()) {
			return;
		}

		Application application = Application.getApplication();
		// dock
		application.setDockIconImage(ResourceUtil.getIconImage(IResourceConst.ICON_APPLICATION, IconSize.ICON_96));
		// about
		application.setAboutHandler(new AboutHandler() {

			@Override
			public void handleAbout(AboutEvent e) {

			}

		});
		// preference
		application.setPreferencesHandler(new PreferencesHandler() {

			@Override
			public void handlePreferences(PreferencesEvent arg0) {

			}

		});

		// application.requestForeground(true);
		application.addAppEventListener(new AppEventListener() {

		});
		// AppForegroundListener
		application.addAppEventListener(new AppForegroundListener() {

			@Override
			public void appMovedToBackground(AppForegroundEvent arg0) {
				logger.debug("appMovedToBackground");
			}

			@Override
			public void appRaisedToForeground(AppForegroundEvent arg0) {
				logger.debug("appRaisedToForeground");
			}

		});
		// AppHiddenListener
		application.addAppEventListener(new AppHiddenListener() {

			@Override
			public void appHidden(AppHiddenEvent arg0) {
				logger.debug("appHidden");
			}

			@Override
			public void appUnhidden(AppHiddenEvent arg0) {
				logger.debug("appUnhidden");
			}

		});
		// AppReOpenedListener
		application.addAppEventListener(new AppReOpenedListener() {

			@Override
			public void appReOpened(AppReOpenedEvent arg0) {
				ApplicationManager.getInstance().getMainFrame().setVisible(true);
			}

		});
	}

}
