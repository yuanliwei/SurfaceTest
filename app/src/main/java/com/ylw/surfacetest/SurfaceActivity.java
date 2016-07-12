package com.ylw.surfacetest;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ylw.surfacetest.scene.Background;
import com.ylw.surfacetest.scene.Circle;
import com.ylw.surfacetest.scene.CricleProgress;
import com.ylw.surfacetest.scene.MultiLine;

import java.util.Timer;
import java.util.TimerTask;

public class SurfaceActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private Timer timer;
    private SurfaceHolder holder;
    private float w;
    private float h;

    private String TAG = "SurfaceActivity";


    private Background background;
    private CricleProgress cricleProgress;
    private MultiLine multiLine;
    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
            cricleProgress = new CricleProgress();
            multiLine = new MultiLine();
            background = new Background();
            circle = new Circle(w, h);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            w = surfaceView.getWidth();
            h = surfaceView.getHeight();
            cricleProgress.initWidthHeigth(w, h);
            multiLine.initWidthHeigth(w, h);
            background.initWidthHeigth(w, h);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            timer.cancel();
        }
    }


    private class InnerTimerTask extends TimerTask {


        @Override
        public void run() {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) return;
            background.draw(canvas);
            cricleProgress.draw(canvas);
            multiLine.draw(canvas);
            circle.draw(canvas);
            // 显示
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
