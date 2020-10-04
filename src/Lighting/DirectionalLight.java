package Lighting;

import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;
import Raytracer.World;

import java.util.Objects;

/**
 * represents sun light // emits light from one direction to scene
 */
public class DirectionalLight extends Light {

    /**
     * position of light source
     */
    final Vector3 direction;

    /**
     * constructor of DirectionalLight Object
     * assigns direction of light source and color of light object
     * throws IllegalArgumentException if color or direction is null
     * @param color color of object
     * @param direction direction of light source
     */
    public DirectionalLight(final Color color, final boolean castsShadows, final Vector3 direction){
        super(color, castsShadows);
        if(direction == null) throw new IllegalArgumentException();
        this.direction = direction;
    }

    /**
     * calculation if point gets illuminated by light
     * returns true if it does
     *  @param pos position of given point
     */
    public boolean illuminates(Point3 pos, World world){
        Ray r = new Ray(pos, directionFrom(pos));
        Hit hit = world.hit(r);
        if(hit == null){
            return true;
        }
        return false;
    }

    /**
     * calculates the vector for the light source from point
     * returns vector that shows to light source
     *  @param point source point for calculation
     */
    public Vector3 directionFrom(final Point3 point){
        return point.sub(direction).asVector().normalized();
        //return direction.asPoint().sub(point).asPoint().asVector().normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionalLight that = (DirectionalLight) o;
        return Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }

    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + direction +
                '}';
    }
}
