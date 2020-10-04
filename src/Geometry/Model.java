package Geometry;

import Material.Material;
import MathLib.Normal3;
import MathLib.Point2;
import MathLib.Point3;
import Raytracer.Hit;
import Raytracer.Ray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Model loads a wavefront obj
 * object has to be located in 'Assets/'
 * wavefront obj specifications implemented from
 * https://de.wikipedia.org/wiki/Wavefront_OBJ
 */
public class Model extends Geometry {

    /**
     * ArrayList containing all vertices
     */
    public final ArrayList<Point3> v = new ArrayList<Point3>();

    /**
     * ArrayList containing all textureCoordinates
     */
    public final ArrayList<Point2> vt = new ArrayList<Point2>();

    /**
     * ArrayList containing all normals
     */
    public final ArrayList<Normal3> vn = new ArrayList<Normal3>();

    /**
     * ArrayList containing all faces
     */
    public final ArrayList<Triangle> f = new ArrayList<Triangle>();

    /**
     * used to get minimal point
     */
    private double minX, minY, minZ = 9999;

    /**
     * used to get maximal point
     */
    private double maxX, maxY, maxZ = -9999;

    /**
     * minimal point
     */
    private Point3 minPoint;

    /**
     * maximal point
     */
    private Point3 maxPoint;

    /**
     * Name of Object
     */
    private String name = "Model";

    /**
     * Constructor calculates in separate function
     * @param nameOfObject Name of wavefront object located on Assets/
     * @param mat material of object
     */
    public Model(final String nameOfObject, final Material mat){
        super(mat);
        //v.add(null); // disgusting
        startCalculateObj(nameOfObject);
    }

    /**
     * checks first if hit object is in axis aligned box
     * @param r incoming ray
     * @return Hit hit
     */
    public Hit hit(Ray r) {

        AxisAlignedBox a = new AxisAlignedBox(minPoint, maxPoint, mat);
        Hit checkHit = a.hit(r);
        if (checkHit != null){
            ArrayList<Hit> hits = new ArrayList<Hit>();
            for (Triangle tri : f) {
                Hit hit = tri.hit(r);
                if (hit != null) hits.add(hit);
            }

//            double max = Double.MIN_VALUE;
//            Hit minHit = null;
/*
            for(Hit h : hits){
                if(h != null && h.t > max){
                    minHit = h;
                    max = h.t;
                }
            }*/

            Hit minHit = hits.stream().min(new Comparator<Hit>() {
                @Override
                public int compare(Hit hit, Hit t1) {
                    return Double.compare(hit.t, t1.t);
                }
            }).orElse(null);



            if (minHit == null) return null;

            Hit hit = new Hit(minHit.t, r, this, minHit.n);
            return hit;
        }
        return null;


    }

    /**
     * takes path to wavefront object and starts working through .obj
     * @param path Name of object
     */
    private void startCalculateObj(final String path){
        String data = "";
        try{
            data = new String(Files.readAllBytes(Paths.get("src/Assets/WaveFrontObjects/" + path)));
        }catch(IOException e){
            e.printStackTrace();
        }
        calculateObj(data);
    }

    /**
     * gets through each line and separates between
     * v for vertices
     * vt for texture
     * vn for normal
     * and f for face
     * all other are currently ignored
     * @param object String of object
     */
    private void calculateObj(final String object){
        String[] data = object.split("\r\n");
        // System.out.println("Model has: " + data.length + " lines");
        for(String line: data){
            //System.out.println(line);
            String[] splitLine = line.split(" ");
            double[] arr;

            switch (splitLine[0]){
                /*
                case "#":
                // comment
                    System.out.println(line);
                    break;

                case "mtllib":
                // path to Material of object
                    System.out.println(line);
                    break;
                */
                case "o":

                    this.name = splitLine[1];
                    break;

                case "v":
                    arr = stringArrayToDoubleArray(splitLine);
                    Point3 currentPoint = new Point3(arr[0], arr[1], arr[2]);
                    checkForEdges(currentPoint);
                    v.add(currentPoint);
                    // System.out.println(this.v.size());
                    break;

                case "vt":
                    arr = stringArrayToDoubleArray(splitLine);
                    vt.add(new Point2(arr[0], arr[1]));
                    break;

                case "vn":
                    arr = stringArrayToDoubleArray(splitLine);
                    vn.add(new Normal3(arr[0], arr[1], arr[2]));
                    break;
                /*
                case "g":
                // grouping
                    System.out.println(line);
                    break;

                case "usemtl":
                // use material
                    System.out.println(line);
                    break;

                case "s":
                    System.out.println(line);
                    break;

                 */

                case "f":
                    // ugly
                    String[] firstFace = splitLine[1].split("/");
                    String[] secondFace = splitLine[2].split("/");
                    String[] thirdFace = splitLine[3].split("/");
                    Triangle t;

                    int xIndex = calInt(firstFace[0]);
                    int yIndex = calInt(secondFace[0]);
                    int zIndex = calInt(thirdFace[0]);
                    /*
                    System.out.println("x: " + xIndex + " y: " + yIndex + " z: " + zIndex);
                    System.out.println(v.get(xIndex-1));
                    System.out.println(v.get(yIndex-1));
                    System.out.println(v.get(zIndex-1));
                    System.out.println("------------------------------------------------------------------");

                     */

                    if(firstFace.length == 3){

                        int xVt = calInt(firstFace[2]);
                        int yVt = calInt(secondFace[2]);
                        int zVt = calInt(thirdFace[2]);

                        int xNormal = calInt(firstFace[1]);
                        int yNormal = calInt(secondFace[1]);
                        int zNormal = calInt(thirdFace[1]);

                        t = new Triangle(v.get(xIndex - 1), v.get(yIndex - 1), v.get(zIndex - 1), vt.get(xVt - 1), vt.get(yVt - 1), vt.get(zVt - 1), vn.get(xNormal - 1), vn.get(yNormal - 1), vn.get(zNormal - 1), mat);

                    }else if(firstFace.length == 2){

                        if(splitLine[1].contains("//")){
                            int xNormal = calInt(firstFace[1]);
                            int yNormal = calInt(secondFace[1]);
                            int zNormal = calInt(thirdFace[1]);

                            t = new Triangle(v.get(xIndex - 1), v.get(yIndex - 1), v.get(zIndex - 1), vn.get(xNormal - 1), vn.get(yNormal - 1), vn.get(zNormal - 1), mat);
                        }else{
                            int xVt = calInt(firstFace[2]);
                            int yVt = calInt(secondFace[2]);
                            int zVt = calInt(thirdFace[2]);

                            t = new Triangle(v.get(xIndex - 1), v.get(yIndex - 1), v.get(zIndex - 1), vt.get(xVt - 1), vt.get(yVt - 1), vt.get(zVt - 1), mat);

                        }

                    }else{
                        // System.out.println(xIndex);
                        t = new Triangle(v.get(xIndex - 1), v.get(yIndex - 1), v.get(zIndex- 1), mat);
                    }
                    // System.out.println(t);

                    f.add(t);
                    break;
            }
        }
        System.out.println(name + " has: " + v.size() + " vertices");
        System.out.println(name + " has: " + f.size() + " faces");
        minPoint = new Point3(minX, minY, minZ);
        maxPoint = new Point3(maxX, maxY, maxZ);
        System.out.println("MinPoint: "  + minPoint);
        System.out.println("MaxPoint: "  + maxPoint);


    }

    /**
     * takes string and tries to parse it
     * trim maybe not needed
     * @param str input
     * @return Integer
     */
    private int calInt(String str){
        return Integer.parseInt(str.trim());
    }

    /**
     * checks each point for minimal Point and maximal Point
     * @param p Point3 to check
     */
    private void checkForEdges(Point3 p){
        if (p.x > maxX) maxX = p.x;
        if (p.y > maxY) maxY = p.y;
        if (p.z > maxZ) maxZ = p.z;

        if (p.x < minX) minX = p.x;
        if (p.y < minY) minY = p.y;
        if (p.z < minZ) minZ = p.z;
    }

    /**
     * transforms String[] to Double[]
     * ignores first index
     * @param strArray String[] to transform
     * @return new Double[] without first index
     */
    private double[] stringArrayToDoubleArray(String[] strArray){
        double[] arr = new double[strArray.length-1];

        for (int i = 1; i < strArray.length; i++){
            try{
                // System.out.println(strArray[i]);
                arr[i - 1] = Double.parseDouble(strArray[i]);
                //System.out.println(arr[i - 1]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        }
        return arr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Double.compare(model.minX, minX) == 0 &&
                Double.compare(model.minY, minY) == 0 &&
                Double.compare(model.minZ, minZ) == 0 &&
                Double.compare(model.maxX, maxX) == 0 &&
                Double.compare(model.maxY, maxY) == 0 &&
                Double.compare(model.maxZ, maxZ) == 0 &&
                Objects.equals(v, model.v) &&
                Objects.equals(vt, model.vt) &&
                Objects.equals(vn, model.vn) &&
                Objects.equals(f, model.f) &&
                Objects.equals(minPoint, model.minPoint) &&
                Objects.equals(maxPoint, model.maxPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, vt, vn, f, minX, minY, minZ, maxX, maxY, maxZ, minPoint, maxPoint);
    }

    @Override
    public String toString() {
        return "Model{" +
                "v=" + v +
                ", vt=" + vt +
                ", vn=" + vn +
                ", f=" + f +
                ", minX=" + minX +
                ", minY=" + minY +
                ", minZ=" + minZ +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                ", maxZ=" + maxZ +
                ", minPoint=" + minPoint +
                ", maxPoint=" + maxPoint +
                '}';
    }
}
