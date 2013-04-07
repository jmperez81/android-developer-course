package com.jmpr.examples.touchscreen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class TouchScreenActivity extends Activity implements OnTouchListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView input = (TextView) findViewById(R.id.TextViewInput);
		input.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touch_screen, menu);
		return true;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		TextView output = (TextView) findViewById(R.id.TextViewOuput);
		output.setText(event.toString());

		// We catched the event and process it, so it must not be propagated
		return true;
	}

}
