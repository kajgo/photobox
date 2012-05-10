package com.photobox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OurCustomView extends View {

	private int w = 20;
	private int h = 20;
			
	public OurCustomView(Context context) {
		super(context);
	}
	
    public OurCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
	public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("panic", "someone is touching me... help!!");
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint bgPaint = new Paint();
		bgPaint.setARGB(255, 255, 255, 0);
		canvas.drawPaint(bgPaint);
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 255);
		canvas.drawRect(0, 0, this.w-this.w/2, this.h-this.h/2, paint);
	}
}
