package Texture;

import Raytracer.Color;

import java.util.Objects;

public class SingleColorTexture extends Texture {

    public final Color color;

    public SingleColorTexture(final Color color){
        if (color == null) throw new IllegalArgumentException();
        this.color = color;
    }

    @Override
    public Color colorFor(double u, double v) {
        return color;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleColorTexture that = (SingleColorTexture) o;
        return Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "SingleColorTexture{" +
                "color=" + color +
                '}';
    }
}
