package com.jmpr.graphics.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GraphicsExampleActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// The view is not defined in XML as usual, it is created
		// programmatically instead
		setContentView(new ExampleView(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.graphics_example, menu);
		return true;
	}

	public void launchExample2(View view) {
		Intent intent = new Intent(this, GraphicsExampleActivity2.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.example2:
			launchExample2(null);
			break;
		}

		// We consume the item, don't propagate
		return true;
	}
}