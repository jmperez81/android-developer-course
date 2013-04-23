package com.jmpr.asteroides.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.services.MusicService;
import com.jmpr.asteroides.util.ScoreBoard;
import com.jmpr.asteroides.util.ScoreBoardArray;
import com.jmpr.asteroides.util.ScoreBoardDatabase;
import com.jmpr.asteroides.util.ScoreBoardExternalFile;
import com.jmpr.asteroides.util.ScoreBoardInternalFile;
import com.jmpr.asteroides.util.ScoreBoardPreferences;
import com.jmpr.asteroides.util.ScoreBoardRawFile;
import com.jmpr.asteroides.util.ScoreBoardXmlSAX;

public class Asteroids extends Activity {
	public static ScoreBoard scoreBoard;

	private Button aboutButton;
	private Button exitButton;

	// private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Floating message
		// Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

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

		// Loads midi file (not necessary because we have a service to do that)
		// mp = MediaPlayer.create(this, R.raw.audio);
		// mp.start();

		// Launch music service
		startService(new Intent(Asteroids.this, MusicService.class));

		// Initializes scoreboard depending on option selected
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		initializeScoreboard(prefs);

		// Register a listener for configuration changes		
		SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefes,
					String key) {
				Log.d("Asteroids",key);
				if (key.equals("storage")) {
					initializeScoreboard(prefes);
				} else if (key.equals("music")) {								
					if (prefes.getBoolean("music", false)) {
						// Stops music service
						startService(new Intent(Asteroids.this, MusicService.class));
					} else {
						// Stops music service
						stopService(new Intent(Asteroids.this, MusicService.class));
					}
				}
			}
		};
		prefs.registerOnSharedPreferenceChangeListener(prefListener);
	}

	private void initializeScoreboard(SharedPreferences prefs) {
		String storageSelected = prefs.getString("storage", "0");

		
		if ("0".equals(storageSelected)) {
			scoreBoard = new ScoreBoardArray(); 			// Array version
		} else if ("1".equals(storageSelected)) {
			scoreBoard = new ScoreBoardPreferences(this); 	// Preferences version
		} else if ("2".equals(storageSelected)) {
			scoreBoard = new ScoreBoardInternalFile(this); 	// File in phone
															// memory version
		} else if ("3".equals(storageSelected)) {
			scoreBoard = new ScoreBoardExternalFile(this); 	// File in external
															// storage
		} else if ("4".equals(storageSelected)) {
			scoreBoard = new ScoreBoardRawFile(this);		// Raw file (read only)
		} else if ("5".equals(storageSelected)) {
			scoreBoard = new ScoreBoardXmlSAX(this);		// XML file (read only)
		} else if ("6".equals(storageSelected)) {
			scoreBoard = new ScoreBoardDatabase(this);		// SQLiteDatabase
		}
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
	 * 
	 * @param view
	 */
	public void launchScoreBoard(View view) {
		Intent intent = new Intent(this, Scores.class);
		startActivity(intent);
	}

	/**
	 * Launches the game activity
	 * 
	 * @param view
	 */
	public void launchGame(View view) {
		Intent intent = new Intent(this, Game.class);
		startActivityForResult(intent, 1234);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1234 & resultCode == RESULT_OK & data != null) {
			int score = data.getExtras().getInt("score");
			String name = "Player";
			// Mejor leerlo desde un Dialog o una nueva actividad
			// AlertDialog.Builder
			scoreBoard.storeNewScore(score, name, System.currentTimeMillis());
			launchScoreBoard(null);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d("debug", "Asteroids activity onResume()");

		// Resumes music
		// mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("debug", "Asteroids activity onPause()");

		// Stops music
		// mp.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.d("debug", "Asteroids activity onDestroy()");

		// Stops music
		// mp.stop();

		// Stops music service
		stopService(new Intent(Asteroids.this, MusicService.class));
	}

	@Override
	protected void onStop() {
		super.onStop();

		Log.d("debug", "Asteroids activity onStop()");

		// Pauses music
		// mp.pause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		Log.d("debug", "Asteroids activity onRestart()");

		// Resumes music
		// mp.start();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.d("debug", "Asteroids activity onStart()");

		// Starts music
		// mp.start();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d("debug", "Restoring saved activity state");
		// mp.seekTo(savedInstanceState.getInt("musicLastPosition"));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("debug", "Saving activity state");
		// outState.putInt("musicLastPosition", mp.getCurrentPosition());
	}

}
