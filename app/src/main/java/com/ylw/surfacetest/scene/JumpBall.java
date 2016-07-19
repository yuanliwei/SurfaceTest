package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 袁立位 on 2016/7/13 20:34.
 */
public class JumpBall {
    private static final String TAG = "JumpBall";
    private final float sw = 50;
    private int width;
    private int height;
    private Paint paint;

    public JumpBall() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(sw);
        paint.setColor(0xffff3333);
        paint.setAlpha(51);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
    }

    float sx = 0;
    float sy = 0;

    int xi = 13;
    int yi = 13;

    public void drawAnim(Canvas canvas, Integer value) {
        canvas.drawColor(0xffffffff);
        float x = 0;
        float y = 0;
        for (int i = 0; i < 5; i++) {
            x = sx + i * xi;
            y = sy + i * yi;
            canvas.drawPoint(x, y, paint);
        }
        if (x > width && xi > 0 || x < 0 && xi < 0) xi = -xi;
        if (y > height && yi > 0 || y < 0 && yi < 0) yi = -yi;
        sx += xi;
        sy += yi;
    }
}
