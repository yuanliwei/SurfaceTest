package com.ylw.surfacetest;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ylw.surfacetest.scene.MotionDragZoomHelper;
import com.ylw.surfacetest.scene.TestCanvas;

import java.util.Timer;

public class CanvasTestActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private Timer timer;
    private SurfaceHolder holder;
    private float w;
    private float h;

    private String TAG = "CanvasTestActivity";

    TestCanvas testCanvas;
    MotionDragZoomHelper motionEventTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_test);
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

        surfaceView = (SurfaceView) findViewById(R.id.canvas_test_surface);
        testCanvas = new TestCanvas();
        motionEventTest = new MotionDragZoomHelper();

        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                testCanvas.setWH(surfaceView.getWidth(), surfaceView.getHeight());
                draw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                testCanvas.setWH(surfaceView.getWidth(), surfaceView.getHeight());
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                testCanvas.onTouch(motionEvent);
                motionEventTest.onTouch(motionEvent);
                return true;
            }
        });

        motionEventTest.setMotionListener(new MotionDragZoomHelper.MotionListener() {
            @Override
            public void onClick(float x, float y) {
                Log.d(TAG, "onClick: x:" + x + " y:" + y);
            }

            @Override
            public void onMoveEnd(float x, float y) {
                Log.d(TAG, "onMoveEnd: x:" + x + " y:" + y);
            }

            @Override
            public void updateZoom(float zoom) {
                Log.d(TAG, "updateZoom: zoom:" + zoom);
            }

            @Override
            public void updatePosition(float x, float y) {
                Log.d(TAG, "updatePosition: x:" + x + " y:" + y);
            }

            @Override
            public void onActionDown(float x, float y) {
                Log.d(TAG, "onActionDown: x:" + x + " y:" + y);
            }

            @Override
            public void onActionUp(float x, float y) {
                Log.d(TAG, "onActionUp: x:" + x + " y:" + y);
            }

            @Override
            public void updateRotate(float degrees) {
                Log.d(TAG, "updateRotate: degrees:" + degrees);
            }
        });

        testCanvas.setHolder(holder);
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        testCanvas.draw(canvas);

        // 显示
        holder.unlockCanvasAndPost(canvas);
    }

}
