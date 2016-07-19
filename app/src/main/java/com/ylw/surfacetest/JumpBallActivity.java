package com.ylw.surfacetest;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ylw.surfacetest.scene.JumpBall;

public class JumpBallActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    private SurfaceHolder holder;
    private JumpBall jumpBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_ball);
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
            boolean start = false;

            @Override
            public void onClick(View view) {
                if (start) {
                    stop();
                } else {
                    start();
                }
                start = !start;
            }

        });

        surfaceView = (SurfaceView) findViewById(R.id.jump_ball_surface);
        holder = surfaceView.getHolder();
        jumpBall = new JumpBall();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                jumpBall.setWH(surfaceView.getWidth(), surfaceView.getHeight());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                jumpBall.setWH(surfaceView.getWidth(), surfaceView.getHeight());
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private ValueAnimator anim;

    public void anim() {
        if (anim == null) {
            anim = ValueAnimator.ofInt(0, 3000);
            anim.setDuration(600);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatMode(ValueAnimator.RESTART);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                Log.d(TAG, "cur - AnimatedValue : " + animation.getAnimatedValue());
                    Canvas canvas = holder.lockCanvas();
                    if (canvas == null) return;

                    jumpBall.drawAnim(canvas, (Integer) animation.getAnimatedValue());

                    // 显示
                    holder.unlockCanvasAndPost(canvas);
                }
            });
        }
        anim.start();
    }

    private void start() {
        anim();
    }

    private void stop() {
        anim.cancel();
    }

}
