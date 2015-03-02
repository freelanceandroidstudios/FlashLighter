package com.flashlighter.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SoundPreferences {

	private SharedPreferences preferences;
	private final String SOUND = "sound";

	public SoundPreferences(Context context) {
		setPreferences(PreferenceManager.getDefaultSharedPreferences(context
				.getApplicationContext()));
	}

	public boolean isSoundOn() {
		return getPreferences().getBoolean(SOUND, true);
	}

	public void setSoundPreference(boolean state) {
		getPreferences().edit().putBoolean(SOUND, state).apply();
	}

	/**
	 * @return the preferences
	 */
	public SharedPreferences getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences
	 *            the preferences to set
	 */
	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

}
