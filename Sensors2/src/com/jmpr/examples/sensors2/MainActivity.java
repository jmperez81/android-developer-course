package com.jmpr.examples.sensors2;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	private List<Sensor> sensorList;
	private TextView aTextView[][] = new TextView[20][3];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root);
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorList = sm.getSensorList(Sensor.TYPE_ALL);
		int n = 0;
		
		// For every sensor detected for this device...
		for (Sensor sensor : sensorList) {
			// A textview with sensor name is added			
			TextView mTextView = new TextView(this);
			mTextView.setText(sensor.getName());			
			rootLayout.addView(mTextView);
			
			// An horizontal layout is added...
			LinearLayout nLinearLayout = new LinearLayout(this);
			rootLayout.addView(nLinearLayout);
			
			// With a Textview with each reading value from sensor
			for (int i = 0; i < 3; i++) {
				aTextView[n][i] = new TextView(this);
				aTextView[n][i].setText("?");
				aTextView[n][i].setWidth(87);
			}
			TextView xTextView = new TextView(this);
			xTextView.setText("  X: ");
			nLinearLayout.addView(xTextView);
			nLinearLayout.addView(aTextView[n][0]);
			TextView yTextView = new TextView(this);
			yTextView.setText("  Y: ");
			nLinearLayout.addView(yTextView);
			nLinearLayout.addView(aTextView[n][1]);
			TextView zTextView = new TextView(this);
			zTextView.setText("  Z: ");
			nLinearLayout.addView(zTextView);
			nLinearLayout.addView(aTextView[n][2]);
			sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
			n++;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			int n = 0;
			for (Sensor sensor : sensorList) {
				if (event.sensor == sensor) {
					for (int i = 0; i < event.values.length; i++) {
						aTextView[n][i]
								.setText(Float.toString(event.values[i]));
					}
				}
				n++;
			}
		}
	}
}