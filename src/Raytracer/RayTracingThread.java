package Raytracer;

import Camera.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Set;

public class RayTracingThread extends Task<Void> {

    private final Canvas canvas;
    private final World world;
    private final Camera cam;
    private final int width;
    private final int height;
    private final int startY;
    private final int finishY;


    public RayTracingThread(World world, Camera cam, Canvas canvas, int width, int height, int startY, int finishY) {
        this.canvas = canvas;
        this.world = world;
        this.cam = cam;
        this.width = width;
        this.height = height;
        this.startY = startY;
        this.finishY = finishY;
    }


    @Override
    public Void call() {
        long start = System.currentTimeMillis();


        double red = world.backgroundColor.r;
        double green = world.backgroundColor.g;
        double blue = world.backgroundColor.b;


        Color color;

        for (int y = startY; y < finishY; y++) {
            ArrayList<Color> arrayList = new ArrayList<Color>();

            for (int x = 0; x < width; x++) {

                Set<Ray> set = cam.rayFor(width, height, x, height - y - 1);
                ArrayList<Color> colorList = new ArrayList<Color>();
                for (Ray r : set) {
                    Hit pixel = world.hit(r);

                    if (pixel != null) {
                        colorList.add(pixel.geo.mat.colorFor(pixel, world, 0));
                    } else {

                        colorList.add(new Color(red, green, blue));
                    }
                }

                double averageRed = 0;
                double averageGreen = 0;
                double averageBlue = 0;

                for (Color c : colorList) {
                    averageRed += c.r;
                    averageGreen += c.g;
                    averageBlue += c.b;
                }
                averageRed /= colorList.size();
                averageGreen /= colorList.size();
                averageBlue /= colorList.size();

                color = new Color(averageRed, averageGreen, averageBlue);

                color.x = x;
                color.y = y;
                arrayList.add(color);
            }
            Platform.runLater(()-> Raytracer.writePixel(arrayList, canvas));



        }
        System.out.println("Thread finished in " + (System.currentTimeMillis() - start) + "ms");

        return null;
    }
}
