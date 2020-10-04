package Geometry;

import MathLib.*;
import Raytracer.*;
import Material.*;

import java.util.Objects;


/**
 *
 */
public class Triangle extends Geometry {

    /**
     *
     */
    final public Point3 a;

    /**
     *
     */
    final public Point3 b;

    /**
     *
     */
    final public Point3 c;

    /**
     *
     */
    final public Normal3 aNormal;

    /**
     *
     */
    final public Normal3 bNormal;

    /**
     *
     */
    final public Normal3 cNormal;

    final private double u;
    final private double v;


    /**
     * Array containing all planes
     */

    /**
     * @param a
     * @param b
     * @param c
     * @param mat
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Material mat) {
        super(mat);
        this.a = a;
        this.b = b;
        this.c = c;


        //this.aNormal = a.asVector().x(c.asVector()).normalized().asNormal();
        //this.bNormal = a.asVector().x(c.asVector()).normalized().asNormal();
        //this.cNormal = a.asVector().x(c.asVector()).normalized().asNormal();

        this.u = 0;
        this.v = 0;

        this.aNormal = c.sub(a).x(b.sub(a)).normalized().asNormal().mul(-1);
        this.bNormal = c.sub(a).x(b.sub(a)).normalized().asNormal().mul(-1);
        this.cNormal = c.sub(a).x(b.sub(a)).normalized().asNormal().mul(-1);




    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param xText
     * @param yText
     * @param zText
     * @param mat
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Point2 xText, final Point2 yText, final Point2 zText, final Material mat) {
        super(mat);
        this.a = a;
        this.b = b;
        this.c = c;

        this.u = xText.x;
        this.v = xText.y;

        this.aNormal = a.asVector().x(c.asVector()).normalized().asNormal();
        this.bNormal = a.asVector().x(c.asVector()).normalized().asNormal();
        this.cNormal = a.asVector().x(c.asVector()).normalized().asNormal();

    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param aNormal
     * @param bNormal
     * @param cNormal
     * @param mat
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Normal3 aNormal, final Normal3 bNormal, final Normal3 cNormal, final Material mat) {
        super(mat);
        this.a = a;
        this.b = b;
        this.c = c;

        this.u = 0;
        this.v = 0;

        this.aNormal = aNormal;
        this.bNormal = bNormal;
        this.cNormal = cNormal;

    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param xText
     * @param yText
     * @param zText
     * @param aNormal
     * @param bNormal
     * @param cNormal
     * @param mat
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Point2 xText, final Point2 yText, final Point2 zText, final Normal3 aNormal, final Normal3 bNormal, final Normal3 cNormal, final Material mat) {
        super(mat);
        this.a = a;
        this.b = b;
        this.c = c;

        this.u = xText.x;
        this.v = xText.y;

        this.aNormal = aNormal;
        this.bNormal = bNormal;
        this.cNormal = cNormal;


    }

    /**
     * beta( a  - b ) + gamma( a - c ) + t*d = a - o
     *
     * @param r incoming ray
     * @return
     */
    public Hit hit(Ray r) {


        Mat3x3 bigA = new Mat3x3(
                a.x - b.x, a.x - c.x, r.d.x,
                a.y - b.y, a.y - c.y, r.d.y,
                a.z - b.z, a.z - c.z, r.d.z);


        Vector3 beam = a.sub(r.o);

        double beta = bigA.changeCol1(beam).determinant / bigA.determinant;
        if (beta >= 0 && beta <= 1) {

            double gamma = bigA.changeCol2(beam).determinant / bigA.determinant;
            if (gamma >= 0 && gamma <= 1) {
                if (gamma + beta <= 1) {



                    double alpha = 1.0 - beta - gamma;
                    final Normal3 n = aNormal.mul(alpha).add(bNormal.mul(beta)).add(cNormal.mul(gamma));


                    double t = bigA.changeCol3(beam).determinant / bigA.determinant;

                    Hit hit =  new Hit(t, r, this, n);
                    hit.u = this.u;
                    hit.v = this.v;
                    return hit;
                }
            }
        }

        /*
        double beta = bigA.changeCol1(beam).determinant / bigA.determinant;
        double gamma = bigA.changeCol2(beam).determinant / bigA.determinant;
        double alpha = 1.0 - beta - gamma;
        final Normal3 n = aNormal.mul(alpha).add(bNormal.mul(beta)).add(cNormal.mul(gamma));
        double t = bigA.changeCol3(beam).determinant / bigA.determinant;
        if(beta < 0 || gamma < 0|| beta + gamma > 1 || t < 0.00001){
            return null;
        }else {
            return new Hit(t, r, this, n);
        }

         */



        return null;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Double.compare(triangle.u, u) == 0 &&
                Double.compare(triangle.v, v) == 0 &&
                Objects.equals(a, triangle.a) &&
                Objects.equals(b, triangle.b) &&
                Objects.equals(c, triangle.c) &&
                Objects.equals(aNormal, triangle.aNormal) &&
                Objects.equals(bNormal, triangle.bNormal) &&
                Objects.equals(cNormal, triangle.cNormal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, aNormal, bNormal, cNormal, u, v);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", aNormal=" + aNormal +
                ", bNormal=" + bNormal +
                ", cNormal=" + cNormal +
                '}';
    }
}
