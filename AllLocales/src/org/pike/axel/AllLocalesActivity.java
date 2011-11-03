package org.pike.axel;

import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AllLocalesActivity extends Activity {
	String[] mAllLocales;
	int mLocale;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        mAllLocales = res.getStringArray(R.array.locales);
        mLocale = 0;
        doLayout();
    }
    private void doLayout() {
        Locale locale;
        String localeCode = mAllLocales[mLocale];
        int index;
        if ((index = localeCode.indexOf('-')) != -1) {
                String langCode = localeCode.substring(0, index);
                String countryCode = localeCode.substring(index + 1);
                locale = new Locale(langCode, countryCode);
        } else {
                locale = new Locale(localeCode);
        }
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.locale);
        tv.setText(mAllLocales[mLocale]);
    }
    public void onBack(View v) {
    	if (mLocale > 0) {
    		mLocale--;
    		doLayout();
    	}
    }
    public void onForward(View v)
    {
    	if (mLocale < mAllLocales.length - 1) {
    		mLocale++;
    		doLayout();
    	}
    }
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		for (int i = 0; i < mAllLocales.length; i++) {
			menu.add(mAllLocales[i]);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String t = (String)item.getTitle();
		int i = Arrays.binarySearch(mAllLocales, t);
		mLocale = i;
		doLayout();
		return super.onOptionsItemSelected(item);
	}

    
}