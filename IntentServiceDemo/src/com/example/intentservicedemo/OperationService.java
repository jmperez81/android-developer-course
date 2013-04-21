package com.example.intentservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * A normal service can't be used for heavy operations. This blocks main application's thread. Use IntentService instead. 
 * @author Zen
 *
 */
public class OperationService extends Service {
	@Override
	public int onStartCommand(Intent i, int flags, int idArranque) {
		double n = i.getExtras().getDouble("number");
		SystemClock.sleep(5000);			// Simulates a heavy operation
		MainActivity.output.append(n * n + "\n");
		return START_NOT_STICKY;  
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
