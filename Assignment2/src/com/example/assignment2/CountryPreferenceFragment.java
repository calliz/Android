package com.example.assignment2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class CountryPreferenceFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}