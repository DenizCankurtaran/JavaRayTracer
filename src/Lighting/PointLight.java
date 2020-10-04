package Lighting;

import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;
import Raytracer.World;

import java.util.Objects;

/**
 * object that emits light in every direction from one point
 */
public class PointLight extends Light {

    /**
     * position of light source
     */
    final Point3 pos;

    /**
     * constructor of PointLight Object
     * assigns position to light source and color of light object
     * throws IllegalArgumentException if color or position is null
     * @param color color of object
     * @param pos position of light source
     */
    public PointLight(final Color color, final boolean castsShadows, final Point3 pos){
        super(color, castsShadows);
        if(pos == null) throw new IllegalArgumentException();
        this.pos = pos;
    }

    /**
     * calculation if point gets illuminated by light
     * returns true if it does
     *  @param pos position of given point
     */
    public boolean illuminates(Point3 pos, World world){
        if(pos == null) throw new IllegalArgumentException();
        Ray ray = new Ray(pos, directionFrom(pos));
        Hit hit = world.hit(ray);
        if (hit == null) return true;
        if (hit.t + 0.00001 > ray.tOf(this.pos) ) return true;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointLight that = (PointLight) o;
        return Objects.equals(pos, that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }

    @Override
    public String toString() {
        return "PointLight{" +
                "pos=" + pos +
                '}';
    }
}
