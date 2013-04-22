package com.jmpr.asteroides.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.customizations.CustomAdapter;

public class Scores extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Stablish the view
		 */
		setContentView(R.layout.scoreboard);

		// Option 1 : Built-in layout with adapter for ListView
		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1,
		// Asteroids.scoreBoard.getScores(10)));

		// Option 2 : Custom layout with adapter for ListView
		// setListAdapter(
		// new ArrayAdapter<String>(this,
		// R.layout.listviewelement,
		// R.id.listViewText,
		// Asteroids.scoreBoard.getScores(10)));

		// Option 3 : Custom Adapter
		setListAdapter(new CustomAdapter(this,
				Asteroids.scoreBoard.getScores(10)));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Object o = getListAdapter().getItem(position);
		Toast.makeText(
				this,
				"You have clicked : " + Integer.toString(position) + " - "
						+ o.toString(), Toast.LENGTH_LONG).show();
	}

}
