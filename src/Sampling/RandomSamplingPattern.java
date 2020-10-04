package Sampling;

import MathLib.Point2;

import java.util.ArrayList;
import java.util.Random;

public interface RandomSamplingPattern {

    public static ArrayList<Point2> getRandomPattern(int numberOfRays){
        System.out.println("using "+ numberOfRays + " rays");
        Random r = new Random();
        ArrayList<Point2> randomOffsets = new ArrayList<Point2>();


        for(int i =0; i< numberOfRays; i++){
            randomOffsets.add(new Point2(0.1 + (1 - 0.1) * r.nextDouble(), 0.1 + (1 - 0.1) * r.nextDouble()));
            //point2s.add(new Point2(0, 0));
        }
        return randomOffsets;
    }
}
