package org.mariotaku.twidere.service;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import org.mariotaku.twidere.Constants;
import org.mariotaku.twidere.R;
import org.mariotaku.twidere.util.NyanSurfaceHelper;

public class NyanWallpaperService extends WallpaperService implements Constants, OnSharedPreferenceChangeListener {

	private NyanSurfaceHelper mHelper;
	private SharedPreferences mPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		mPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
		mPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public Engine onCreateEngine() {
		final Engine engine = new Engine();
		final SurfaceHolder holder = engine.getSurfaceHolder();
		mHelper = new NyanSurfaceHelper(this, holder);
		updateSurface();
		return engine;
	}

	@Override
	public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
		if (PREFERENCE_KEY_LIVE_WALLPAPER_SCALE.equals(key)) {
			updateSurface();
		}
	}

	private void updateSurface() {
		if (mPreferences == null) return;
		final Resources res = getResources();
		final int def = res.getInteger(R.integer.default_live_wallpaper_scale);
		mHelper.setScale(mPreferences.getInt(PREFERENCE_KEY_LIVE_WALLPAPER_SCALE, def));
	}

}
