package org.mozilla.pike;

import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PluralTesterActivity extends Activity {
	
	private class QuantityNode {
		public String quantity;
		public int from;
		public int to;
		
		public QuantityNode(String quantity, int from) {
			this.quantity = quantity;
			this.from = from;
		}
		
		public String toString() {
			return quantity + " <" + from + ":" + to + ">";
		}
	}

	String[][] locales = {
			{"af", "1"},
			{"ak", "2"},
			{"ar", "12"},
			{"as", "1"},
			{"ast", "1"},
			{"be", "7"},
			{"bg", "1"},
			{"bn-BD", "1"},
			{"bn-IN", "2"},
			{"br", "1"},
			{"bs", "1"},
			{"ca", "1"},
			{"cs", "8"},
			{"csb", "9"},
			{"cy", "1"},
			{"da", "1"},
			{"de", "1"},
			{"el", "1"},
			{"en-GB", "1"},
			{"en-ZA", "1"},
			{"eo", "1"},
			{"es-AR", "1"},
			{"es-CL", "1"},
			{"es-ES", "1"},
			{"es-MX", "1"},
			{"et", "1"},
			{"eu", "1"},
			{"fa", "0"},
			{"fi", "1"},
			{"fr", "2"},
			{"fy-NL", "1"},
			{"ga-IE", "11"},
			{"gd", "4"},
			{"gl", "1"},
			{"gu-IN", "2"},
			{"he", "1"},
			{"hi-IN", "1"},
			{"hr", "7"},
			{"hu", "1"},
			{"hy-AM", "1"},
			{"id", "0"},
			{"is", "15"},
			{"it", "1"},
			//{"ja-JP-mac", "0"},
			{"ja", "0"},
			{"ka", "0"},
			{"kk", "1"},
			{"kn", "2"},
			{"ko", "0"},
			{"ku", "1"},
			{"lg", "1"},
			{"lt", "6"},
			{"lv", "3"},
			{"mai", "1"},
			{"mk", "1"},
			{"ml", "1"},
			{"mn", "1"},
			{"mr", "2"},
			{"nb-NO", "1"},
			{"nl", "1"},
			{"nn-NO", "1"},
			{"nso", "1"},
			{"oc", "2"},
			{"or", "2"},
			{"pa-IN", "1"},
			{"pl", "9"},
			{"pt-BR", "1"},
			{"pt-PT", "2"},
			{"rm", "1"},
			{"ro", "1"},
			{"ru", "7"},
			{"si", "1"},
			{"sk", "8"},
			{"sl", "1"},
			{"son", "1"},
			{"sq", "1"},
			{"sr", "7"},
			{"sv-SE", "1"},
			{"sw", "1"},
			{"ta-LK", "1"},
			{"ta", "2"},
			{"te", "2"},
			{"th", "0"},
			{"tr", "0"},
			{"uk", "7"},
			{"vi", "1"},
			{"x-testing", "1"},
			{"zh-CN", "1"},
			{"zh-TW", "0"},
			{"zu", "1"}
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout lv = new LinearLayout(this);
        lv.setOrientation(LinearLayout.VERTICAL);
        String allContent = " ";
        allContent += Build.HARDWARE + " " + Build.MANUFACTURER + " " + Build.MODEL+ " " + Build.FINGERPRINT + "\n";
        allContent += Build.VERSION.SDK_INT + " " + Build.VERSION.RELEASE + "\n";
        EditText area = new EditText(this);
        area.setSingleLine(false);
        lv.addView(area);
        int localeCount = locales.length;
        for (int loc_i=0; loc_i < localeCount; loc_i++) {
        	Locale locale;
        	String localeCode = locales[loc_i][0];
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
        	allContent += " " + localeCode + "\t" + locales[loc_i][1] + "\t";
        	String quantity;
        	int count;
        	QuantityNode node = null;
        	LinkedList<QuantityNode> quantities = new LinkedList<QuantityNode>();
        	for (count=0; count <= 200; count++) {
        		quantity = res.getQuantityString(R.plurals.numberOfCars, count, count);
        		if (node == null || !node.quantity.equals(quantity)) {
        			if (node != null) {
        				node.to = count - 1;
        				quantities.add(node);
        			}
        			node = new QuantityNode(quantity, count);
        		}
        	}
        	node.to = count - 1;
        	quantities.add(node);
        	String row = quantities.toString();
        	allContent += row + "\n";
        }
        area.setText(allContent);
        setContentView(lv);
    }
}