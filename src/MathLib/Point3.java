package MathLib;

import java.util.Objects;

/**
 * Point3 represents a point in a 3 dimensional space
 */
public class Point3 {

    /**
     * double value on x axis
     */
    public final double x;

    /**
     * double value on y axis
     */
    public final double y;

    /**
     * double value on z axis
     */
    public final double z;

    /**
     * Constructor of Point object
     * @param x value on x axis
     * @param y value on y axis
     * @param z value on z axis
     */
    public Point3(final double x, final double y, final double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * subtracts a Point from given Point and returns a Vector
     * If given Point is null IllegalArgumentException will be thrown
     * @param p Point
     * @return Vector
     */
    public Vector3 sub(final Point3 p){
        if(p == null) throw new IllegalArgumentException();
        return new Vector3(x - p.x, y - p.y, z-p.z);
    }

    /**
     * subtracts a Vector from Point and returns a Point
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector
     * @return Point
     */
    public Point3 sub(final Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return new Point3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * adds a Vector to a Point
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector
     * @return Point
     */
    public Point3 add(final Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return new Point3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Converts Point as Vector
     *
     * @return Vector
     */
    public Vector3 asVector() {
        return new Vector3(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3 point3 = (Point3) o;
        return Double.compare(point3.x, x) == 0 &&
                Double.compare(point3.y, y) == 0 &&
                Double.compare(point3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Point3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
