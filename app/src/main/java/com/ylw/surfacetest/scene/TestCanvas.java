package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * 测试Canvas方法
 * <p/>
 * Created by 袁立位 on 2016/7/14 10:21.
 */
public class TestCanvas {
    private static final String TAG = "TestCanvas";
    private int width;
    private int height;

    private Paint paint;
    private Paint paintFlag;
    MotionDragZoomHelper motionEventTest;
    private SurfaceHolder holder;

    public float y = 111;
    public float x = 222;
    private float strokeWidth = 600;


    float zoom = 1f;
    private float degrees = 0;

    public TestCanvas() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        motionEventTest = new MotionDragZoomHelper();
        motionEventTest.setStartPosition(x, y);
        motionEventTest.setMotionListener(new MotionDragZoomHelper.MotionListener() {

            @Override
            public void onClick(float x, float y) {
                TestCanvas.this.x = x;
                TestCanvas.this.y = y;
                motionEventTest.setStartPosition(x, y);
                draw();
            }

            @Override
            public void onMoveEnd(float x, float y) {
            }

            @Override
            public void updateZoom(float zoom) {
                TestCanvas.this.zoom = zoom;
                draw();

            }

            @Override
            public void updatePosition(float x, float y) {
                TestCanvas.this.x = x;
                TestCanvas.this.y = y;
                draw();
            }

            @Override
            public void onActionDown(float x, float y) {
            }

            @Override
            public void onActionUp(float x, float y) {
            }

            @Override
            public void updateRotate(float degrees) {
                TestCanvas.this.degrees = degrees;
                Log.d(TAG, "updateRotate: degree : " + degrees);
                draw();
            }
        });
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void onTouch(MotionEvent e) {
        motionEventTest.onTouch(e);
    }

    public void draw(Canvas canvas) {
        paint.setColor(0xffff9999);
        paint.setStrokeWidth(strokeWidth * zoom);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.translate(x, y);
        canvas.rotate(degrees);
        canvas.drawColor(0xffffffff);
        canvas.drawPoint(0, 0, paint);

        paint.setStrokeWidth(strokeWidth * zoom / 5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(0xff009900);
        canvas.drawPoint(-strokeWidth * zoom / 2, -strokeWidth * zoom / 2, paint);
//        canvas.translate(100, 200);
//
//        canvas.drawPoint(200, 200, paint);

    }

    public void setHolder(SurfaceHolder holder) {
        this.holder = holder;
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;
        draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
}
