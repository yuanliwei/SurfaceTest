package com.ylw.surfacetest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ylw.surfacetest.scene.Background;
import com.ylw.surfacetest.scene.CricleProgress;
import com.ylw.surfacetest.scene.MultiLine;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private Timer timer;
    private SurfaceHolder holder;
    private float w;
    private float h;

    private String TAG = "MainActivity";


    private Background background;
    private CricleProgress cricleProgress;
    private MultiLine multiLine;

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
                cricleProgress.onTouch(motionEvent);
                multiLine.onTouch(motionEvent);
                return true;
            }
        });
    }


    private class HolderCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
//            canvas = surfaceHolder.lockCanvas();
//            canvas.drawArc(50,200,450,600,);
            timer = new Timer("timer");
            timer.schedule(new InnerTimerTask(), 100, 20);
            w = surfaceView.getWidth();
            h = surfaceView.getHeight();
            cricleProgress = new CricleProgress(w, h);
            multiLine = new MultiLine(w, h);
            background = new Background(w, h);
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
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x88ff8888);
        paint.setStrokeWidth(60);
        paint.setStyle(Paint.Style.STROKE);
        Paint paintBg = new Paint();
        paintBg.setColor(0xffEEEEEE);

    }

    private class InnerTimerTask extends TimerTask {


        @Override
        public void run() {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) return;
            background.draw(canvas);
            cricleProgress.draw(canvas);
            multiLine.draw(canvas);
            // 显示
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
