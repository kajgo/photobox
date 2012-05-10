package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OurCustomView extends View {

	private float px = 20;
	private float py = 20;
			
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
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d("iiiih!", "don't push it...");
			break;
		case MotionEvent.ACTION_UP:
			Log.d("phiew!", "back again!");
			break;
		case MotionEvent.ACTION_MOVE:
			px = event.getX();
			py = event.getY();
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint bgPaint = new Paint();
		bgPaint.setARGB(255, 255, 255, 0);
		canvas.drawPaint(bgPaint);
		
		Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.testimage_x);
		
		int border = 10;
		int w = image.getWidth() + border * 2;
		int h = image.getHeight() + border * 2;
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		canvas.drawRect(px-w/2-border, py-h/2-border, px+w/2+border, py+h/2+border, paint);
		
		Matrix matrix = new Matrix();
		matrix.setTranslate(px-w/2+border, py-h/2+border);
		canvas.drawBitmap(image, matrix, null);
	}
}
