package com.example.assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainList extends ListActivity {
	private List<String> activities = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	private Map<String, Class> name2class = new HashMap<String, Class>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Add Activities to list */
		setup_activities();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.main_list_item,
				activities));

		/* Attach list item listener */
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClick());
	}

	/* Private Help Entities */
	private class OnItemClick implements OnItemClickListener {
		@SuppressWarnings("rawtypes")
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			/* Find selected activity */
			String activity_name = activities.get(position);
			Class activity_class = name2class.get(activity_name);

			/* Start new Activity */
			Intent intent = new Intent(MainList.this, activity_class);
			MainList.this.startActivity(intent);
		}
	}

	/* Diagnostics while developing */
	@SuppressWarnings("unused")
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void setup_activities() {
		addActivity("Random", Random.class);
		addActivity("BMI", BMI.class);
		addActivity("ColorDisplay", ColorDisplay.class);
		addActivity("MyCountries", MyCountries.class);
	}

	@SuppressWarnings("rawtypes")
	private void addActivity(String name, Class activity) {
		activities.add(name);
		name2class.put(name, activity);
	}
}
