package org.pike.axel;

import org.mozilla.l20n.Localization;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class en implements Localization {

	@Override
	public void localizeView(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.text_label: {
			((TextView)v).setText("good stuff");
			break;
		}
		}
		if (v instanceof ViewGroup) {
			ViewGroup container = (ViewGroup)v;
			int childCount = container.getChildCount();
			for (int i = 0; i < childCount; i++) {
				localizeView(container.getChildAt(i));
			}
		}
	}

	@Override
	public void localizeMenu(Menu menu) {
		int size = menu.size();
		for (int i = 0; i < size; i++) {
			MenuItem mi = menu.getItem(i);
			int id = mi.getItemId();
			switch (id) {
			case R.id.menu_en: {
				mi.setTitle("English");
				break;
			}
			case R.id.menu_de: {
				mi.setTitle("German");
				break;
			}
			}
		}
	}

}
