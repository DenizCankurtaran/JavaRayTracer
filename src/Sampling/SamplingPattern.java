package Sampling;

import MathLib.Point2;

import java.util.ArrayList;

/**
 * Pattern to sample pixel color
 * use of pattern in cameras
 */
public class SamplingPattern {

    /**
     * List of points containing offset per pixel
     */
    public final ArrayList<Point2> points;

    /**
     * List of points containing offset per pixel
     * @param points pixel offset
     */
    public SamplingPattern(ArrayList<Point2> points){
        this.points = points;
    }
}
