package com.example.intentservicedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText input;
	public static TextView output;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);
		
		// Broadcast receiver registration 
		IntentFilter filter = new IntentFilter(OperationReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(new OperationReceiver(), filter);
	}

	public void operation(View view) {
		double n = Double.parseDouble(input.getText().toString());
		output.append(n + "^2 = ");
		// Intent i = new Intent(this, OperationService.class); // Using a regular Service will block main thread
		Intent i = new Intent(this, OperationIntentService.class);
		i.putExtra("number", n);
		startService(i);
	}

	/**
	 * Programatically creation of a broadcast receiver for collecting the operation result.
	 * Note : We could have used also a declarative version, using the manifest. This receiver only will be used by this activity, so it is defined as an inner class. 
	 *
	 */
	public class OperationReceiver extends BroadcastReceiver {

		public static final String ACTION_RESP = "com.example.intentservice.intent.action.RESPUESTA_OPERACION";

		@Override
		public void onReceive(Context context, Intent intent) {
			// Operation result will be set as an extra parameter
			Double res = intent.getDoubleExtra("result", 0.0);
			output.append(" " + res);			
		}
	}
}
