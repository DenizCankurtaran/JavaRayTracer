package Raytracer;

import MathLib.Point3;
import MathLib.Vector3;

import java.util.Objects;

/**
 * implementation of a ray
 */

public class Ray {

    /**
     * point of ray origin
     */
    public final Point3 o;

    /**
     * direction of ray
     */
    public final Vector3 d;

    /**
     * constructor of ray
     * @param o point of origin
     * @param d direction of ray
     */
    public Ray(final Point3 o, final Vector3 d){
        this.o = o;
        this.d = d.normalized();
    }

    /**
     * calculates point at ray with factor t
     * p = o + t * d
     * @param t factor to reach a point on ray
     * @return point of ray at t
     */
    public Point3 at(final double t){
        return o.add(d.mul(t));
    }

    /**
     * return the factor needed to reach point p on ray
     * t = (p - o) / d
     * @param p point on ray
     * @return factor to reach point on ray
     * throws IllegalArgumentException if p is null
     */
    public double tOf(Point3 p){
        if(p == null) throw new IllegalArgumentException();
        return p.sub(o).magnitude/d.magnitude;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        Ray ray = (Ray) o1;
        return Objects.equals(o, ray.o) &&
                Objects.equals(d, ray.d);
    }

    @Override
    public int hashCode() {
        return Objects.hash(o, d);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "o=" + o +
                ", d=" + d +
                '}';
    }
}
