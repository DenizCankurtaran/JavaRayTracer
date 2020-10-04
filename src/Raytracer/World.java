package Raytracer;

import Camera.Camera;
import Geometry.Geometry;
import Lighting.Light;

import java.util.ArrayList;

/**
 * worlds class includes every object
 */
public class World {

    /**
     * List containing every geometry to be rendered
     */
    public final ArrayList <Geometry> itemList;

    /**
     * List containing all light sources
     */
    public final ArrayList <Light> lightSources;

    /**
     * background color of world // every non hit point will be dyed this color
     */
    public final Color backgroundColor;

    /**
     * ambientLight colorLight that will be added on top of every light for
     * realistic effect
     */
    public final Color ambientLight;

    /**
     * refraction index of light
     */
    public final double refractiveIndex;

    /**
     * cam so gui can know which camera to use
     */
    public Camera cam;


    /**
     * constructor of world object
     * throws IllegalArgumentException if color is null
     * @param itemList list containing all geometry
     * @param lightSources list containing all light sources
     * @param backgroundColor color of background
     * @param ambientLight light of ambient
     * @param refractiveIndex refraction index of light
     * @param cam cam so gui can know which camera to use
     */
    public World(final ArrayList<Geometry> itemList, final ArrayList<Light> lightSources, final Color backgroundColor, final Color ambientLight, final double refractiveIndex , final Camera cam){
        if(backgroundColor == null || itemList == null) throw new IllegalArgumentException();
        this.itemList = itemList;
        this.lightSources = lightSources;
        this.backgroundColor = backgroundColor;
        this.ambientLight = ambientLight;
        this.refractiveIndex = refractiveIndex;
        this.cam = cam;
    }

    /**
     * calculation of Raytracer hit points
     * runs ray against every object in scene
     * returns hit point with lowest positive t if more than one t is given
     * returns null if no hit point is found
     *  @param r outgoing ray
     */
    public final Hit hit(final Ray r){
        ArrayList<Hit> arrayList = new ArrayList<Hit>();
        Hit hit;
        for (Geometry geometry : itemList){
            hit = geometry.hit(r);
            if(hit != null){
                arrayList.add(hit);
            }
        }

        double max = Double.MAX_VALUE;
        Hit closestHitPoint = null;
        for(Hit hitPoint : arrayList){
            if(hitPoint.t < max){
                max = hitPoint.t;
                closestHitPoint = hitPoint;
            }
        }
        //System.out.println(closestHitPoint);
        return closestHitPoint;
    }

    /**
     * sets active cam for gui
     * @param cam so gui can know which camera to use
     */
    public void setCam(Camera cam) {
        this.cam = cam;
    }

    @Override
    public String toString() {
        return "World{" +
                "itemList=" + itemList +
                ", lightSources=" + lightSources +
                ", backgroundColor=" + backgroundColor +
                ", ambientLight=" + ambientLight +
                ", cam=" + cam +
                '}';
    }
}
