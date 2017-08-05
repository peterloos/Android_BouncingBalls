package peterloos.de.utils;

/**
 * Created by Peter on 01.08.2017.
 */

public class Timer {

    public final static long PreferredFrameRate = 40;                // frames per second
    public final static long SleepTime = 1000 / PreferredFrameRate;  // 25 msecs, if 40 frames per second

    private long start;

    public Timer () {
        this.setStart();
    }

    public long getTimeElapsed() {
        return System.currentTimeMillis() - this.start;
    }

    public void setStart() {
        this.start = System.currentTimeMillis();
    }
}