package peterloos.de.bouncingballs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import peterloos.de.utils.Timer;

/**
 * Created by Peter on 01.08.2017.
 */

public class BouncingBallsAnimationThread extends Thread {

    private RectF size;

    private final SurfaceHolder holder;
    private boolean isRunning;
    private Paint paintBorder;

    private ArrayList<BouncingBall> balls;

    private boolean paused;

    public BouncingBallsAnimationThread(SurfaceHolder holder) {
        this.holder = holder;
        this.isRunning = true;

        this.paintBorder = new Paint();
        this.paintBorder.setStyle(Paint.Style.STROKE);
        this.paintBorder.setStrokeWidth(10);
        this.paintBorder.setColor(Color.BLUE);

        this.balls = new ArrayList<>();
        this.paused = false;

        this.size = new RectF(0, 0, 0, 0);
    }

    // getter / setter
    public void setRunning(boolean b) {
        this.isRunning = b;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setSurfaceSize(RectF size) {
        synchronized (holder) {
            this.size = size;
        }
    }

    // public interface
    public void run() {

        Timer frameTimer = new Timer();

        while (this.isRunning) {

            // store start time of this frame
            frameTimer.setStart();

            if (!this.paused) {
                this.update();
                this.draw();
            }

            // calculate frame update time and sleep, if necessary
            long rest = Timer.SleepTime - frameTimer.getTimeElapsed();
            if (rest > 0) {
                this.pause(rest);
            }
        }
    }

    private void update() {

        synchronized (holder) {
            for (BouncingBall ball : this.balls) {
                ball.move();
                ball.checkBoundaries();
                ball.checkCollisions(this.balls);
            }
        }
    }

    private void draw() {
        Canvas canvas = null;
        try {
            canvas = this.holder.lockCanvas();
            if (canvas != null) {
                // surface can be edited
                canvas.drawColor(Color.BLACK);
                this.drawBalls(canvas);
            }
        } finally {
            if (canvas != null) {
                this.holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawBalls(Canvas canvas) {

        synchronized (this.holder) {
            for (BouncingBall ball : this.balls) {
                PointF center = ball.getCenter();
                this.paintBorder.setColor(ball.getColor());
                canvas.drawCircle(center.x, center.y, Globals.getBallRadius(), this.paintBorder);
            }
        }
    }

    private void pause(long pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addBall(BouncingBall ball) {
        synchronized (holder) {
            ball.setSurfaceSize(this.size);
            this.balls.add(ball);
        }
    }
}
