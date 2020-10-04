package Geometry;

import Material.Material;
import MathLib.*;
import Raytracer.Hit;
import Raytracer.Ray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Node extends Geometry{

    /**
    Transforms matrixes for ray
    */
    public final Transform tf;

    /**
    List containing all transformable geometries
    */
    public final ArrayList<Geometry> items;

    /**
    * Constructor of Node
    * @param mat material of Node
    * @param tf Transform matrixes
    * @param items List containing all transformable geometries
    */
    public Node(final Material mat, final Transform tf, final ArrayList<Geometry> items) {
        super(mat);
        this.tf = tf;
        this.items = items;
    }

    /**
    * Transforms ray, hits geometries with new ray
    * gets hit with minimal t
    * transforms Normal
    * @param r untransformed ray
    * @return new Hit with transformed t and normal
    */
    public Hit hit( final Ray r) {
        Ray newRay = tf.mul(r);
        ArrayList<Hit> hits = new ArrayList<Hit>();
        for(Geometry geo : items){
            Hit tmp = geo.hit(newRay);
            if(tmp != null) hits.add(tmp);
        }

        Hit minHit = hits.stream().min(new Comparator<Hit>() {
            @Override
            public int compare(Hit hit, Hit t1) {
                return Double.compare(hit.t, t1.t);
            }
        }).orElse(null);

        if(minHit == null) return null;

        Normal3 n = tf.mul(minHit.n);

        Hit transformedHit = new Hit(minHit.t, r, minHit.geo, n);

        return transformedHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(tf, node.tf) &&
                Objects.equals(items, node.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tf, items);
    }

    @Override
    public String toString() {
        return "Node{" +
                "tf=" + tf +
                ", items=" + items +
                '}';
    }
}
