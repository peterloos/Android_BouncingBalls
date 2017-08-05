package peterloos.de.bouncingballs;

        import android.graphics.Color;
        import android.graphics.PointF;
        import android.graphics.RectF;

        import java.util.ArrayList;
        import java.util.Random;

        import peterloos.de.utils.GeoVector;

/**
 * Created by Peter on 01.08.2017.
 */

public class BouncingBall {

    private static Random random = new Random();

    private PointF center;        // center of the circle to be drawn
    private RectF size;           // size of view
    private GeoVector direction;  // direction vector to move circle
    private GeoVector difference; // helper object

    private int color;

    public BouncingBall(PointF center) {

        this.center = center;
        this.size = new RectF();

        this.color = Color.BLUE;

        // setup direction vector
        float x = (random.nextBoolean()) ? random.nextFloat() : (-1) * random.nextFloat();
        float y = (random.nextBoolean()) ? random.nextFloat() : (-1) * random.nextFloat();
        this.direction = new GeoVector(x, y);
        this.direction.normalize();
        this.direction.scale(Globals.BallVelocity);

        this.difference = new GeoVector();
    }

    // getter / setter
    public void setSurfaceSize(RectF size) {
        this.size = size;
    }

    public PointF getCenter() {
        return this.center;
    }

    public GeoVector getDirection() {
        return this.direction;
    }

    public int getColor() {
        return color;
    }

    // public interface
    public void move() {

        this.center.x = this.center.x + this.direction.getX();
        this.center.y = this.center.y + this.direction.getY();
    }

    public void checkBoundaries() {

        if (this.center.y < Globals.getBallRadius()) {
            // top wall collision, invert direction vertical
            if (this.direction.getY() < 0) {
                this.direction.setY(-this.direction.getY());
            }
        } else if (this.center.y > (this.size.height() - Globals.getBallRadius())) {
            // bottom wall collision, invert direction vertical
            if (this.direction.getY() > 0) {
                this.direction.setY(-this.direction.getY());
            }
        } else if (this.center.x < Globals.getBallRadius()) {
            // left wall collision, invert direction horizontal
            if (this.direction.getX() < 0) {
                this.direction.setX(-this.direction.getX());
            }
        } else if (this.center.x > (this.size.width() - Globals.getBallRadius())) {
            // left wall collision, invert direction horizontal
            if (this.direction.getX() > 0) {
                this.direction.setX(-this.direction.getX());
            }
        }
    }

    public void checkCollisions(ArrayList<BouncingBall> balls) {

        boolean colorswap = false;

        for (int i = 0; i < balls.size(); i++) {
            if (this == balls.get(i))
                continue;

            // GeoVector difference = GeoVector.diff(this.getCenter(), balls.get(i).getCenter());
            GeoVector.diff(this.getCenter(), balls.get(i).getCenter(), this.difference);


            if (this.difference.length() <= (Globals.getBallRadius() * 2)) {
                this.getDirection().add(this.difference);
                this.direction.normalize();
                this.direction.scale(Globals.BallVelocity);

                colorswap = true;
            }
        }

        if (colorswap) {
            if (this.color == Color.BLUE)
                this.color = Color.RED;
            else if (this.color == Color.RED)
                this.color = Color.GREEN;
            else if (this.color == Color.GREEN)
                this.color = Color.YELLOW;
            else if (this.color == Color.YELLOW)
                this.color = Color.WHITE;
            else if (this.color == Color.WHITE)
                this.color = Color.BLUE;
        }
    }
}
