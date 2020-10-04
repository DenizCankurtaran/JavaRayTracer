package MathLib;

import java.util.Objects;

/**
 * Represents a normal vector in 3 dimensional space
 */
public class Normal3 {

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
     * Constructor of Normal3 object
     * @param x value on x axis
     * @param y value on y axis
     * @param z value on z axis
     */
    public Normal3(final double x, final double y, final double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Multiplies every value on each axis with given value
     * @param c double value
     * @return Normal
     */
    public Normal3 mul(final double c){
        return new Normal3(c * x, c * y, c * z);
    }

    /**
     * Adds given Normal to Normal
     * If given Normal is null IllegalArgumentException will be thrown
     * @param n Normal
     * @return Normal
     */
    public Normal3 add(final Normal3 n){
        if(n == null) throw new IllegalArgumentException();
        return new Normal3(x + n.x, y + n.y, z + n.z);
    }

    public Vector3 asVector() {
        return new Vector3(x, y, z);
    }

    /**
     * Calculates the dot product of given Vector and Normal
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector
     * @return double
     */
    public double dot(Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Normal3 normal3 = (Normal3) o;
        return Double.compare(normal3.x, x) == 0 &&
                Double.compare(normal3.y, y) == 0 &&
                Double.compare(normal3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Normal3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
