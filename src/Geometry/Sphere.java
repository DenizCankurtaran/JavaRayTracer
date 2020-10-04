package Geometry;

import Material.*;
import MathLib.*;
import Raytracer.*;

import java.util.Objects;

/**
 * implementation of sphere shape
 * inherits from abstract geometry class
 */
public class Sphere extends Geometry {

    /**
     * center of sphere
     */
    public final Point3 c;

    /**
     * radius of drawn sphere
     */
    public final double r;

    /**
     * constructor of Sphere Object
     * creates Sphere object and assigns center, radius and color
     * throws IllegalArgumentException if c or color is null
     * @param c center of sphere
     * @param r radius of sphere
     * @param mat color of sphere
     */
    public Sphere(final Point3 c, final double r, final Material mat){
        super(mat);
        if(c == null) throw new IllegalArgumentException();
        this.c = c;
        this.r = r;
    }

    /**
     * neutral constructor for transformable sphere
     * @param mat color
     */
    public Sphere(final Material mat){
        super(mat);
        this.c = new Point3(0, 0, 0);
        this.r = 1;
    }

    /**
     * calculation of Hit points
     * throws IllegalArgumentException if r is null
     * returns hit point with lowest positive t
     * returns null if no hit point is found
     * calculates normal vector through substracting
     * hit point and center of sphere
     *  @param r incoming ray
     * @return hit
     */
    public final Hit hit(final Ray r){
        if(r == null) throw new IllegalArgumentException();

        final double a =  r.d.dot(r.d);
        final double b = r.d.dot((r.o.sub(this.c)).mul(2.0));
        final double c = ( r.o.sub(this.c) ).dot( (r.o.sub(this.c)) ) - Math.pow(this.r, 2);

        final double d = Math.pow(b, 2) - (4.0 * a * c);


        double t;

        if(d < 0.0){
            return null;
        } else if(d == 0.0){
            t = -b / (2.0 * a);
        } else {
            final double tOne = (-b - Math.sqrt(d)) / (2.0 * a);
            final double tTwo = (-b + Math.sqrt(d)) / (2.0 * a);
            if(tOne > 0.0001) t = tOne;
            else if(tTwo > 0.0001) t = tTwo;
            else return null;
        }



        Normal3 n = r.at(t).sub(this.c).normalized().asNormal();
        Hit h = new Hit(t, r, this, n);
        // Point3 texturePoint = r.at(t);
        Vector3 tp = r.at(t).sub(this.c).normalized();

        // https://viclw17.github.io/2019/04/12/raytracing-uv-mapping-and-texturing/
        // https://en.wikipedia.org/wiki/UV_mapping
        double phi = Math.atan2(tp.x, tp.z);
        double theta = Math.asin(-tp.y);
        h.u = 0.5 + (phi / (2 * Math.PI));
        h.v = 0.5 - (theta  / Math.PI);

        // h.u = Math.acos((-texturePoint.z) / this.r) / Math.PI;
        // h.v = Math.acos(texturePoint.x / (this.r * Math.sin(Math.PI * h.u))) / (2 * Math.PI);

        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.r, r) == 0 &&
                Objects.equals(c, sphere.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, r);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "c=" + c +
                ", r=" + r +
                '}';
    }
}
