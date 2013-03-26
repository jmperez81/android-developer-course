package com.jmpr.asteroides.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.util.ScoreBoard;
import com.jmpr.asteroides.util.ScoreBoardArray;

public class Asteroids extends Activity {
	private Button aboutButton;
	private Button exitButton;
	public static ScoreBoard scoreBoard = new ScoreBoardArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// This is an alternate way to establish a Listener. Allows not only
		// click events
		aboutButton = (Button) findViewById(R.id.mainButtonAbout);
		aboutButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				launchAbout(null);
			}
		});

		// Listener for Exit button
		exitButton = (Button) findViewById(R.id.mainButtonExit);
		exitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				launchExit(null);
			}
		});
	}

	/**
	 * This method creates the menu in the view
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Adds the specified menu to the activity
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * This method processes the item menu selection
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			launchAbout(null);
			break;
		case R.id.config:
			launchPreferences(null);
			break;
		}

		// We consume the item, don't propagate
		return true;
	}

	/**
	 * Launches the About activity
	 * 
	 * @param view
	 */
	public void launchAbout(View view) {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}

	/**
	 * Launches the Preferences activity
	 * 
	 * @param view
	 */
	public void launchPreferences(View view) {
		Intent intent = new Intent(this, Preferences.class);
		startActivity(intent);
	}

	/**
	 * Exits from the application
	 * 
	 * @param view
	 */
	public void launchExit(View view) {
		finish();
	}
	
	/**
	 * Launches the ScoreBoard activity
	 * @param view
	 */
	public void launchScoreBoard(View view) {
		Intent intent = new Intent(this, Scores.class);
		startActivity(intent);
	}
	
	/**
	 * Launches the game activity
	 * @param view
	 */
	public void launchGame(View view) {
		Intent intent = new Intent(this, Game.class);
		startActivity(intent);
	}
}
