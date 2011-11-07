package org.pike.axel;

import org.mozilla.l20n.Localization;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class PostCreateActivity extends Activity {
	Localization mLocalization;
	String mLocale = "";
	boolean mNeedsMenu;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLocale("en");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mNeedsMenu = true;
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.layout.menu, menu);
		mLocalization.localizeMenu(menu);
		mNeedsMenu = false;
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		String oldLocale = mLocale;
		switch (id) {
		case R.id.menu_de: setLocale("de"); break;
		case R.id.menu_en: setLocale("en"); break;
		}
		if (mLocale != oldLocale) {
			View v = getWindow().getDecorView();
			mLocalization.localizeView(v);
			mNeedsMenu = true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mNeedsMenu) {
			mLocalization.localizeMenu(menu);
			mNeedsMenu = true;
		}
		return super.onPrepareOptionsMenu(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		View v = getWindow().getDecorView();
		mLocalization.localizeView(v);
	}
	
	private void setLocale(String loc) {
    	mLocalization = null;
		try {
			mLocalization = (Localization)Class.forName("org.pike.axel." + loc).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mLocale = loc;
	}
}