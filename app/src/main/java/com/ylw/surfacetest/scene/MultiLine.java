package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by 袁立位 on 2016/7/8 10:57.
 */
public class MultiLine {

    private final Paint paint;
    private final Paint paintBg;
    private final float w;
    private final float h;
    private int time;
    private float[] points;
    private int dt = 30;
    private int count;

    public MultiLine(float w, float h) {
        this.w = w;
        this.h = h;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff8888);
        paint.setStyle(Paint.Style.STROKE);
        paintBg = new Paint();
        paintBg.setColor(0xffEEEEEE);

        initPoints(dt);
    }

    private void initPoints(float dt) {
        count = (int) (w / dt);
        float dh = h / count;
        points = new float[count * 4];
        for (int i = 0; i < count * 4; ) {
            points[i] = dt;
            points[i + 1] = dh + dh * i / 4;

            points[i + 2] = dt + dt * i / 4;
            points[i + 3] = h - dt;

            i += 4;
        }

    }

    public void draw(Canvas canvas) {
        paint.setColor(0xffff0000);
        paint.setStrokeWidth(3);

        canvas.drawLine(dt, 0, dt, h, paint);
        canvas.drawLine(0, h - dt, w, h - dt, paint);

        drawPoints(canvas, w - dt, h - dt, dt);

        drawTime(canvas);
    }

    private void drawTime(Canvas canvas) {
        paint.setStrokeWidth(3);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(0xff3333ff);
        paint.setTextSize(60);
        canvas.drawText("Time : " + time, w / 2, h / 3 * 2, paint);
        time++;
    }

    private void drawPoints(Canvas canvas, float w, float h, int dt) {

        if (time > count * 2) time = 0;
        int start;
        int length;
        if (time < count) {
            start = 0;
            length = time*4;
        } else {
            start = (time - count)*4;
            length = (count*2 - time)*4;
        }

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
        paint.setColor(0xff00ff33);

//        canvas.drawPoints(points, paint);
        canvas.drawPoints(points, start, length, paint);

        paint.setStrokeWidth(1);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setColor(0xff0000ff);
//        canvas.drawLines(points, paint);
        canvas.drawLines(points, start, length, paint);


    }

    public void onTouch(MotionEvent motionEvent) {

    }
}
