package com.jmpr.graphics.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.View;

public class GraphicsExampleActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// The view is not defined in XML as usual, it is created
		// programmatically instead
		setContentView(new ExampleView(this));

	}

	public class ExampleView extends View {
		public ExampleView(Context context) {
			super(context);
		}

		// This method is responsible for the view rendering
		@Override
		protected void onDraw(Canvas canvas) {
			// Brush definition
			Paint brush = new Paint();

			// Color definition 1
			// brush.setColor(Color.BLUE);
			
			// Color definition 2, through a colors.xml file
			brush.setColor(getResources().getColor(R.color.circleColor));

			brush.setStrokeWidth(8);
			brush.setStyle(Style.STROKE);

			// Draw a circle
			canvas.drawCircle(150, 150, 100, brush);

			// Draw a point in the middle of the circle
			canvas.drawPoint(150, 150, brush);

			// Draw a line
			canvas.drawLine(158, 150, 250, 150, brush);
		}
	}
}