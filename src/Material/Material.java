package Material;

import Raytracer.*;

/**
 * abstract class for materials
 */
public abstract class Material {

    /**
     * returns Color for Hit
     * @param hit hit object
     * @param world for getting the lights
     * @return
     */
    public abstract Color colorFor(Hit hit, World world, final int counter);

}
