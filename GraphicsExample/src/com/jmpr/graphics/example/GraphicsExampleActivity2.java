package com.jmpr.graphics.example;

import android.app.Activity;
import android.os.Bundle;

public class GraphicsExampleActivity2 extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
//		setContentView(new ExampleView2(this));
		
		// Layout called main define our view now
		setContentView(R.layout.main);
	}
}
