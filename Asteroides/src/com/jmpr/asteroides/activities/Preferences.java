package com.jmpr.asteroides.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.jmpr.asteroides.R;

public class Preferences extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		// Adds the preferences screen
		// Deprecated method actually does not have an alternative 
		// (http://stackoverflow.com/questions/6822319/what-to-use-instead-of-addpreferencesfromresource-in-a-preferenceactivity)
		addPreferencesFromResource(R.xml.preferences);
	}

}
