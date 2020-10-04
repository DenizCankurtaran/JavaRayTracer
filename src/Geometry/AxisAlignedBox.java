package Geometry;

import Material.*;
import MathLib.*;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * AxisAlignedBox
 * Box with aligned axes in 3 dimensions
 */
public class AxisAlignedBox extends Geometry {

    /**
     * left bottom distant point
     */
    final public Point3 a;

    /**
     * right top close point
     */
    final public Point3 b;

    /**
     * Array containing all planes
     */
    final public Plane[] planeList;

    /**
     * Constructor of AxisAlignedBox
     * Calculates all planes with 2 given points
     * @param a Point3 left bottom distant point
     * @param b Point3 right top close point
     * @param mat color
     */
    public AxisAlignedBox(final Point3 a, final Point3 b, final Material mat) {
        super(mat);
        this.a = a;
        this.b = b;
        planeList = new Plane[6];

        planeList[0] = new Plane(b, new Normal3(0, 0, 1), mat);
        planeList[1] = new Plane(a, new Normal3(0, -1, 0), mat);
        planeList[2] = new Plane(a, new Normal3(-1, 0, 0), mat);
        planeList[3] = new Plane(b, new Normal3(1, 0, 0), mat);
        planeList[4] = new Plane(b, new Normal3(0, 1, 0), mat);
        planeList[5] = new Plane(a, new Normal3(0, 0, -1), mat);
    }

    /**
     * neutral constructor for transformable AAB
     * @param mat color
     */
    public AxisAlignedBox(final Material mat) {
        super(mat);
        this.a = new Point3(-0.5, -0.5, -0.5);
        this.b = new Point3(0.5, 0.5, 0.5);

        planeList = new Plane[6];
        planeList[0] = new Plane(b, new Normal3(0, 0, 1), mat);
        planeList[1] = new Plane(a, new Normal3(0, -1, 0), mat);
        planeList[2] = new Plane(a, new Normal3(-1, 0, 0), mat);
        planeList[3] = new Plane(b, new Normal3(1, 0, 0), mat);
        planeList[4] = new Plane(b, new Normal3(0, 1, 0), mat);
        planeList[5] = new Plane(a, new Normal3(0, 0, -1), mat);

    }

    /**
     * checks if hit point is within Box created through point a and b
     * slightly increases check value because of float value to ensure every hit
     * is within box
     * @param r incoming ray
     * @return hit
     */
    public Hit hit(Ray r) {
        // hits all plane and adds hits to list
        ArrayList<Hit> hits = new ArrayList<Hit>();
        for (Plane plane : planeList) {
            if ((r.o.sub(plane.a)).dot(plane.n) > 0) {
                Hit hit = plane.hit(r);
                if (hit != null) hits.add(hit);
            }
        }

        Hit maxHit = hits.stream().max(new Comparator<Hit>() {
            @Override
            public int compare(Hit hit, Hit t1) {
                return Double.compare(hit.t, t1.t);
            }
        }).orElse(null);

        if (maxHit == null) return null;

        Point3 at = r.at(maxHit.t);
        if (((at.x + 0.00001) >= a.x && at.x <= (b.x + 0.00001)) && ((at.y + 0.00001) >= a.y && at.y <= (b.y + 0.00001)) && ((at.z + 0.00001) >= a.z && at.z <= (b.z + 0.00001))) {
            maxHit.u = at.x;
            maxHit.v = -at.z;
            return maxHit;
        }

        return null;
    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "a=" + a +
                ", b=" + b +
                ", planeList=" + Arrays.toString(planeList) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AxisAlignedBox that = (AxisAlignedBox) o;
        return Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Arrays.equals(planeList, that.planeList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(a, b);
        result = 31 * result + Arrays.hashCode(planeList);
        return result;
    }
}
