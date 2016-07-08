package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by wangjing on 2016/7/8.
 */
public class Circle {
    private Paint paint;
    private float w;
    private float h;

    public Circle(float w, float h) {
        this.w = w;
        this.h = h;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    public void draw(Canvas canvas) {
        canvas.drawArc(8, 8, 530, 530, 60, 200, false, paint);
    }
}
