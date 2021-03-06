package org.mariotaku.twidere.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

import org.mariotaku.twidere.Constants;
import org.mariotaku.twidere.R;
import org.mariotaku.twidere.service.NyanDaydreamService;
import org.mariotaku.twidere.service.NyanWallpaperService;
import org.mariotaku.twidere.util.NyanSurfaceHelper;

public class NyanActivity extends Activity implements Constants, OnLongClickListener, OnSharedPreferenceChangeListener {

	private SurfaceView mSurfaceView;
	private SharedPreferences mPreferences;
	private NyanSurfaceHelper mHelper;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mSurfaceView = (SurfaceView) findViewById(R.id.surface);
	}

	@Override
	public boolean onLongClick(final View v) {
		Toast.makeText(this, R.string.nyan_sakamoto, Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
		if (PREFERENCE_KEY_LIVE_WALLPAPER_SCALE.equals(key)) {
			updateSurface();
		}
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
		mPreferences.registerOnSharedPreferenceChangeListener(this);
		setContentView(R.layout.surface_view);
		mSurfaceView.setOnLongClickListener(this);
		final SurfaceHolder holder = mSurfaceView.getHolder();
		mHelper = new NyanSurfaceHelper(this, holder);
		updateSurface();
		enableWallpaperDaydream();
	}

	private void enableWallpaperDaydream() {
		final PackageManager pm = getPackageManager();
		final ComponentName wallpaperComponent = new ComponentName(this, NyanWallpaperService.class);
		final int wallpaperState = pm.getComponentEnabledSetting(wallpaperComponent);
		boolean showToast = false;
		if (wallpaperState != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			pm.setComponentEnabledSetting(wallpaperComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
			showToast = true;
		}
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
			final ComponentName daydreamComponent = new ComponentName(this, NyanDaydreamService.class);
			final int daydreamState = pm.getComponentEnabledSetting(daydreamComponent);
			if (daydreamState != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
				pm.setComponentEnabledSetting(daydreamComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
						PackageManager.DONT_KILL_APP);
				showToast = true;
			}
		}
		if (showToast) {
			Toast.makeText(this, R.string.livewp_daydream_enabled_message, Toast.LENGTH_SHORT).show();
		}
	}

	private void updateSurface() {
		if (mPreferences == null) return;
		final Resources res = getResources();
		final int def = res.getInteger(R.integer.default_live_wallpaper_scale);
		mHelper.setScale(mPreferences.getInt(PREFERENCE_KEY_LIVE_WALLPAPER_SCALE, def));
	}

}
