/*
 *				Twidere - Twitter client for Android
 * 
 * Copyright (C) 2012 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

public class LicenseFragment extends WebViewFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadUrl("file:///android_asset/gpl-3.0-standalone.html");
		setWebViewClient(new LicenseWebViewClient(getActivity()));

	}

	private class LicenseWebViewClient extends DefaultWebViewClient {

		private FragmentActivity mActivity;

		public LicenseWebViewClient(FragmentActivity activity) {
			super(activity);
			mActivity = activity;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			mActivity.setTitle(view.getTitle());
			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			return true;
		}
	}
}
