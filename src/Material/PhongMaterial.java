package Material;

import Lighting.Light;
import MathLib.Point3;
import Raytracer.*;
import Texture.Texture;

/**
 * calculates Light reflection on geometry
 */
public class PhongMaterial extends Material{

    /**
     * Color of geometry
     */
    public final Texture texture;

    /**
     * Color of reflected Light
     */
    public final Color specular;

    /**
     * Power of reflection
     * stronger < exponent < weaker
     */
    public final int exponent;


    /**
     * Constructor of PhongMaterial
     * throws IllegalArgumentException if diffuse or specular is null
     * @param texture Texture of geometry
     * @param specular Color of reflected Light
     * @param exponent Power of reflection
     */
    public PhongMaterial(final Texture texture, final Color specular, final int exponent){
        if(texture == null || specular == null) throw new IllegalArgumentException();
        this.texture = texture;
        this.specular = specular;
        this.exponent = exponent;
    }

    /**
     * Calculation after Phong
     * c = cr(ca + cl * max(0, n * l) + cl * max(0, e * rl)^exponent )
     * also checks if geometry has a shadow
     * @param hit hit of geometry
     * @param world World
     * @return color
     */
    public Color colorFor(Hit hit, World world, final int counter) {
        Color p1,p2,p3,p4,sum, fin = new Color(0,0,0);

        Point3 point3 = hit.ray.at(hit.t);
        Color diffuse = texture.colorFor(hit.u, hit.v);
        for(Light l : world.lightSources){
            if(!l.illuminates(point3, world) && l.castsShadows){
                continue;
            }

            p1 = diffuse.mul(l.color); // diffuse reflection color * ambient light
            p2 = p1.mul(Math.max(0,hit.n.dot(l.directionFrom(hit.ray.at(hit.t)).normalized()))); // diffuse reflection color * maximum between 0 and (normal vector*light source vector)
            p3 = specular.mul(l.color); // specular reflection color * light source color
            p4 = p3.mul(Math.pow(Math.max(0,world.cam.e.sub(hit.ray.at(hit.t)).normalized().dot(l.directionFrom(hit.ray.at(hit.t)).reflectedOn(hit.n))), exponent));// p3 * maximum between 0 and (eye vector * reflected light source vector) factorized by exponent(P)
            sum = p2.add(p4); // add both parts (p2 + p4)
            fin = fin.add(sum); // add all sums

        }
        return diffuse.mul(world.ambientLight).add(fin); // multiply diffuse reflection color with ambient light color and add sum
    }


}
