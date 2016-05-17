package com.seancheey;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "com.seancheey.messages"; //$NON-NLS-1$
	public static final Locale zh_CN = new Locale("zh", "CN"), en_US = new Locale("en", "US");

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static Locale locale = en_US;

	public static Locale getLocale() {
		return locale;
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getComponentString(String key) {
		key = key.replace(' ', '_');
		return getString(key);
	}

	public static void setLocale(Locale local) {
		locale = local;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	private Messages() {
	}
}
