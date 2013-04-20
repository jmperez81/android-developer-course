package com.jmpr.examples.videoview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private VideoView mVideoView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mVideoView = (VideoView) findViewById(R.id.surface_view);

		// Video is included under res/raw directory, must push it inside this directory using file explorer view with emulator started
		mVideoView.setVideoPath("/mnt/sdcard/video.mp4");
		
		// Alternatively, we can define a streaming
		// mVideoView.setVideoURI(Uri.parse(UriString));

		// Adds a media controller
		mVideoView.setMediaController(new MediaController(this));
		
		mVideoView.start();
		mVideoView.requestFocus();
	}
}
