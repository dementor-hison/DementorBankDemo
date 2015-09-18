package com.dementor.bankdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class DementorView extends LinearLayout {

	public DementorView(Context context) {
		super(context);
	}
	
	public DementorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public DementorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DementorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("DEMENTOR - ", "onDraw() : " + getWidth() + ", " + getHeight());
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		
		RectF rectF = new RectF(0, 0, getWidth(), getHeight());
		canvas.drawRoundRect(rectF, 80, 80, paint);
	}
}
