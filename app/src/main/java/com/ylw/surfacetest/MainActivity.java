package com.ylw.surfacetest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    boolean isCreate = false;
    private Canvas canvas;
    private Timer timer;
    private SurfaceHolder holder;
    private Paint paint;
    private Paint paintBg;
    private float w;
    private float h;
    private float endX = 700;
    private float endY = 1200;

    private float startX = 0;
    private float startY = 0;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        holder = surfaceView.getHolder();
        holder.addCallback(new HolderCallBack());

        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                double start = pow(startX - x, 2) + pow(startY - y, 2);
                double end = pow(endX - x, 2) + pow(endY - y, 2);
                if (start < end) {
                    startX = x;
                    startY = y;
                } else {
                    endX = x;
                    endY = y;
                }
                Log.d(TAG,"x1 = , y1 = ; x2 = , y2 = ") ;
                return true;
            }
        });
        initPaint();
    }


    private class HolderCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
//            canvas = surfaceHolder.lockCanvas();
//            canvas.drawArc(50,200,450,600,);
            timer = new Timer("timer");
            timer.schedule(new InnerTimerTask(), 1000, 10);
            w = surfaceView.getWidth();
            h = surfaceView.getHeight();

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            timer.cancel();
        }
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x88ff8888);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paintBg = new Paint();
        paintBg.setColor(0xffEEEEEE);

    }

    private class InnerTimerTask extends TimerTask {
        float s = 0.1f;
        float p = 0.1f;

        @Override
        public void run() {
            canvas = holder.lockCanvas();
            canvas.drawRect(0, 0, w, h, paintBg);

            // 画圆弧
            paint.setColor(0xffff8888);
            paint.setStrokeWidth(40);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);

            paint.setColor(0xff33ff33);

            canvas.drawArc(startX, startY, endX, endY, s, p, false, paint);
//            canvas.drawArc(startX, startY, endX, endY, s + 90, p, false, paint);
            canvas.drawArc(startX, startY, endX, endY, s + 180, p, false, paint);
//            canvas.drawArc(startX, startY, endX, endY, s + 270, p, false, paint);

            // 画点
            canvas.drawPoints(new float[]{startX, startY, endX, endY}, paint);

            // 画方框
            paint.setColor(0xff8888ff);
            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(startX, startY, endX, endY, paint);

            // 显示
            holder.unlockCanvasAndPost(canvas);

            // 修改值
            s += 2;
            p += 4;
            if (p > 180) {
                p = -p;
            }
        }
    }

}
