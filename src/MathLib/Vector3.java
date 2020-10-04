package MathLib;

import java.util.Objects;

/**
 * Vector3 represents a vector in 3 dimensional space
 */
public class Vector3 {

    /**
     * value on x axis
     */
    public final double x;

    /**
     * value on y axis
     */
    public final double y;

    /**
     * value on z axis
     */
    public final double z;

    /**
     * magnitude of the whole vector
     */
    public final double magnitude;


    /**
     * Constructor of Vector object
     * also calculates the magnitude
     *
     * @param x value on x axis
     * @param y value on y axis
     * @param z value on z axis
     */
    public Vector3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.magnitude = Math.sqrt((x * x) + (y * y) + (z * z));

    }

    /**
     * Adds given Vector on top of Vector
     * If given Vector is null IllegalArgumentException will be thrown
     *
     * @param v Vector
     * @return Vector
     */
    public Vector3 add(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException();
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Adds given Normal vector to Vector
     * If given Normal is null IllegalArgumentException will be thrown
     *
     * @param n Normal
     * @return Vector
     */
    public Vector3 add(final Normal3 n) {
        if (n == null) throw new IllegalArgumentException();
        return new Vector3(x + n.x, y + n.y, z + n.z);
    }

    /**
     * Subtracts given Normal vector from Vector
     * If given Normal is null IllegalArgumentException will be thrown
     *
     * @param n Normal
     * @return Vector
     */
    public Vector3 sub(final Normal3 n) {
        if (n == null) throw new IllegalArgumentException();
        return new Vector3(x - n.x, y - n.y, z - n.z);
    }

    /**
     * Multiplies every value on each axis with given value
     *
     * @param c value to multiply
     * @return Vector
     */
    public Vector3 mul(final double c) {
        return new Vector3(c * x, c * y, c * z);
    }

    /**
     * Calculates the dot product of given Vector3 and Vector3
     * If given Vector is null IllegalArgumentException will be thrown
     *
     * @param v Vector
     * @return double
     */
    public double dot(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException();
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    /**
     * Calculates the dot product of given Normal3 and Vector3
     * If given Normal is null IllegalArgumentException will be thrown
     *
     * @param n Normal3
     * @return double
     */
    public double dot(final Normal3 n) {
        if (n == null) throw new IllegalArgumentException();
        return (x * n.x) + (y * n.y) + (z * n.z);
    }

    /**
     * Calculates the normalized Vector
     *
     * @return Vector
     */
    public Vector3 normalized() {
        return mul(1 / magnitude);
    }

    /**
     * Converts Vector as Normal
     *
     * @return Normal3
     */
    public Normal3 asNormal() {
        return new Normal3(x, y, z);
    }

    /**
     * Converts Vector as Point
     *
     * @return Point
     */
    public Point3 asPoint() {
        return new Point3(x, y, z);
    }

    /**
     * Reflects Vector on Normal and returns reflected Vector
     * If given Normal is null IllegalArgumentException will be thrown
     *
     * @param n Normal
     * @return Vector
     */
    public Vector3 reflectedOn(final Normal3 n) {
        if (n == null) throw new IllegalArgumentException();
        double dotProduct = 2 * this.dot(n);
        Normal3 projectedVector = n.mul(dotProduct);
        Vector3 v = this.mul(-1).normalized();
        return v.add(projectedVector);
    }

    /**
     * Cross product of given Vector and Vector
     * If given Vector is null IllegalArgumentException will be thrown
     *
     * @param v Vector
     * @return Vector
     */
    public Vector3 x(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException();
        double new_x = (y * v.z) - (z * v.y);
        double new_y = (z * v.x) - (x * v.z);
        double new_z = (x * v.y) - (y * v.x);

        return new Vector3(new_x, new_y, new_z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Double.compare(vector3.x, x) == 0 &&
                Double.compare(vector3.y, y) == 0 &&
                Double.compare(vector3.z, z) == 0 &&
                Double.compare(vector3.magnitude, magnitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, magnitude);
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", magnitude=" + magnitude +
                '}';
    }
}
