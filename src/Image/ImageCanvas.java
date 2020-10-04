package Image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * image.ImageCanvas opens a canvas with a diagonal red line from the top left
 * image is savable
 */
public class ImageCanvas extends Application {

    Canvas canvas;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // window set up
        final VBox root = new VBox();
        final Scene scene = new Scene(root, 640, 480);
        canvas = new Canvas(scene.getWidth(), scene.getHeight());

        canvas.widthProperty().bind(scene.widthProperty());
        scene.widthProperty().addListener((v, oldValue, newValue) -> calculatePixels(scene));
        canvas.heightProperty().bind(scene.heightProperty());
        scene.heightProperty().addListener((v, oldValue, newValue) -> calculatePixels(scene));

        final MenuBar menuBar = new MenuBar();
        final Menu menu = new Menu("Save");
        final MenuItem saveItem = new MenuItem("Save image to drive");

        menuBar.getMenus().add(menu);
        menu.getItems().add(saveItem);
        saveItem.setOnAction(e -> ImageSaver.saveImage(canvas));

        calculatePixels(scene);
        root.getChildren().addAll(menuBar, canvas);

        stage.setScene(scene);
        stage.show();
    }

    public void calculatePixels(Scene sc){
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final PixelWriter pixelWriter = gc.getPixelWriter();

        for(int y = 0; y < canvas.getHeight(); y++ ){
            for(int x = 0; x < canvas.getWidth(); x++){
                if(x == y) pixelWriter.setColor(x, y, Color.RED);
                else pixelWriter.setColor(x, y, Color.BLACK);
            }
        }

    }
}