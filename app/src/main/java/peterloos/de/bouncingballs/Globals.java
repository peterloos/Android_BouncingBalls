package peterloos.de.bouncingballs;

import android.graphics.Point;

/**
 * Created by Peter on 01.08.2017.
 */

public class Globals {

    public static final String TAG = "PeLo";

    private static Point size = null;

    private static final float NumBallsPerLine = 30;
    private static float ballRadius = -1;
    private static boolean radiusCalculated = false;

    public static final float BallVelocity = 10;

    public static void setDisplaySize (Point size) {
        Globals.size = size;
    }

    public static float getBallRadius () {

        if (radiusCalculated) {
            return ballRadius;
        }

        if (size != null)  {
            ballRadius = size.x / NumBallsPerLine;
            return ballRadius;
        }

        return 800 / NumBallsPerLine;
    }
}

