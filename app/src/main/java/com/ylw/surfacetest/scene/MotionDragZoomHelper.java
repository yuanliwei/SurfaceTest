package com.ylw.surfacetest.scene;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * author ylw Created on 2016/7/10. description : MotionDragZoomHelper
 */
public class MotionDragZoomHelper {
    private static final String TAG = "MotionDragZoomHelper";

    private int index1 = 0;
    private int index2 = 0;

    private float zoom = 1;
    private float oZoom = 1;
    private boolean moved;
    private float downTwoPointLength = 0;

    private static final long timeWindow = 800;

    private float[][] p = new float[15][2];

    private PointF downP = new PointF();
    private PointF downP2 = new PointF();

    private PointF imgP = new PointF();
    private PointF downImgP = new PointF();
    private PointF offsetP = new PointF();
    private PointF dOffsetP = new PointF();

    float degrees = 0;
    float downDegrees = 0;
    float nowDegrees = 0;
    boolean reverse = false;

    public void onTouch(MotionEvent event) {
        if (motionListener == null) {
            Log.w(TAG, "onTouch: motionListener is null!");
            return;
        }

        int index = event.getActionIndex();
        p[index][0] = event.getX(index);
        p[index][1] = event.getY(index);


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                index1 = index;
                index2 = index;
                downP.set(p[index1][0], p[index1][1]);
                downP2.set(p[index2][0], p[index2][1]);
                downImgP.set(imgP);

                offsetP.set((downP.x + downP2.x) / 2 - imgP.x, (downP.y + downP2.y) / 2 - imgP.y);
                dOffsetP.set(offsetP);
                oZoom = zoom;
                moved = false;

                motionListener.onActionDown(downP.x, downP.y);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                index1 = index2;
                index2 = index;
                downP.set(p[index1][0], p[index1][1]);
                downP2.set(p[index2][0], p[index2][1]);
                downImgP.set(imgP);
                oZoom = zoom;
                moved = true;


                offsetP.set((downP.x + downP2.x) / 2 - imgP.x, (downP.y + downP2.y) / 2 - imgP.y);
                dOffsetP.set(offsetP);
                downTwoPointLength = (float) sqrt(pow(downP.x - downP2.x, 2) + pow(downP.y - downP2.y, 2)) / zoom;

                reverse = downP.y > downP2.y;
                downDegrees = (float) (acos((downP.x - downP2.x) / downTwoPointLength) - degrees);
                if (reverse) {
                    downDegrees += PI;
                }

                nowDegrees = downDegrees;

                break;
            case MotionEvent.ACTION_MOVE:
                p[index1][0] = event.getX(index1);
                p[index1][1] = event.getY(index1);
                p[index2][0] = event.getX(index2);
                p[index2][1] = event.getY(index2);

                float nowTwoPointLength = (float) sqrt(pow(p[index1][0] - p[index2][0], 2) + pow(p[index1][1] - p[index2][1], 2));
                if (nowTwoPointLength != 0 && downTwoPointLength != 0) {
                    zoom = nowTwoPointLength / downTwoPointLength;
                    offsetP.set(dOffsetP.x * zoom / oZoom, dOffsetP.y * zoom / oZoom);
                    motionListener.updateZoom(zoom);

                    reverse = p[index1][1] < p[index2][1];
                    nowDegrees = (float) (acos((p[index1][0] - p[index2][0]) / nowTwoPointLength));
                    if (reverse) {
                        downDegrees += PI;
                    }

                    degrees = nowDegrees - downDegrees;

                    motionListener.updateRotate((float) (degrees * 180 / PI));
                }
                imgP.set((p[index1][0] + p[index2][0]) / 2 - offsetP.x, (p[index1][1] + p[index2][1]) / 2 - offsetP.y);
                motionListener.updatePosition(imgP.x, imgP.y);
                if (pow(p[index1][0] - downP.x, 2) + pow(p[index1][1] - downP.y, 2) > 20
                        || pow(p[index2][0] - downP2.x, 2) + pow(p[index2][1] - downP2.y, 2) > 20) {
                    moved = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int count = event.getPointerCount();
                boolean change = false;
                for (int i = 0; i < count; i++) {
                    if (count == 2) {
                        if (i != index) {
                            index1 = i;
                            index2 = i;
                            change = true;
                            break;
                        }
                    } else if (index1 == index) {
                        if (i != index && i != index2) {
                            index1 = i;
                            change = true;
                            break;
                        }
                    } else if (index2 == index) {
                        if (i != index && i != index1) {
                            index2 = i;
                            change = true;
                            break;
                        }
                    }
                }
                if (change) {
                    downP.set(p[index1][0], p[index1][1]);
                    downP2.set(p[index2][0], p[index2][1]);
                    offsetP.set((downP.x + downP2.x) / 2 - imgP.x, (downP.y + downP2.y) / 2 - imgP.y);
                }
                if (index1 > index) index1--;
                if (index2 > index) index2--;
                break;
            case MotionEvent.ACTION_UP:
                if (!moved) {
                    motionListener.onActionUp(event.getX(), event.getY());
                    if (event.getEventTime() - event.getDownTime() < timeWindow) {
                        motionListener.onClick(event.getX(), event.getY());
                    }
                } else {
                    motionListener.onMoveEnd(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

    }

    private MotionListener motionListener;

    public void setStartPosition(float x, float y) {
        imgP.set(x, y);
    }

    public interface MotionListener {

        void onClick(float x, float y);

        void onMoveEnd(float x, float y);

        void updateZoom(float zoom);

        void updatePosition(float x, float y);

        void onActionDown(float x, float y);

        void onActionUp(float x, float y);

        void updateRotate(float degrees);
    }

    public void setMotionListener(MotionListener motionListener) {
        this.motionListener = motionListener;
    }
}
