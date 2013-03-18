package com.jmpr.additionalviews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText entrada;
	private TextView salida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Object o = findViewById(R.id.entrada);
		entrada = (EditText) findViewById(R.id.entrada);
		salida = (TextView) findViewById(R.id.salida);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void sePulsa(View view) {
			try {
			salida.setText(String.valueOf(Float.parseFloat(entrada.getText()
					.toString()) * 2.0));
			} catch (NumberFormatException e) {
				Toast.makeText(this, "¡Introduce un valor numérico!", Toast.LENGTH_SHORT).show();
			}			
	}

	public void sePulsa0(View view) {
		entrada.setText("");
		salida.setText("");
	}
}
