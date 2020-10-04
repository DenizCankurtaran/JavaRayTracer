package Material;

import Geometry.Geometry;
import MathLib.Point3;
import MathLib.Vector3;
import Raytracer.Color;
import Raytracer.Hit;
import Raytracer.Ray;
import Raytracer.World;
import Texture.Texture;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Refracts light and makes geometry transparent
 * breaks light through geometry
 */
public class RefractionMaterial extends Material{

    /**
     * enum different indices for refraction
     */
    public enum RefractionIndex{
        VACUUM,
        AIR,
        ICE,
        WATER,
        GLASS,
        DIAMOND
    }

    /**
     * Texture of geometry
     */
    final Texture texture;

    /**
     * index for refraction
     */
    final double refractiveIndex;


    /**
     * Constructor
     * checks with private function value of refraction
     * if given index doesnt exist 1.0 will be standard
     * @param texture texture of geometry
     */
    public RefractionMaterial(final Texture texture, RefractionIndex index){
        if(texture == null) throw new IllegalArgumentException();
        this.texture = texture;
        this.refractiveIndex = checkIndex(index);

    }

    /**
     * calculates color of refracted geometry
     * ray goes through geometry and hits geometries behind+
     * Color calculates with R * color of this geometry + T * color of hit geometry
     * @param hit hit object
     * @param world for getting the lights
     * @return color
     */
    public Color colorFor(Hit hit, World world, final int counter) {
        Point3 at = hit.ray.at(hit.t);

        double airDensity = 1.0;

        double phi1 = -hit.ray.d.dot(hit.n);
        double phi2 = Math.sqrt( 1 - Math.pow(airDensity / this.refractiveIndex, 2) * (1 - Math.pow(phi1, 2)));
        Vector3 t = hit.ray.d.mul(airDensity / this.refractiveIndex).normalized().sub(hit.n.mul(phi2 - (airDensity / this.refractiveIndex) * phi2));
        Ray ray = new Ray(at, t);

        double R0 = Math.pow((airDensity - this.refractiveIndex) / (airDensity + this.refractiveIndex), 2);
        double R = (R0 + (1 - R0)) * Math.pow(1 - phi1, 5);
        double T = 1 - R;

        ArrayList<Hit> arrayList = new ArrayList<Hit>();
        for(Geometry geo: world.itemList){
            Hit h = geo.hit(ray);
            if(h != null)arrayList.add(h);
        }
        Hit maxHit = arrayList.stream().max(new Comparator<Hit>() {
            @Override
            public int compare(Hit hit, Hit t1) {
                return Double.compare(hit.t, t1.t);
            }
        }).orElse(null);


        if(maxHit == null){
            return null;
        }

        Color thisColor = this.texture.colorFor(maxHit.u, maxHit.v).mul(R);
        Color returnColor = maxHit.geo.mat.colorFor(maxHit, world, 0).mul(T);
        return thisColor.add(returnColor);

    }

    /**
     * private function to give enum values
     * @param index enum
     * @return double value if refraction index
     */
    private double checkIndex(final RefractionIndex index){

        switch (index){
            case VACUUM: return 1.0;

            case AIR: return 1.0003;

            case ICE: return 1.31;

            case WATER: return 1.33;

            case GLASS: return 1.5;

            case DIAMOND: return 2.42;

            default: return 1.0;
        }

    }
}
