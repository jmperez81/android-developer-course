package com.example.broadcastreveiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jmpr.examples.servicesdemo.MusicService;

/**
 * NOTE: Not working in emulator, not tested under a real device
 * @author Zen
 *
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, MusicService.class));
	}
}
