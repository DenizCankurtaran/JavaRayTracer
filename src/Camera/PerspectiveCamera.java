package Camera;

import MathLib.*;
import Raytracer.*;
import Sampling.SamplingPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * camera object with additional angle setting
 */
public class PerspectiveCamera extends Camera{
    /**
     * angle of camera
     */
    final public double angle;

    /**
     * Constructor of Camera object
     * calculates a cartesian coordinates system based on the Camera
     * throws IllegalArgumentException if one of parameters is null
     * @param e position
     * @param g direction vector
     * @param t up vector
     * @param angle angle of view
     * @param pattern number of rays per pixel
     */
    public PerspectiveCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle, SamplingPattern pattern){
        super(e, g, t, pattern);
        this.angle = angle;
    }

    /**
     * calculates set of rays on one pixel
     * number of rays is defined through SamplingPattern in Constructor
     * @param w height of picture
     * @param h width of picture
     * @param x x coordinate of pixel
     * @param y y coordinate of pixel
     * @return ray of single pixel
     */
    public Set<Ray> rayFor(final int w, final int h, final int x, final int y){
        Point3 o = this.e;

        Set<Ray> set = new HashSet<Ray>();
        for(Point2 p: pattern.points){
            double partOne = (h / 2.0) / Math.tan(angle / 2.0);
            double partTwo = (x + p.x)  - ((w - 1.0) / 2.0);
            double partThree = (y + p.y) - ((h - 1.0) / 2.0);

            Vector3 uVector = this.u.mul(partTwo);
            Vector3 vVector = this.v.mul(partThree);

            Vector3 r = this.w.mul(-1).mul(partOne).add(uVector).add(vVector);
            Vector3 d = r.normalized();
            set.add(new Ray(o, d));
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerspectiveCamera that = (PerspectiveCamera) o;
        if(!e.equals(that.e) || !g.equals(that.g) || !t.equals(that.t) || !u.equals(that.u) || !v.equals(that.v) ||  !w.equals(that.w)) {
            return false;
        }
        return Double.compare(that.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(angle);
    }

    @Override
    public String toString() {
        return "PerspectiveCamera{" +
                "angle=" + angle +
                ", e=" + e +
                ", g=" + g +
                ", t=" + t +
                '}';
    }
}
