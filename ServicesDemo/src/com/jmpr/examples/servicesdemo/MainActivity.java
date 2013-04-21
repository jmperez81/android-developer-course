package com.jmpr.examples.servicesdemo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button arrancar = (Button) findViewById(R.id.start_button);
		arrancar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startService(new Intent(MainActivity.this, MusicService.class));
			}
		});
		Button detener = (Button) findViewById(R.id.stop_button);
		detener.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				stopService(new Intent(MainActivity.this, MusicService.class));
			}
		});
	}

}
