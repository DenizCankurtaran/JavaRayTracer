package Texture;

import Raytracer.Color;

public abstract class Texture {

    public abstract Color colorFor(final double u, final double v);

    @Override
    public String toString() {
        return "Texture{}";
    }
}
