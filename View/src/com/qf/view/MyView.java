package com.qf.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyView extends View {

	private BitmapShader bitmapShader = null;

	private void init() {
		paint = new Paint();
		bitmapShader = new BitmapShader(BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_launcher),
				Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);

		paint.setShader(bitmapShader);

	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private Paint paint = null;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 绘图方法不可以实例对象
		// paint=new Paint();
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 1,
				paint);

	}

	private boolean isDown = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// event.getAction()==MotionEvent.ACTION_UP
		// event.getX()
		// event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if ((event.getX() - getWidth() / 2)
					* (event.getX() - getWidth() / 2)
					+ (event.getY() - getHeight() / 2)
					* (event.getY() - getHeight() / 2) < (getWidth() / 2 - 1)
					* (getWidth() / 2 - 1)) {
				Log.i("down", "down");
				isDown = true;

			}
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			Log.i("up2", "up2");
			if ((event.getX() - getWidth() / 2)
					* (event.getX() - getWidth() / 2)
					+ (event.getY() - getHeight() / 2)
					* (event.getY() - getHeight() / 2) < (getWidth() / 2 - 1)
					* (getWidth() / 2 - 1)) {
				Log.i("up1", "up1");
				if (isDown) {
					Log.i("up", "up");
					performClick();
					isDown = false;

				}
			}
			break;

		default:
			break;
		}

		return true;
	}

}
