package peterloos.de.utils;

import android.graphics.PointF;

/**
 * Created by loospete on 01.08.2017.
 */

public class GeoVector {

    private float x;
    private float y;

    // c'tor
    public GeoVector() {
        this (0F, 0F);
    }

    public GeoVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // getter/setter
    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    // public interface
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void scale(float f) {
        this.x = f * this.x;
        this.y = f * this.y;
    }

    public void normalize() {
        this.scale((float) 1.0 / this.length());
    }

    public void add(GeoVector v) {
        this.x += v.x;
        this.y += v.y;
    }

    public static GeoVector diff(PointF p1, PointF p2) {
        return new GeoVector(p1.x - p2.x, p1.y - p2.y);
    }

    public static void diff(PointF p1, PointF p2, GeoVector result) {
        result.setX(p1.x - p2.x);
        result.setY(p1.y - p2.y);
    }
}
