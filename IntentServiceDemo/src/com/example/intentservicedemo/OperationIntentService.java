package com.example.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Now a new thread will be created for each service operation 
 *
 */
public class OperationIntentService extends IntentService {
	public OperationIntentService() {
		super("IntentServiceOperacion");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		double n = intent.getExtras().getDouble("numero");
		SystemClock.sleep(5000);
		
		// This is not allowed!!! only from main thread is permitted to access views
		// MainActivity.output.append(n * n + "\n");
		
		// Result broadcast
		Intent i = new Intent();
		i.setAction(MainActivity.OperationReceiver.ACTION_RESP);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		i.putExtra("result", n*n);
		sendBroadcast(i);
	}
}
