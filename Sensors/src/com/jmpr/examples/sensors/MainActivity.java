package com.jmpr.examples.sensors;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	private TextView output;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		output = (TextView) findViewById(R.id.output);

		// All sensors
		log("Available sensors");
		log("*****************");
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensorList) {
			log(sensor.getName());
		}

		log("Sensor readings");
		log("*****************");
		
		// Orientation sensors readings
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!sensorList.isEmpty()) {
			Sensor orientationSensor = sensorList.get(0);
			sensorManager.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_UI);
		}

		// Accelerometer sensors listener
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (!sensorList.isEmpty()) {
			Sensor acelerometerSensor = sensorList.get(0);
			sensorManager.registerListener(this, acelerometerSensor,
					SensorManager.SENSOR_DELAY_UI);
		}

		// Magnetic field sensors listener 
		sensorList = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
		if (!sensorList.isEmpty()) {
			Sensor magneticSensor = sensorList.get(0);
			sensorManager.registerListener(this, magneticSensor,
					SensorManager.SENSOR_DELAY_UI);
		}

		// Temperature sensors listener
		sensorList = sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
		if (!sensorList.isEmpty()) {
			Sensor temperatureSensor = sensorList.get(0);
			sensorManager.registerListener(this, temperatureSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
	}

	private void log(String string) {
		output.append(string + "\n");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int precision) {
		// Nothing done
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Each sensor could access to this method, so the synchronized modifier is needed
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ORIENTATION:
				for (int i = 0; i < 3; i++) {
					log(event.sensor.getName() + i + ": " + event.values[i]);
				}
				break;
			case Sensor.TYPE_ACCELEROMETER:
				for (int i = 0; i < 3; i++) {
					log(event.sensor.getName() + i + ": " + event.values[i]);
				}
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				for (int i = 0; i < 3; i++) {
					log(event.sensor.getName() + i + ": " + event.values[i]);
				}
				break;
			default:
				for (int i = 0; i < event.values.length; i++) {
					log(event.sensor.getName() + i + ": " + event.values[i]);
				}
			}
		}
	}
}
