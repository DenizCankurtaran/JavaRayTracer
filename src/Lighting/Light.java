package Lighting;

import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.World;

/**
 * abstract class for Light object
 */
public abstract class Light {

    /**
     * color of light
     */
    public final Color color;

    /**
     * whether or not the light casts shadows
     */
    public final boolean castsShadows;

    /**
     * constructor of Light Object
     * assigns color light
     * throws IllegalArgumentException if color is null
     * @param color color of object
     */
    public Light(final Color color, final boolean castsShadows){
        if(color == null) throw new IllegalArgumentException();
        this.color = color;
        this.castsShadows = castsShadows;
    }

    /**
     * calculation if point gets illuminated by light
     * returns true if it does
     *  @param pos position of given point
     */
    public abstract boolean illuminates(final Point3 pos, World world);

    /**
     * calculates the vector for the light source from point
     * returns vector that shows to light source
     *  @param point source point for calculation
     */
    public abstract Vector3 directionFrom(final Point3 point);

}
