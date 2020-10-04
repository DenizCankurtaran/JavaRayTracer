package Geometry;

import MathLib.*;
import Raytracer.*;
import Material.*;

import java.util.Objects;

/**
 * implementation of plane shape
 * inherits from abstract geometry class
 */
public class Plane extends Geometry {

    /**
     * point of origin
     */
    public final Point3 a;

    /**
     * 3-dimensional vector that gets projected on point of origin
     */
    public final Normal3 n;

    /**
     * constructor of Plane Object
     * creates Plane object and assigns point of origin, vector and color
     * throws IllegalArgumentException if a, n or color is null
     * @param a point of origin
     * @param n vector of Plane
     * @param mat color of Plane
     */
    public Plane(final Point3 a, final Normal3 n, final Material mat){
        super(mat);
        if(a == null || n == null) throw new IllegalArgumentException();
        this.a = a;
        this.n = n;
    }

    /**
     * neutral constructor for transformable plane
     * @param mat color
     */
    public Plane(final Material mat){
        super(mat);
        this.a = new Point3(0, 0, 0);
        this.n = new Normal3(0, 1, 0);
    }

    /**
     * calculation of Raytracer hit points
     * t = ((a - o) * n) / (d * n)
     * returns hit point t
     * returns null if no hit point is found
     *  @param r incoming ray
     * @return hit
     */
    public Hit hit(final Ray r){
        if(r.d.dot(n) != 0.0){
            final double t = (a.sub(r.o).dot(n)) / (r.d.dot(n));
            if(t < 0.000001) return null;
            Point3 texturePoint = r.at(t);
            Hit h = new Hit(t, r, this, n);
            h.u = texturePoint.x;
            h.v = -texturePoint.z;
            //return new Hit(t, r, this, n);
            return h;
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(a, plane.a) &&
                Objects.equals(n, plane.n);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, n);
    }

    @Override
    public String toString() {
        return "Plane{" +
                "a=" + a +
                ", n=" + n +
                '}';
    }
}
