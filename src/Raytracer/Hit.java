package Raytracer;

import Geometry.Geometry;
import MathLib.Normal3;

import java.util.Objects;

/**
 * representation of a hit point
 */
public class Hit {

    /**
     * factor where the ray and geometry hit each other
     */
    public final double t;

    /**
     * ray of pixel
     */
    public final Ray ray;

    /**
     * geometry to be hit by ray
     */
    public final Geometry geo;

    /**
     * normal vector of hit object
     */
    public final Normal3 n;

    public double u = 0;

    public double v = 0;



    /**
     * constructor for Hit Object
     * @param t factor where the ray and geometry hit each other
     * @param ray ray of pixel
     * @param geo geometry to be hit by ray
     */
    public Hit(final double t, final Ray ray, final Geometry geo, final Normal3 n){
        this.t = t;
        this.ray = ray;
        this.geo = geo;
        this.n = n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hit hit = (Hit) o;
        return Double.compare(hit.t, t) == 0 &&
                Objects.equals(ray, hit.ray) &&
                Objects.equals(geo, hit.geo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, ray, geo);
    }

    @Override
    public String toString() {
        return "Hit{" +
                "t=" + t +
                ", ray=" + ray +
                ", geo=" + geo +
                '}';
    }
}
