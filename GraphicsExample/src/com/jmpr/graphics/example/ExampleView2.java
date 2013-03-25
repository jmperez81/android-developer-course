package com.jmpr.graphics.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class ExampleView2 extends View {
	private ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());

	// Warning, dimensions are not yet determined, onSizeChanged should be used to do things with dimensions. AttributeSet is essential if XML view definition is used
	public ExampleView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// Every time size is recalculated for this view, this callback method is called
	@Override
	protected void onSizeChanged(int width, int height,
			int previousWidth, int previousHeight) {
		// Shape readjust to adapt to the new dimensions
		shapeDrawable.setBounds(0, 0, width, height);
	}

	// This method is responsible for the view rendering
	@Override
	protected void onDraw(Canvas canvas) {
		shapeDrawable.draw(canvas);
	}
}
