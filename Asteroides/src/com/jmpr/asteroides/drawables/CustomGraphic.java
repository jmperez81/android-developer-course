package com.jmpr.asteroides.drawables;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class CustomGraphic {
	private Drawable drawable; 		// Drawable to be drawn
	private double posX, posY; 		// Position
	private double incX, incY; 		// Movement speed
	private int angle, rotation;	// Rotation angle and speed
	private int width, height; 		// Dimensions
	private int radioCollision;		// Determines if a collision occurred

	// View where the drawable will be drawn
	private View view;

	// Will determine the space to delete (view.ivalidate)
	public static final int MAX_SPEED = 20;

	public CustomGraphic(View view, Drawable drawable) {
		this.view = view;
		this.drawable = drawable;
		width = drawable.getIntrinsicWidth();
		height = drawable.getIntrinsicHeight();
		radioCollision = (height + width) / 4;
	}

	public void renderGraphic(Canvas canvas) {
		canvas.save();
		int x = (int) (posX + width / 2);
		int y = (int) (posY + height / 2);
		canvas.rotate((float) angle, (float) x, (float) y);
		drawable.setBounds((int) posX, (int) posY,
				(int) posX + width, (int) posY + height);
		drawable.draw(canvas);
		canvas.restore();
		int rInval = (int) Math.hypot(width, height) / 2 + MAX_SPEED;
		view.invalidate(x - rInval, y - rInval, x + rInval, y + rInval);
	}

	public void updatePosition(double factor) {
		posX += incX * factor;
		// If the drawable is out of the screen, position must be readjusted
		if (posX < -width / 2) {
			posX = view.getWidth() - width / 2;
		}

		if (posX > view.getWidth() - width / 2) {
			posX = -width / 2;
		}

		posY += incY * factor;

		if (posY < -height / 2) {
			posY = view.getHeight() - height / 2;
		}

		if (posY > view.getHeight() - height / 2) {
			posY = -height / 2;
		}

		angle += rotation * factor; // Angle update
	}

	public double getDistanceTo(CustomGraphic g) {
		return Math.hypot(posX - g.posX, posY - g.posY);
	}

	public boolean checkCollision(CustomGraphic g) {
		return (getDistanceTo(g) < (radioCollision + g.radioCollision));
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getIncX() {
		return incX;
	}

	public void setIncX(double incX) {
		this.incX = incX;
	}

	public double getIncY() {
		return incY;
	}

	public void setIncY(double incY) {
		this.incY = incY;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRadioCollision() {
		return radioCollision;
	}

	public void setRadioCollision(int radioCollision) {
		this.radioCollision = radioCollision;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
}
