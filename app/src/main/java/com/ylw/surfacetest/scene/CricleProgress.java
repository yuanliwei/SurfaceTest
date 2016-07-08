package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import static java.lang.Math.min;
import static java.lang.Math.pow;

/**
 * Created by 袁立位 on 2016/7/8 10:50.
 */
public class CricleProgress {

    private static final String TAG = "CricleProgress";
    private final Paint paint;
    private final Paint paintBg;
    private final float w;
    private final float h;

    private float startX = 87.87419f;
    private float startY = 140.77823f;
    private float endX = 718.29047f;
    private float endY = 751.4602f;

    private RectF rect;

    float s = 0.1f;
    float p = 0.1f;

    public CricleProgress(float w, float h) {
        this.w = w;
        this.h = h;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x88ff8888);
        paint.setStyle(Paint.Style.STROKE);
        paintBg = new Paint();
        paintBg.setColor(0xffEEEEEE);
        rect = new RectF(startX, startY, endX, endY);
    }

    public void draw(Canvas canvas) {

        // 画圆弧
        paint.setColor(0xffff8888);
        paint.setStrokeWidth(min(rect.width() / 20, rect.height() / 20));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setColor(0xff33ff33);

        canvas.drawArc(rect, s, p, false, paint);
//            canvas.drawArc(rect, s + 90, p, false, paint);
        canvas.drawArc(rect, s + 180, p, false, paint);
//            canvas.drawArc(rect, s + 270, p, false, paint);

        // 画点
        canvas.drawPoints(new float[]{rect.left, rect.top, rect.right, rect.bottom}, paint);

        // 画方框
        paint.setColor(0xff8888ff);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, paint);


        // 修改值
        s += 4;
        p += 4;
        if (p > 180) {
            p = -p;
        }
    }


    PointF downPos = new PointF();
    PointF curPos = new PointF();
    boolean move = false;

    public void onTouch(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        curPos.set(x, y);

        double start = pow(rect.left - x, 2) + pow(rect.top - y, 2);
        double end = pow(rect.right - x, 2) + pow(rect.bottom - y, 2);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPos.set(curPos.x - rect.left, curPos.y - rect.top);
                if (start > 1000 && end > 1000 && rect.contains(x, y)) {
                    move = true;
                } else {
                    move = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (move) {
                    rect.offsetTo(curPos.x - downPos.x, curPos.y - downPos.y);
                } else if (start < end) {
                    rect.left = x;
                    rect.top = y;
                } else {
                    rect.right = x;
                    rect.bottom = y;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        Log.d(TAG, "x1 = " + startX + ", y1 = " + startY + "; x2 = " + endX + ", y2 = " + endY + "");
    }
}
