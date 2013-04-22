package com.jmpr.asteroides.views;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.drawables.CustomGraphic;

public class ViewGame extends View implements SensorEventListener {
	private Context context;
	
	private Vector<CustomGraphic> asteroids; // Set of current asteroids
	private int initialAsteroids = 5; // Initial number of asteroids
	private int fragmentsPerAsteroid = 3; // Fragments to divide an asteroid
											// when it is destroyed
	private CustomGraphic ship; // Ship graphic
	private int shipSteer; // Direction increment
	private float shipAcceleration; // Ship speed increment

	// Touch screen handling
	private float mX = 0, mY = 0;
	private boolean shot = false;
	private final int SENSITIVITY_Y = 6;
	private final int SENSITIVITY_X = 6;
	private final int ACCELERATION_FACTOR_MIN = 2;
	private final int ACCELERATION_FACTOR_MAX = 25;

	// Missile
	private CustomGraphic missile;
	private static int STEP_MISSILE_SPEED = 12;
	private boolean missileActive = false;
	private int missileTime;

	// Graphics type (should be read from preferences, fixed right now
	int graphicsType = 0;

	// Sensor management
	private boolean existInitValue = false;
	private float initValue;
	SensorManager mSensorManager;
	
	// Score
	private int totalScore = 0;
	
	// Auxiliary activity for sending score
	private Activity parentActivity;
	 
	public void setParentActivity(Activity padre) {
	      this.parentActivity = padre;
	}
	
	// ***********************************
	// Threads
	// ***********************************
	// Process interval (millis)
	private static int INTERVAL_PROCESS = 50;
	// Last processment time
	private long lastProcessmentTime = 0;
	// Main thread for the game
	private MainThread thread = new MainThread();

	// Standard steer and acceleration increment
	private static final int SHIP_STEER_STEP = 5;
	private static final float SHIP_ACC_STEP = 0.5f;

	// Constructor
	public ViewGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		Drawable drawableShip, drawableAsteroid, drawableMissile;
		drawableAsteroid = context.getResources().getDrawable(
				R.drawable.asteroid1);
		drawableShip = context.getResources().getDrawable(R.drawable.ship);
		ship = new CustomGraphic(this, drawableShip);
		asteroids = new Vector<CustomGraphic>();

		for (int i = 0; i < initialAsteroids; i++) {
			CustomGraphic asteroide = new CustomGraphic(this, drawableAsteroid);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setAngle((int) (Math.random() * 360));
			asteroide.setRotation((int) (Math.random() * 8 - 4));
			asteroids.add(asteroide);
		}

		// Missile
		if (graphicsType == 0) {
			ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
			dMisil.getPaint().setColor(Color.WHITE);
			dMisil.getPaint().setStyle(Style.STROKE);
			dMisil.setIntrinsicWidth(15);
			dMisil.setIntrinsicHeight(3);
			drawableMissile = dMisil;
		} else {
			drawableMissile = context.getResources().getDrawable(
					R.drawable.ship);
		}

		missile = new CustomGraphic(this, drawableMissile);
	}

	public MainThread getThread() {
		return thread;
	}

	@Override
	synchronized protected void onSizeChanged(int width, int height,
			int prevWidth, int prevHeight) {

		super.onSizeChanged(width, height, prevWidth, prevHeight);

		// Ship is placed in the middle
		ship.setPosX((width - ship.getWidth()) / 2);
		ship.setPosY((height - ship.getHeight()) / 2);

		// We adjust the position according to the screen dimensions, once we
		// know them. We ensure that no asteroid is close enough to the initial
		// ship position
		for (CustomGraphic asteroid : asteroids) {
			do {
				asteroid.setPosX(Math.random() * (width - asteroid.getWidth()));
				asteroid.setPosY(Math.random()
						* (height - asteroid.getHeight()));
			} while (asteroid.getDistanceTo(ship) < (width + height) / 5);
		}

		lastProcessmentTime = System.currentTimeMillis();
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (CustomGraphic asteroide : asteroids) {
			asteroide.renderGraphic(canvas);
		}

		ship.renderGraphic(canvas);

		// Draw missile only if it is active
		if (missileActive) {
			missile.renderGraphic(canvas);
		}
	}

	synchronized protected void updatePhysics() {
		long now = System.currentTimeMillis();

		// Do nothing until the next processment time is due
		if (lastProcessmentTime + INTERVAL_PROCESS > now) {
			return;
		}

		double delay = (now - lastProcessmentTime) / INTERVAL_PROCESS;
		lastProcessmentTime = now;

		// Ship speed and direction update depending on shipSteer and
		// shipAcceleration
		ship.setAngle((int) (ship.getAngle() + shipSteer * delay));
		double nIncX = ship.getIncX() + shipAcceleration
				* Math.cos(Math.toRadians(ship.getAngle())) * delay;
		double nIncY = ship.getIncY() + shipAcceleration
				* Math.sin(Math.toRadians(ship.getAngle())) * delay;

		// Only updates when speed modulus don't exceed maximum value
		if (Math.hypot(nIncX, nIncY) <= CustomGraphic.MAX_SPEED) {
			ship.setIncX(nIncX);
			ship.setIncY(nIncY);
		}

		// update of X and Y position
		ship.updatePosition(delay);
		for (CustomGraphic asteroid : asteroids) {
			asteroid.updatePosition(delay);
		}

		// Missile behaviour
		if (missileActive) {
			missile.updatePosition(delay);
			missileTime -= delay;
			if (missileTime < 0) {
				missileActive = false;
			} else {
				for (int i = 0; i < asteroids.size(); i++)
					if (missile.checkCollision(asteroids.elementAt(i))) {
						destroyAsteroid(i);
						break;
					}
			}
		}
		
		// Player loses
		for (CustomGraphic asteroid : asteroids) {
		    if (asteroid.checkCollision(ship)) {
		       gameOver();
		    }
		}
	}

	private void activateMissile() {
		missile.setPosX(ship.getPosX() + ship.getWidth() / 2
				- missile.getWidth() / 2);
		missile.setPosY(ship.getPosY() + ship.getHeight() / 2
				- missile.getHeight() / 2);
		missile.setAngle(ship.getAngle());
		missile.setIncX(Math.cos(Math.toRadians(missile.getAngle()))
				* STEP_MISSILE_SPEED);
		missile.setIncY(Math.sin(Math.toRadians(missile.getAngle()))
				* STEP_MISSILE_SPEED);
		missileTime = (int) Math.min(
				this.getWidth() / Math.abs(missile.getIncX()), this.getHeight()
						/ Math.abs(missile.getIncY())) - 2;
		missileActive = true;
	}

	private void destroyAsteroid(int i) {
		totalScore+=1000;
		asteroids.remove(i);
		missileActive = false;
		
		// Player wins
		if (asteroids.isEmpty()) {
			gameOver();
		}
	}

	public class MainThread extends Thread {
		private boolean paused, running;

		public synchronized void pause() {
			paused = true;
		}

		public synchronized void resume2() {
			paused = false;
			notify();
		}

		public void stop2() {
			running = false;
			if (paused)
				resume2();
		}

		@Override
		public void run() {
			running = true;
			while (running) {
				updatePhysics();
				synchronized (this) {
					while (paused) {
						try {
							wait();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Nothing done

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float value = event.values[1];
		if (!existInitValue) {
			initValue = value;
			existInitValue = true;
		}
		shipSteer = (int) (value - initValue) / 3;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			shot = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dy < SENSITIVITY_Y && dx > SENSITIVITY_X) {
				shipSteer = Math.round((x - mX) / ACCELERATION_FACTOR_MIN);
				shot = false;
			} else if (dx < SENSITIVITY_X && dy > SENSITIVITY_Y) {
				if ((mY - y) > 0) {
					Log.d("Asteroids", "Acceleration modified : " + (mY - y));
					shipAcceleration = Math.round((mY - y)
							/ ACCELERATION_FACTOR_MAX);
				} else {
					Log.d("Asteroids", "Acceleration not modified");
				}
				shot = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			shipSteer = 0;
			shipAcceleration = 0;
			if (shot) {
				activateMissile();
			}
			break;
		}
		mX = x;
		mY = y;
		return true;
	}
	
	public void deactivateSensors() {
		mSensorManager.unregisterListener(this);
		Log.d("debug", "Sensors deactivated");
	}
	
	public void activateSensors() {				
		// Sensor registration
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> listSensors = mSensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			mSensorManager.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
		
		Log.d("debug", "Sensors activated");
	}

	private void gameOver() {
	    Bundle bundle = new Bundle();
	    bundle.putInt("score", totalScore);
	    Intent intent = new Intent();
	    intent.putExtras(bundle);
	    parentActivity.setResult(Activity.RESULT_OK, intent);
	    parentActivity.finish();
	}
}
