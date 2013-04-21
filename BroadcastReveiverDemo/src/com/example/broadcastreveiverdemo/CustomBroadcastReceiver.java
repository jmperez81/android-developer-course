package com.example.broadcastreveiverdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomBroadcastReceiver extends BroadcastReceiver {
	String status = "";
	String number = "";
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			// Actions for phone sms received : we launch the camera application automatically
			Intent intentToLaunch = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
			intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	// Mandatory, as we are not issuing the intent from an activity
			context.startActivity(intentToLaunch);
		} else if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			// Actions for phone ringing
			
			// Gets information from intent
			Bundle extras = intent.getExtras();
			if (extras != null) {
				status = extras.getString(TelephonyManager.EXTRA_STATE);

				if (TelephonyManager.EXTRA_STATE_RINGING.equals(status)) {
					number = extras
							.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
					
					String info = "Call from number " + number;

					Log.d("CustomBroadcastReceiver", info + " intent=" + intent);

					// Notification creation
					NotificationManager nm = (NotificationManager) context
							.getSystemService(Context.NOTIFICATION_SERVICE);
					Notification notification = new Notification(R.drawable.ic_launcher,
							"Incoming call received", System.currentTimeMillis());
					PendingIntent pendingIntent = PendingIntent.getActivity(context,
							0, new Intent(context, MainActivity.class), 0);
					notification.setLatestEventInfo(context, "Incoming call", info,
							pendingIntent);
					nm.notify(1, notification);		
				} 			
			}

		}		
	}
}
