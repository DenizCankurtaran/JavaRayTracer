package Image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShowImage extends Application {

    private File file;

    private BufferedImage image;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose a picture");

        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            ImageView iv = new ImageView(file.toURI().toString());

            box.getChildren().add(iv);
        }
        System.out.println(file);

        image = ImageIO.read(new File(file.toString()));
        int width = image.getWidth();
        int height = image.getHeight();

        stage.setScene( new Scene(box, width, height));
        stage.show();
    }
}
