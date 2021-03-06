package com.jmpr.android.examples;

import com.example.intents.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	public void pgWeb(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.google.es/"));
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void llamadaTelefono(View view) {
		Intent intent = new Intent(Intent.ACTION_CALL,
				Uri.parse("tel:629762979"));
		startActivity(intent);
	}

	public void googleMaps(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("geo:41.656313,-0.877351"));
		startActivity(intent);
	}

	public void tomarFoto(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivity(intent);
	}

	public void mandarCorreo(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
		intent.putExtra(Intent.EXTRA_TEXT, "texto del correo");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "juanma4ever@hotmail.com" });
		startActivity(intent);
	}
}
