package Camera;

import MathLib.*;
import Raytracer.*;
import Sampling.SamplingPattern;

import java.util.Objects;
import java.util.Set;

/**
 * Camera object in 3 dimensional space
 */
public abstract class Camera {
    /**
     * eye position
     */
    public final Point3 e;

    /**
     * gaze direction vector
     */
    public final Vector3 g;

    /**
     * up vector
     */
    public final Vector3 t;


    /**
     * can be interpreted as x axis
     */
    public final Vector3 u;

    /**
     * can be interpreted as y axis
     */
    public final Vector3 v;

    /**
     * can be interpreted as z axis
     */
    public final Vector3 w;

    public final SamplingPattern pattern;

    /**
     * Constructor of Camera object
     * calculates a cartesian coordinates system based on the Camera
     * throws IllegalArgumentException if one of parameters is null
     * @param e position
     * @param g direction vector
     * @param t up vector
     * @param pattern number of rays per pixel
     */
    public Camera(final Point3 e, final Vector3 g, final Vector3 t, final SamplingPattern pattern){
        if(e == null || g == null || t == null) throw new IllegalArgumentException();
        this.e = e;
        this.g = g;
        this.t = t;
        this.pattern = pattern;
        w = g.mul(-1).normalized(); // multiply with -1 like in the hearing didnt work
        Vector3 tmp = this.t.x(w);
        u = tmp.normalized();
        v = w.x(u);
    }

    /**
     * calculates reflected ray of a pixel
     * @param w height of picture
     * @param h width of picture
     * @param x x coordinate of pixel
     * @param y y coordinate of pixel
     * @return set of rays
     */
    public abstract Set<Ray> rayFor(final int w, final int h, final int x, final int y);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return Objects.equals(e, camera.e) &&
                Objects.equals(g, camera.g) &&
                Objects.equals(t, camera.t) &&
                Objects.equals(u, camera.u) &&
                Objects.equals(v, camera.v) &&
                Objects.equals(w, camera.w);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e, g, t, u, v, w);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "e=" + e +
                ", g=" + g +
                ", t=" + t +
                ", u=" + u +
                ", v=" + v +
                ", w=" + w +
                '}';
    }
}
