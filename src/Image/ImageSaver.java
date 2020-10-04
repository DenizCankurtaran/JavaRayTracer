package Image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageSaver {

    static File out;

    public static void saveImage(final Canvas c) {
        try{
            FileChooser fc = new FileChooser();
            fc.setTitle("Select save location");
            fc.setInitialDirectory(new File(System.getProperty("user.home")));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG","*.png"));
            out = fc.showSaveDialog(null);

            WritableImage wImage = new WritableImage((int) c.getWidth(), (int) c.getHeight());
            WritableImage snapshot = c.snapshot(new SnapshotParameters(), wImage);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", out);

        } catch (IOException e) {
            System.err.print("Error creating image.");
        }
    }

}
