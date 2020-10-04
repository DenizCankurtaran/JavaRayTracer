package Raytracer;

import java.util.Objects;

/**
 * color class represents a color in rgb space
 */
public class Color {
    /**
     * red color value
     */
    public final double r;

    /**
     * green color value
     */
    public final double g;

    /**
     * blue color value
     */
    public final double b;

    public int x = 0;
    public int y = 0;


    /**
     * constructor for color class
     * max values set to 1 or 0 if exceeded
     */
    public Color(double r, double g, double b){
        if(r > 1) r=1;
        if(r < 0) r=0;
        this.r = r;
        if(g > 1) g=1;
        if(g < 0) g=0;
        this.g = g;
        if(b > 1) b=1;
        if(b < 0) b=0;
        this.b = b;
    }

    /**
     * adds one color to another
     * throws IllegalArgumentException if color object is null
     * @param c second color to add onto this one
     * @return new color
     */
    public Color add (final Color c){
        if(c == null) throw new IllegalArgumentException();
        return new Color(this.r+c.r,this.g+c.g,this.b+c.b);
    }

    /**
     * subtracts one color from another
     * throws IllegalArgumentException if color object is null
     * @param c second color to subtract from this one
     * @return new color
     */
    public Color sub(final Color c){
        if(c == null) throw new IllegalArgumentException();
        return new Color(this.r-c.r,this.g-c.g,this.b-c.b);
    }

    /**
     * multiplies one color with another
     * throws IllegalArgumentException if color object is null
     * @param c second color to multiply with this one
     * @return new color
     */
    public Color mul(final Color c){
        if(c == null) throw new IllegalArgumentException();
        return new Color(this.r*c.r,this.g*c.g,this.b*c.b);
    }
    /**
     * multiplies one color with a constant
     * @param v constant to multiply color with
     * @return new color
     */
    public Color mul(final double v){
        return new Color(this.r*v,this.g*v,this.b*v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(color.r, r) == 0 &&
                Double.compare(color.g, g) == 0 &&
                Double.compare(color.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
