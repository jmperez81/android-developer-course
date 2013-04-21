package com.jmpr.asteroides.services;

import com.jmpr.asteroides.R;
import com.jmpr.asteroides.activities.Asteroids;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
	MediaPlayer player;
	// Status bar notifications
	private NotificationManager nm;  
	private static final int ID_NOTIFICATION_CREATE = 1;
	private Resources resources;

	@Override
	public void onCreate() {
		// Get an instance of resources
		resources = getResources();
		
		Toast.makeText(this, resources.getString(R.string.musicServiceStarted), Toast.LENGTH_SHORT).show();
		player = MediaPlayer.create(this, R.raw.audio);
		// Notification manager instantiation
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}

	/**
	 * Only for SDK 2.0+
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intenc, int flags, int idArranque) {
		Toast.makeText(this, resources.getString(R.string.musicServiceStarted) + idArranque,
				Toast.LENGTH_SHORT).show();
		player.start();
		
		// Issues a new notification to status bar
		// ********************************************************************
		// 	Modern way using builder pattern design (API Level 11+)

		//	Notification notification = new Notification.Builder(this)
		//		.setSmallIcon(R.drawable.ic_launcher)
		//		.setWhen(System.currentTimeMillis())
		//		.setTicker(resources.getString(R.string.musicServiceNotificationTitle))
		//		.build();		
		Notification notification = new Notification(
                R.drawable.ic_launcher,
                resources.getString(R.string.musicServiceNotificationTitle),
                System.currentTimeMillis() );
		PendingIntent pendingIntent = PendingIntent.getActivity(
		          this, 0, new Intent(this, Asteroids.class), 0);
		notification.setLatestEventInfo(this, resources.getString(R.string.musicServiceNotificationInfo),
				resources.getString(R.string.musicServiceNotificationInfo), pendingIntent);
		
		// Adds a sound for the notification
		// Way 1 : Default sound		
		notification.defaults |= Notification.DEFAULT_SOUND;
		// Way 2 : Custom sound
		// notificacion.sound = Uri.parse("file:///sdcard/carpeta/tono.mp3");
		// Way 3 : Using content provider, id must be obtained querying the Content Provider
		// notificacion.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		
		// Adds vibration for the notification
		// Way 1 : Default vibration
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		// Way 2 : Custom vibration pattern
		// notification.vibrate = new long[]{0L,100L,200L,300L};	// 0 ms vibrating, 100ms stopped, 200ms vibrating, 300ms stopped, ...
		
		// Adds led flashing (if supported by the device)
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		
		nm.notify(ID_NOTIFICATION_CREATE, notification);
		// ********************************************************************
		
		return START_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, resources.getString(R.string.musicServiceStarted) + " " + startId,
				Toast.LENGTH_SHORT).show();
		player.start();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, resources.getString(R.string.musicServiceStopped), Toast.LENGTH_SHORT).show();
		player.stop();
		
		// Remove status bar notification
		nm.cancel(ID_NOTIFICATION_CREATE);
	}

	@Override
	public IBinder onBind(Intent intencion) {
		return null;
	}
}
