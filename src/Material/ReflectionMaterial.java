package Material;

import Geometry.Geometry;
import Lighting.Light;
import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;
import Raytracer.World;
import Texture.Texture;

import java.util.ArrayList;


public class ReflectionMaterial extends Material {

    /**
     * Color of Geometry
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
     * amount of max recursions
     */
    public final int depth;

    /**
     * Constructor of PhongMaterial
     * throws IllegalArgumentException if diffuse or specular is null
     * @param texture texture of Geometry
     * @param specular Color of reflected Light
     * @param exponent Power of reflection
     */
    public ReflectionMaterial(final Texture texture, final Color specular, final int exponent, final int depth){
        if(texture == null || specular == null) throw new IllegalArgumentException();
        this.texture = texture;
        this.specular = specular;
        this.exponent = exponent;
        this.depth = depth;
    }

    /**
     * Calculation after Phong
     * c = cr(ca + cl * max(0, n * l) + cl * max(0, e * rl)^exponent )
     * also checks if Geometry has a shadow
     * @param hit hit of Geometry
     * @param world World
     * @return color
     */
    public Color colorFor(Hit hit, World world, int counter) {
        Color p1,p2,p3,p4,sum, fin = new Color(0,0,0);

        Point3 point3 = hit.ray.at(hit.t);
        Color diffuse = texture.colorFor(hit.u, hit.v);

        for(Light l : world.lightSources){
            if(!l.illuminates(point3, world) && l.castsShadows){
                continue;
            }
            p1 = diffuse.mul(l.color); // diffuse reflection color * ambient light
            p2 = p1.mul(Math.max(0,hit.n.dot(l.directionFrom(point3).normalized()))); // diffuse reflection color * maximum between 0 and (normal vector*light source vector)
            p3 = specular.mul(l.color); // specular reflection color * light source color
            p4 = p3.mul(Math.pow(Math.max(0,world.cam.e.sub(point3).normalized().dot(l.directionFrom(point3).reflectedOn(hit.n))), exponent));// p3 * maximum between 0 and (eye vector * reflected light source vector) factorized by exponent(P)
            sum = p2.add(p4); // add both parts (p2 + p4)
            fin = fin.add(sum); // add all sums

        }
        return diffuse.mul(world.ambientLight).add(fin).add(reflection(point3, hit.ray.d.mul(-1).reflectedOn(hit.n).normalized(), world, counter )); // multiply diffuse reflection color with ambient light color and add sum
    }

    /**
     * gets reflected color using recursion
     * depth checks amount of may recursions
     * hits objects if hit gets color uses hit and reflected vector
     * for new recursion
     *
     * @param point point of obj
     * @param reflectedVector vector of obj
     * @param world World containing all geos
     * @param counter amount of recursions
     * @return reflected color
     */
    private Color reflection(final Point3 point, final Vector3 reflectedVector, final World world, int counter){
        if(counter < depth){
            Ray r = new Ray(point, reflectedVector);
            ArrayList<Hit> arrayList = new ArrayList<Hit>();
            for(Geometry geo : world.itemList){
                arrayList.add(geo.hit(r));
            }
            /*
            Hit minHit = arrayList.stream().min(new Comparator<Hit>() {
                @Override
                public int compare(Hit hit, Hit t1) {
                    return Double.compare(hit.t, t1.t);
                }
            }).orElse(null);

             */

            double max = Double.MAX_VALUE;
            Hit minHit = null;
            for(Hit h : arrayList){
                if(h != null && h.t < max){
                    minHit = h;
                    max = h.t;
                }
            }

            if (minHit == null) return world.backgroundColor;
            Color oldColor = minHit.geo.mat.colorFor(minHit, world, counter + 1);
            //System.out.println(oldColor);
            return oldColor;
            //counter += 1;
            //return reflection(r.at(minHit.t), reflectedVector.reflectedOn(minHit.n), world, oldColor, counter);

        }
        return new Color(0, 0, 0);

    }

}
