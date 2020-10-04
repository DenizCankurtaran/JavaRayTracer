package MathLib;

import java.util.Objects;

/**
 * Mat3x3 represents a 3x3 Matrix in 3 dimensional Space
 */
public class Mat3x3 {

    /**
     * Matrix value in row 1 and column 1
     */
    public final double m11;

    /**
     * Matrix value in row 1 and column 2
     */
    public final double m12;

    /**
     * Matrix value in row 1 and column 3
     */
    public final double m13;

    /**
     * Matrix value in row 2 and column 1
     */
    public final double m21;

    /**
     * Matrix value in row 2 and column 2
     */
    public final double m22;

    /**
     * Matrix value in row 2 and column 3
     */
    public final double m23;

    /**
     * Matrix value in row 3 and column 1
     */
    public final double m31;

    /**
     * Matrix value in row 3 and column 2
     */
    public final double m32;

    /**
     * Matrix value in row 3 and column 3
     */
    public final double m33;

    /**
     * determinant of the whole Mat3x3 object
     */
    public final double determinant;

    /**
     * Constructor of Mat3x3 object
     * also calculates the determinant
     * @param m11 Matrix value in row 1 and column 1
     * @param m12 Matrix value in row 1 and column 2
     * @param m13 Matrix value in row 1 and column 3
     * @param m21 Matrix value in row 2 and column 1
     * @param m22 Matrix value in row 2 and column 2
     * @param m23 Matrix value in row 2 and column 3
     * @param m31 Matrix value in row 3 and column 1
     * @param m32 Matrix value in row 3 and column 2
     * @param m33 Matrix value in row 3 and column 3
     */
    public Mat3x3(final double m11, final double m12, final double m13, final double m21, final double m22, final double m23, final double m31, final double m32, final double m33) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.determinant = (m11 * m22 * m33) + (m12 * m23 * m31) + (m13 * m21 * m32) - (m13 * m22 * m31) - (m11 * m23 * m32) - (m12 * m21 * m33);

    }

    /**
     * Multiplies 2 matrices and returns the result
     * If given Matrix is null IllegalArgumentException will be thrown
     * @param m Matrix to multiply with
     * @return new Matrix
     */
    public Mat3x3 mul(Mat3x3 m){
        if(m == null) throw new IllegalArgumentException();
        double newM11 = (m11 * m.m11) + (m12 * m.m21) + (m13 * m.m31);
        double newM12 = (m11 * m.m12) + (m12 * m.m22) + (m13 * m.m32);
        double newM13 = (m11 * m.m13) + (m12 * m.m23) + (m13 * m.m33);

        double newM21 = (m21 * m.m11) + (m22 * m.m21) + (m23 * m.m31);
        double newM22 = (m21 * m.m12) + (m22 * m.m22) + (m23 * m.m32);
        double newM23 = (m21 * m.m13) + (m22 * m.m23) + (m23 * m.m33);

        double newM31 = (m31 * m.m11) + (m32 * m.m21) + (m33 * m.m31);
        double newM32 = (m31 * m.m12) + (m32 * m.m22) + (m33 * m.m32);
        double newM33 = (m31 * m.m13) + (m32 * m.m23) + (m33 * m.m33);

        return new Mat3x3(newM11, newM12, newM13, newM21, newM22, newM23, newM31, newM32, newM33);
    }

    /**
     * Multiplies Matrix with given Vector
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector to multiply with
     * @return new Vector
     */
    public Vector3 mul(Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        double newX = (m11 * v.x) + (m12 * v.y) + (m13 * v.z);
        double newY = (m21 * v.x) + (m22 * v.y) + (m23 * v.z);
        double newZ = (m31 * v.x) + (m32 * v.y) + (m33 * v.z);
        return new Vector3(newX, newY, newZ);
    }

    /**
     * Multiplies Matrix with given Point
     * If given Point is null IllegalArgumentException will be thrown
     * @param p Point to multiply with
     * @return new Point
     */
    public Point3 mul(Point3 p){
        if(p == null) throw new IllegalArgumentException();
        double newX = (m11 * p.x) + (m12 * p.y) + (m13 * p.z);
        double newY = (m21 * p.x) + (m22 * p.y) + (m23 * p.z);
        double newZ = (m31 * p.x) + (m32 * p.y) + (m33 * p.z);
        return new Point3(newX, newY, newZ);
    }

    /**
     * Changes whole column 1 with given Vector
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector that changes column
     * @return new Matrix with changed column
     */
    public Mat3x3 changeCol1(Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return new Mat3x3(v.x, m12, m13, v.y, m22, m23, v.z, m32, m33);
    }

    /**
     * Changes whole column 2 with given Vector
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector that changes column
     * @return new Matrix with changed column
     */
    public Mat3x3 changeCol2(Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return new Mat3x3(m11, v.x, m13, m21, v.y, m23, m31, v.z, m33);
    }

    /**
     * Changes whole column 3 with given Vector
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v Vector that changes column
     * @return new Matrix with changed column
     */
    public Mat3x3 changeCol3(Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        return new Mat3x3(m11, m12, v.x, m21, m22, v.y, m31, m32, v.z);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mat3x3 mat3x3 = (Mat3x3) o;
        return Double.compare(mat3x3.m11, m11) == 0 &&
                Double.compare(mat3x3.m12, m12) == 0 &&
                Double.compare(mat3x3.m13, m13) == 0 &&
                Double.compare(mat3x3.m21, m21) == 0 &&
                Double.compare(mat3x3.m22, m22) == 0 &&
                Double.compare(mat3x3.m23, m23) == 0 &&
                Double.compare(mat3x3.m31, m31) == 0 &&
                Double.compare(mat3x3.m32, m32) == 0 &&
                Double.compare(mat3x3.m33, m33) == 0 &&
                Double.compare(mat3x3.determinant, determinant) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m11, m12, m13, m21, m22, m23, m31, m32, m33, determinant);
    }

    @Override
    public String toString() {
        return "Mat3x3{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m21=" + m21 +
                ", m22=" + m22 +
                ", m23=" + m23 +
                ", m31=" + m31 +
                ", m32=" + m32 +
                ", m33=" + m33 +
                ", determinant=" + determinant +
                '}';
    }

}
