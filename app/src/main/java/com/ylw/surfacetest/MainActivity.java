package com.ylw.surfacetest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

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
    private float x=700;
    private float y=1200;

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
                x = motionEvent.getX();
                y = motionEvent.getY();
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
            timer.schedule(new InnerTimerTask(), 1000, 50);
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
//            canvas.drawRect(0, 0, w, h, paintBg);
            for (int i = 1; i < 9; i++) {
                canvas.drawArc(i * 10 * 3, i * 10 * 3, x + i * 10 * 3, y + i * 10 * 3, s, p, false, paint);
            }
            holder.unlockCanvasAndPost(canvas);
            s += 4;
            p += 8;
            if (p > 360) {
                p = -p;
            }
        }
    }

}
