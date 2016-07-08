package com.ylw.surfacetest.scene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by 袁立位 on 2016/7/8 10:50.
 */
public class Background {

    private static final String TAG = "Background";

    private final Paint paintBg;
    private final float w;
    private final float h;


    public Background(float w, float h) {
        this.w = w;
        this.h = h;
        paintBg = new Paint();
        paintBg.setColor(0xffEEEEEE);
    }

    public void draw(Canvas canvas) {

        canvas.drawRect(0, 0, w, h, paintBg);
    }

    public void onTouch(MotionEvent motionEvent) {
    }
}
