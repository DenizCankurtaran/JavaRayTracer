package Geometry;

import Material.*;
import Raytracer.*;

/**
 * abstract class for geometry object
 */
public abstract class   Geometry {

    /**
     * material of geometry
     */
    public final Material mat;

    /**
     * constructor of geometry Object
     * assigns color to shape
     * throws IllegalArgumentException if color is null
     * @param mat material color of object
     */
    public Geometry(final Material mat){
        if(mat == null) throw new IllegalArgumentException();
        this.mat = mat;
    }

    /**
     * calculation of Hit points
     * returns hit point with lowest positive t
     * returns null if no hit point is found
     *  @param r incoming ray
     */
    public abstract Hit hit(final Ray r);
}
