package Material;

import Raytracer.*;
import Texture.Texture;

import java.util.Objects;

/**
 * basic color material
 */
public class SingleColorMaterial extends Material {

    /**
     * color of material
     */
    public final Texture texture;

    /**
     * Color of Geometry
     * @param texture
     */
    public SingleColorMaterial(final Texture texture){
        if(texture == null) throw new IllegalArgumentException();
        this.texture = texture;
    }

    /**
     * returns color of hit object
     * @param hit hit object
     * @param world world object // needed for light calculation
     * @return color
     */
    public Color colorFor(Hit hit, World world, final int counter){
        return this.texture.colorFor(0,0).mul(world.ambientLight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleColorMaterial that = (SingleColorMaterial) o;
        return Objects.equals(texture, that.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture);
    }

    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "color=" + texture +
                '}';
    }
}
