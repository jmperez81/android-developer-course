package com.jmpr.asteroides.views;

import java.util.Vector;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.drawables.CustomGraphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class ViewGame extends View {
	private Vector<CustomGraphic> asteroids; 	// Set of current asteroids
	private int initialAsteroids = 5; 			// Initial number of asteroids
	private int fragmentsPerAsteroid = 3; 		// Fragments to divide an asteroid when it is destroyed
	private CustomGraphic ship;					// Ship graphic
	private int shipSteer; 						// Direction increment
	private float shipAcceleration; 			// Ship speed increment
	
	// Standard steer and acceleration increment
	private static final int SHIP_STEER_STEP = 5;
	private static final float SHIP_ACC_STEP = 0.5f;

	public ViewGame(Context context, AttributeSet attrs) {
		super(context, attrs);
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
	}

	@Override
	protected void onSizeChanged(int width, int height,
			int prevWidth, int prevHeight) {

		super.onSizeChanged(width, height, prevWidth, prevHeight);

		// Ship is placed in the middle
		ship.setPosX((width - ship.getWidth()) / 2);
		ship.setPosY((height - ship.getHeight()) / 2);
		
		// We adjust the position according to the screen dimensions, once we
		// know them. We ensure that no asteroid is close enough to the initial ship position
		for (CustomGraphic asteroid : asteroids) {
			do{
				asteroid.setPosX(Math.random()*(width-asteroid.getWidth()));
				asteroid.setPosY(Math.random()*(height-asteroid.getHeight()));
			} while(asteroid.getDistanceTo(ship) < (width+height)/5);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (CustomGraphic asteroide : asteroids) {
			asteroide.renderGraphic(canvas);
		}
		
		ship.renderGraphic(canvas);
	}
}
