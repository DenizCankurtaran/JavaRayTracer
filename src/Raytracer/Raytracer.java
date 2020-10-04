package Raytracer;

import Camera.*;
import Geometry.*;
import Image.ImageSaver;
import Lighting.Light;
import Lighting.PointLight;
import Material.*;
import MathLib.Point2;
import MathLib.Point3;
import MathLib.Vector3;
import Sampling.RandomSamplingPattern;
import Sampling.SamplingPattern;
import Texture.ImageTexture;
import Texture.SingleColorTexture;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * creates a ray for every pixel to test every object in the world
 */

public class Raytracer extends Application {
    ArrayList<Camera> cams;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //-----------------------------------------> RayTracer


        ArrayList<Geometry> itemList = new ArrayList<>();
        ArrayList<Light> lightSources = new ArrayList<>();
        cams = new ArrayList<Camera>();

        lightSources.add(new PointLight(new Color(1, 1, 1), true, new Point3(4, 4, 4)));

        ArrayList<Point2> point2s = RandomSamplingPattern.getRandomPattern(1);
        ArrayList<Geometry> geoList = new ArrayList<Geometry>();
        geoList.add(new Model("bunny.obj", new LambertMaterial(new SingleColorTexture(new Color(1, 0, 0)))));
        //itemList.add(new Sphere(new Point3(0, 0, 0), 1, new LambertMaterial(new ImageTexture("earth.jpg"))));
        Node bunny = new Node(new SingleColorMaterial(new SingleColorTexture(new Color(0,0,0))), new Transform().scale(new Vector3(2, 2, 2)), geoList);
        itemList.add(bunny);
        Camera cam2 = new PerspectiveCamera(new Point3(1, 1, 1), new Vector3(-1, -1, -1), new Vector3(0, 1, 0), Math.PI / 4.0, new SamplingPattern(point2s));
        World world = new World(itemList, lightSources, new Color(0, 0, 0), new Color(0.1, 0.1, 0.1), 1.0, cam2); //cam null


        //-----------------------------------------> JavaFx

        Canvas canvas = new Canvas(640, 480);

        VBox root = new VBox();
        final MenuBar menuBar = new MenuBar();

        final MenuItem saveItem = new MenuItem("Save Image to drive");
        saveItem.setOnAction(e -> ImageSaver.saveImage(canvas));

        final Menu optionsMenu = new Menu("Options");
        optionsMenu.getItems().add(saveItem);
        menuBar.getMenus().add(optionsMenu);
        Scene scene = new Scene(root, canvas.getWidth(), canvas.heightProperty().add(menuBar.heightProperty()).doubleValue());
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        scene.heightProperty().addListener((v, oldValue, newValue) -> calculatePixels(world,  canvas, canvas.getWidth(), canvas.getHeight()));
        scene.widthProperty().addListener((v, oldValue, newValue) -> calculatePixels(world,  canvas, canvas.getWidth(), canvas.getHeight()));

        root.getChildren().addAll(menuBar, canvas);


        stage.setTitle("RayTracer");
        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> calculatePixels(world, canvas, canvas.getWidth(), canvas.getHeight()));

    }


    public static void calculatePixels(World world, Canvas canvas, double w, double h) {
        System.out.println("WidthCalc: " + w + ", HeightCalc: " +h);
        long start = System.currentTimeMillis();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, w, h);
        int numberOfThreads = 5;
        System.out.println("number of threads: " + numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int rows = (int) (h / numberOfThreads);
        int startY = 0;
        int finishY = rows;

        Future<?> future = null;
        for (int i = 0; i < numberOfThreads; i++) {
            Task<Void> task = new RayTracingThread(world, world.cam, canvas, (int) w, (int) h, startY, (int) h);
            Thread thread = new Thread(task);
            future = executor.submit(thread);
            startY += rows;
            finishY += rows;
        }


        while (!future.isDone()) {
            Thread.yield();
        }


        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.yield();
        }
        System.out.println("rendering finished in " + (System.currentTimeMillis() - start) + "ms");


    }


    public static void writePixel(ArrayList<Color> array, Canvas canvas) {

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final PixelWriter pixelWriter = gc.getPixelWriter();
        for (Color color : array) {
            javafx.scene.paint.Color newColor = new javafx.scene.paint.Color(color.r, color.g, color.b, 1);
            pixelWriter.setColor(color.x, color.y, newColor);
        }

    }

}
