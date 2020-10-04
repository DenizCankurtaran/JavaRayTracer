package Material;
import Lighting.*;
import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.*;
import Texture.Texture;

/**
 * LambertMaterial represents how Material absorbs light
 * light gets reflected equally
 */
public class LambertMaterial extends Material {

    /**
     * diffuse color
     */
    public Texture texture;

    public Color color;

    /**
     * Constructor of LambertMaterial
     * @param texture diffuse color
     */
    public LambertMaterial(final Texture texture){
        if(texture == null) throw new IllegalArgumentException();
        this.texture = texture;
    }

    /**
     * calculates Lambert color
     * l = vector to lightSource
     * n = normal to surface
     * c = cr * (ca * cl * max(0, n * l))
     * also checks if geometry has a shadow
     * @param hit hit of geometry
     * @param world World
     * @return Lambert color
     */
    public Color colorFor(final Hit hit, final World world, final int counter){
        //tmp?
        Color c = texture.colorFor(hit.u, hit.v).mul(world.ambientLight);
        Point3 point3 = hit.ray.at(hit.t);

        for(Light light: world.lightSources){
            if( !light.illuminates(point3, world) && light.castsShadows){
                continue;
            }

            Vector3 l = light.directionFrom(point3).normalized();

            c = texture.colorFor(hit.u, hit.v).mul(world.ambientLight.add(light.color.mul(Math.max(0, hit.n.dot(l)))));

        }

        return c;
    }

}
