package peterloos.de.bouncingballs;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by loospete on 01.08.2017.
 */

public class BouncingBallsView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private SurfaceHolder holder;
    private BouncingBallsAnimationThread thread;

    public BouncingBallsView(Context context) {
        super(context);
        Log.v(Globals.TAG, "c'tor BouncingBallsView");

        this.holder = getHolder();
        this.holder.addCallback(this);

        this.setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(Globals.TAG, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v(Globals.TAG, "surfaceChanged");

        if (this.thread == null) {
            this.thread = new BouncingBallsAnimationThread(holder);
            this.thread.setRunning(true);
            RectF size = new RectF(0, 0, width, height);
            this.thread.setSurfaceSize(size);
            this.thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(Globals.TAG, "surfaceDestroyed");

        boolean retry = true;
        this.thread.setRunning(false);
        while (retry) {
            try {
                this.thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        this.thread = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PointF center = new PointF(event.getX(), event.getY());
                BouncingBall ball = new BouncingBall(center);
                this.thread.addBall(ball);
                break;
        }
        return true;
    }

    public void pause() {
        if (this.thread != null) {
            this.thread.setPaused(true);
        }
    }

    public void resume() {
        if (this.thread != null) {
            this.thread.setPaused(false);
        }
    }
}

