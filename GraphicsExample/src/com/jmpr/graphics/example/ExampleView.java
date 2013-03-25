package com.jmpr.graphics.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class ExampleView extends View {
	private Paint brush = new Paint();
	private Path simplePath = new Path();
	private Path complexPath = new Path();
	private Drawable imgRathalos;
	private ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());

	public ExampleView(Context context) {
		super(context);
	}

	// Warning, dimensions are not yet determined, onSizeChanged should be used to do things with dimensions
	public ExampleView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	// Every time size is recalculated for this view, this callback method is called
	@Override
	protected void onSizeChanged(int width, int height,
			int previousWidth, int previousHeight) {

	}

	// This method is responsible for the view rendering
	@Override
	protected void onDraw(Canvas canvas) {
		// Brush definition

		// Color definition way 1
		// *****************************************************************
		brush.setColor(Color.BLUE);
		// *****************************************************************

		// Color definition 2, through a colors.xml file
		// *****************************************************************
		// brush.setColor(getResources().getColor(R.color.circleColor));
		// *****************************************************************

		brush.setStrokeWidth(8);
		brush.setStyle(Style.STROKE);

		// Draw a circle
		canvas.drawCircle(150, 550, 100, brush);

		// Draw a point in the middle of the circle
		canvas.drawPoint(150, 550, brush);

		// Draw a line
		canvas.drawLine(158, 550, 250, 550, brush);

		// *****************************************************************
		// Draw a simple path
		// *****************************************************************
		simplePath.addCircle(150, 300, 100, Direction.CCW);
		canvas.drawPath(simplePath, brush);
		brush.setStrokeWidth(1);
		brush.setStyle(Style.FILL);
		brush.setTextSize(20);
		brush.setTypeface(Typeface.SANS_SERIF);
		canvas.drawTextOnPath("Android application developer for smartphones", simplePath, 10, -20, brush);

		// *****************************************************************
		// Draw a complex path
		// *****************************************************************
		complexPath.moveTo(50, 100);
		complexPath.cubicTo(60, 70, 150, 90, 200, 110);
		complexPath.lineTo(300, 200);
		canvas.drawPath(complexPath, brush);
		brush.setStrokeWidth(1);
		brush.setStyle(Style.FILL);
		brush.setTextSize(20);
		brush.setTypeface(Typeface.SANS_SERIF);
		canvas.drawTextOnPath("Android application developer for smartphones", complexPath, 10, -20, brush);

		// *****************************************************************
		// Draw image via Drawable
		// *****************************************************************
		imgRathalos = getContext().getResources().getDrawable(R.drawable.rathalos);
		imgRathalos.setBounds(300, 300, 450, 450);
		imgRathalos.draw(canvas);

		// *****************************************************************
		// Set a background image
		// Note : it's better to set the background in the layout xml code
		// android:background="@drawable/gradient"
		// *****************************************************************
		setBackgroundResource(R.drawable.gradient);

		// *****************************************************************
		// Draw a ShapeDrawable
		// *****************************************************************
		// shapeDrawable.getPaint().setColor(0xff0000ff);
		shapeDrawable.setBounds(300, 500, 450, 600);
		shapeDrawable.draw(canvas);
	}
}
