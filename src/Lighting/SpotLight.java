package Lighting;

import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;
import Raytracer.World;

import java.util.Objects;

/**
 * emits light from one point to one direction with a given angle
 */
public class SpotLight extends Light {

    /**
     * position of light source
     */
    final Point3 pos;

    /**
     * direction of light source
     */
    final Vector3 direction;

    /**
     * angle of light
     */
    final double halfAngle;

    /**
     * constructor of SpotLight Object
     * assigns position of light source, direction of light rays, angle and color of light
     * throws IllegalArgumentException if color, position or direction is null
     * @param color color of object
     * @param pos position of light source
     * @param direction direction of light source
     * @param halfAngle angle of light
     */
    public SpotLight(final Color color, final boolean castsShadows, final Point3 pos, final Vector3 direction, final double halfAngle){
        super(color, castsShadows);
        if(pos == null || direction == null) throw new IllegalArgumentException();
        this.pos = pos;
        this.direction = direction;
        this.halfAngle = halfAngle;
    }

    /**
     * calculation if point gets illuminated by light
     * compares angles from hit and light with hit angle and camera angle
     * Tip came from group 'Emanneppurg'
     * returns true if it does
     *  @param pos position of given point
     */
    public boolean illuminates(Point3 pos, World world){
        Vector3 a = pos.sub(this.pos).normalized();
        double angle = Math.acos((direction.dot(a))/(direction.magnitude * a.magnitude));
        if(angle < halfAngle){
            Ray ray = new Ray(pos, directionFrom(pos));
            Hit hit = world.hit(ray);
            if(hit == null) return true;
            if (hit.t > ray.tOf(this.pos) ) return true;
        }
        return false;
    }

    /**
     * calculates the vector for the light source from point
     * @param point source point for calculation
     * @return normalized vector that shows to light source
     */
    public Vector3 directionFrom(final Point3 point){
        if(point == null) throw new IllegalArgumentException();
        return this.pos.sub(point).normalized();
        //return point.sub(pos).normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotLight spotLight = (SpotLight) o;
        return Double.compare(spotLight.halfAngle, halfAngle) == 0 &&
                Objects.equals(pos, spotLight.pos) &&
                Objects.equals(direction, spotLight.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, direction, halfAngle);
    }

    @Override
    public String toString() {
        return "SpotLight{" +
                "pos=" + pos +
                ", direction=" + direction +
                ", halfAngle=" + halfAngle +
                '}';
    }
}
