package com.moxm.frameworks.samples.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by Richard on 15/7/14.
 */
public class SampleSurfaceView extends SurfaceView implements Callback, Runnable {

    private static final String TAG = "+++SampleSurfaceView";

    private SurfaceHolder mSurfaceHolder;
    private boolean run = false;

    private Thread thread;
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();;

    public SampleSurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        Log.d(TAG, "SampleSurfaceView");
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        thread = new Thread(this);//创建一个绘图线程

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceCreated");
        run = true;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceDestroyed");
        run = false;
    }

    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
        Log.d(TAG, "draw");

        canvas.getClipBounds(mRect);

        drawCircle(canvas, mRect, mPaint);
    }

    public void drawCircle(Canvas canvas, final Rect rect, Paint paint) {
        int top = rect.top;
        int left = rect.left;
        int width = rect.width();
        int height = rect.height();
        Log.d(TAG, "drawCircle.top:" + top + " | left:" + left + " | width:" + width + " | height:" + height);

        paint.setColor(0xffff00ff);
        paint.setAntiAlias(true);
        canvas.drawCircle(left + width / 2, top + height / 2, 50, paint);
        run = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!run) run = true;
                return true;
            case MotionEvent.ACTION_UP:
                if(run) run = false;
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        Log.d(TAG, "run");
        Canvas canvas = null;
        while(run) {
            /**在这里加上线程安全锁**/
//            synchronized (mSurfaceHolder) {
                try {
                    /**拿到当前画布 然后锁定**/
                    canvas = mSurfaceHolder.lockCanvas(null);
                    draw(canvas);
                } finally {
                    if (canvas != null) {
                        /**绘制结束后解锁显示在屏幕上**/
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
//            }

        }
    }

}