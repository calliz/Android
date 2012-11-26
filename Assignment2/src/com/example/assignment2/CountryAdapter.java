package com.example.assignment2;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CountryAdapter extends ArrayAdapter<Country> {
	private List<Country> values;
	private Activity context;

	public CountryAdapter(Activity context, int textViewResourceId,
			List<Country> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.mycountries_row_layout, null);
		}
		
		Country country = values.get(position);
		
		if(country != null) {
			//
			
		}

		return super.getView(position, convertView, parent);
	}

}
