package Texture;

import MathLib.Point3;
import Raytracer.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * projects image onto geometry
 * via normalized u v coordinates
 */
public class ImageTexture extends Texture {

    /**
     * url to image
     */
    public final String url;

    /**
     * Image to be read from
     */
    public BufferedImage img = null;

    /**
     * Constructor //TODO: might add assets folder
     * @param url to file
     */
    public ImageTexture(final String url){
        this.url = url;
        try{
            this.img = ImageIO.read(new File("src/Assets/Textures/" + url));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * http://www.irisa.fr/prive/kadi/Cours_LR2V/RayTracing_Texturing.pdf
     * normalized u and v coordinates from 0 < coordinate < 1
     * starts from 0, 0 hence y coordinate calculated from bottom to top
     * @param u x coordinate on image gets normalized
     * @param v y coordinate on image gets normalized
     * @return Color color
     */
    public Color colorFor(final double u, final double v) {
        double xImage = normalizeTextureSpace(u);
        double yImage = normalizeTextureSpace(v);
        double x = (img.getWidth() - 1) * xImage;
        double y = (img.getHeight() - 1 - ((img.getHeight() - 1) * yImage));
        int color = img.getRGB((int) x, (int) y);
        double red = ((color & 0xff0000) >> 16) / 255.0;
        double green = ((color & 0xff00) >> 8) / 255.0;
        double blue = (color & 0xff) / 255.0;

        return new Color(red, green, blue);
    }

    /**
     * normalized coordinates in texture space
     * @param value double Value to be normalized
     * @return normalized double
     */
    private double normalizeTextureSpace(double value){
        value = value % 1;
        if(value < 0.0) value += 1;
        return value;
    }


}
