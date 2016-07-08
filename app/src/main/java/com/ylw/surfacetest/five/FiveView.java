package com.ylw.surfacetest.five;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangjing on 2016/7/8.
 */
public class FiveView extends View {

    private FiveBackground fiveBackground;
    private Context context;

    public FiveView(Context context) {
        this(context, null);
    }

    public FiveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FiveView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FiveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        fiveBackground = new FiveBackground(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        fiveBackground.setWidthHeight(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        fiveBackground.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        fiveBackground.onTouchEvent(event);
        postInvalidate();
        return true;
    }
}
