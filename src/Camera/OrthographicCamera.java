package Camera;

import MathLib.*;
import Raytracer.*;
import Sampling.SamplingPattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * OrthographicCamera
 */
public class OrthographicCamera extends Camera {
    /**
     * Zoomlevel
     * s < n further away
     * s > n closer
     */
    final public double s;

    /**
     * Constructor of Camera object
     * calculates a cartesian coordinates system based on the Camera
     * throws IllegalArgumentException if one of parameters is null
     * @param e position
     * @param g direction vector
     * @param t up vector
     * @param s scale factor
     * @param pattern number of rays per pixel
     */
    public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t, final double s, SamplingPattern pattern) {
        super(e, g, t, pattern);
        this.s = s;
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

        Set<Ray> set = new HashSet<Ray>();
        for(Point2 p : pattern.points){
            Vector3 d = this.w.mul(-1);
            double a = (double)w / (double) h;

            double partOne = s * ((x + p.x) - (w - 1) / 2) / (w - 1);
            Vector3 uVector = this.u.mul(partOne * a);

            double partTwo = s * ((y + p.y) - (h - 1) / 2) / (h - 1);
            Vector3 vVector = this.v.mul(partTwo);

            Point3 o = this.e.add(uVector.add(vVector));
            set.add(new Ray(o, d));
        }

        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrthographicCamera that = (OrthographicCamera) o;
        if(!e.equals(that.e) || !g.equals(that.g) || !t.equals(that.t) || !u.equals(that.u) || !v.equals(that.v) ||  !w.equals(that.w)) {
            return false;
        }
        return Double.compare(that.s, s) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(s);
    }

    @Override
    public String toString() {
        return "OrthographicCamera{" +
                "s=" + s +
                ", e=" + e +
                ", g=" + g +
                ", t=" + t +
                '}';
    }
}
