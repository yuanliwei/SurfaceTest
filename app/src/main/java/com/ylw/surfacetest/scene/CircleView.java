package com.ylw.surfacetest.scene;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangjing on 2016/7/8.
 */
public class CircleView extends View {

    private Paint paint;

    public CircleView(Context context) {
        this(context, null);

    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
    }

    private float startX = 10;
    private float startY = 10;
    private float endX = 500;
    private float endY = 500;
    private float start = 0;
    private float length = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(startX, startY, endX, endY, start - 180, length, false, paint);
        canvas.drawArc(startX, startY, endX, endY, start, length, false, paint);
        start += 4;
        length += 8;
        if (length >= 180) {
            length = -length;
        }
        postInvalidateDelayed(20);//延时重画
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//触摸改变圆的位置
        float x = event.getX();
        float y = event.getY();
        startX = x - 245;
        startY = y - 245;
        endX = x + 245;
        endY = y + 245;
        return true;
    }
}
