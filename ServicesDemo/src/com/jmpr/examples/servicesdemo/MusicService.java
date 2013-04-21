package com.jmpr.examples.servicesdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
	MediaPlayer player;
	// Status bar notifications
	private NotificationManager nm;  
	private static final int ID_NOTIFICATION_CREATE = 1;

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
		player = MediaPlayer.create(this, R.raw.audio);
		
		// Notification manager instantiation
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}

	/**
	 * Only for SDK 2.0+
	 */
	@Override
	public int onStartCommand(Intent intenc, int flags, int idArranque) {
		Toast.makeText(this, "Service started " + idArranque,
				Toast.LENGTH_SHORT).show();
		player.start();
		
		// Issues a new notification to status bar
		Notification notification = new Notification(
                R.drawable.ic_launcher,
                "Music service started",
                System.currentTimeMillis() );
		PendingIntent pendingIntent = PendingIntent.getActivity(
		          this, 0, new Intent(this, MainActivity.class), 0);
		notification.setLatestEventInfo(this, "Playing music",
		       "additional information here", pendingIntent);
		nm.notify(ID_NOTIFICATION_CREATE, notification);
		
		return START_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "Service started" + startId,
				Toast.LENGTH_SHORT).show();
		player.start();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
		player.stop();
		
		// Remove status bar notification
		nm.cancel(ID_NOTIFICATION_CREATE);
	}

	@Override
	public IBinder onBind(Intent intencion) {
		return null;
	}
}
