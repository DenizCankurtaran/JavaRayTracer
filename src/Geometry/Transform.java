package Geometry;

import MathLib.*;
import Raytracer.Ray;

import java.util.Objects;

/**
 * Transforms 3 dimensional objects through 4 dimensional projection
 */
public class Transform {
    /**
     * Transformation matrix
     */
    public final Mat4x4 m;

    /**
     * Inverse Transformation Matrix
     */
    public final Mat4x4 i;

    /**
     * public Constructor initializes identity transformation and inverse Matrix
     */
    public Transform() {
        m = new Mat4x4(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        i = new Mat4x4(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    /**
     * private Constructor
     *
     * @param m previously projected Transformation Matrix
     * @param i previously projected Inverse Matrix
     */
    private Transform(final Mat4x4 m, final Mat4x4 i) {
        if (m == null || i == null) throw new IllegalArgumentException();
        this.m = m;
        this.i = i;
    }

    /**
     * simple translation to given Vector position
     *
     * @param v to which position
     * @return projected Transformation
     */
    public Transform translation(final Vector3 v) {
        Mat4x4 m =
                new Mat4x4(1, 0, 0, v.x,
                        0, 1, 0, v.y,
                        0, 0, 1, v.z,
                        0, 0, 0, 1);
        Mat4x4 i = new Mat4x4(1, 0, 0, -v.x,
                0, 1, 0, -v.y,
                0, 0, 1, -v.z,
                0, 0, 0, 1);

        return new Transform(this.m.mul(m), i.mul(this.i));
    }

    /**
     * scales Object with given Vector
     *
     * @param v scale x, y, z to which degree
     * @return projected Transformation
     */
    public Transform scale(final Vector3 v) {
        Mat4x4 m =
                new Mat4x4(v.x, 0, 0, 0,
                        0, v.y, 0, 0,
                        0, 0, v.z, 0,
                        0, 0, 0, 1);
        Mat4x4 i = new Mat4x4(1 / v.x, 0, 0, 0,
                0, 1 / v.y, 0, 0,
                0, 0, 1 / v.z, 0,
                0, 0, 0, 1);

        return new Transform(this.m.mul(m), i.mul(this.i));
    }

    /**
     * rotation on x axis to given angle
     *
     * @param angle double
     * @return projected Transformation
     */
    public Transform rotateX(final double angle) {
        Mat4x4 m = new Mat4x4(1, 0, 0, 0,
                0, Math.cos(angle), -Math.sin(angle), 0,
                0, Math.sin(angle), Math.cos(angle), 0,
                0, 0, 0, 1);

        Mat4x4 i = new Mat4x4(1, 0, 0, 0,
                0, Math.cos(angle), Math.sin(angle), 0,
                0, -Math.sin(angle), Math.cos(angle), 0,
                0, 0, 0, 1);
        return new Transform(this.m.mul(m), i.mul(this.i));
    }

    /**
     * rotation on y axis to given angle
     *
     * @param angle double
     * @return projected Transformation
     */
    public Transform rotateY(final double angle) {
        Mat4x4 m = new Mat4x4(Math.cos(angle), 0, Math.sin(angle), 0,
                0, 1, 0, 0,
                -Math.sin(angle), 0, Math.cos(angle), 0,
                0, 0, 0, 1);
        Mat4x4 i = new Mat4x4(Math.cos(angle), 0, -Math.sin(angle), 0,
                0, 1, 0, 0,
                Math.sin(angle), 0, Math.cos(angle), 0,
                0, 0, 0, 1);
        return new Transform(this.m.mul(m), i.mul(this.i));
    }

    /**
     * rotation on z axis to given angle
     *
     * @param angle double
     * @return projected Transformation
     */
    public Transform rotateZ(final double angle) {
        Mat4x4 m = new Mat4x4(Math.cos(angle), -Math.sin(angle), 0, 0,
                Math.sin(angle), Math.cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        Mat4x4 i = new Mat4x4(Math.cos(angle), Math.sin(angle), 0, 0,
                -Math.sin(angle), Math.cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
        return new Transform(this.m.mul(m), i.mul(this.i));
    }

    /**
     * Transforms the ray
     *
     * @param ray
     * @return
     */
    public Ray mul(final Ray ray) {
        Ray newR = new Ray(i.mul(ray.o), i.mul(ray.d));
        // t is missing
        // transformed ray and untransformed ray hit calculation return t?
        return newR;
    }

    /**
     * @param n
     * @return
     */
    public Normal3 mul(final Normal3 n) {
        return this.i.transpose().mul(n.asVector()).normalized().asNormal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Objects.equals(m, transform.m) &&
                Objects.equals(i, transform.i);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, i);
    }

    @Override
    public String toString() {
        return "Transform{" +
                "m=" + m +
                ", i=" + i +
                '}';
    }
}
