package com.jmpr.asteroides.activities;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.views.ViewGame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity {
	private ViewGame viewGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		viewGame = (ViewGame) findViewById(R.id.gameView);
		
		// Set this activity as parent in game view (necessary for returning the player score)
		viewGame.setParentActivity(this);
	}

	@Override
	protected void onPause() {
		Log.d("debug", "Game activity onPause()");
		
		super.onPause();
		
		// Pauses the physics thread when the Activity loses focus
		viewGame.getThread().pause();
	}

	@Override
	protected void onResume() {		
		Log.d("debug", "Game activity onResume()");
		
		super.onResume();
		
		// Resumes the physics thread when the Activity is resumed
		viewGame.getThread().resume2();
		
		// Resumes sensors
		viewGame.activateSensors();
	}

	@Override
	protected void onStop() {
		Log.d("debug", "Game activity onStop()");
		
		super.onStop();

		// Stops the physics thread when the Activity loses focus
		viewGame.getThread().pause();
		
		// Stop sensors
		viewGame.deactivateSensors();		
	}

	@Override
	protected void onStart() {
		Log.d("debug", "Game activity onStart()");
		
		super.onStart();
		
		// Activates sensors
		viewGame.activateSensors();
	}

	@Override
	protected void onDestroy() {		
		Log.d("debug", "Game activity onDestroy()");
		
		super.onDestroy();
		
		// Stop sensors
		viewGame.deactivateSensors();
		
		// Destroys the physics thread when the Activity is destroyed
		viewGame.getThread().stop2();
	}
}
