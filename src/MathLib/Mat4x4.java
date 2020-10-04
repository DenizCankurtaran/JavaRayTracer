package MathLib;


import java.util.Objects;

/**
 * Mat4x4 represents a 4x4 Matrix
 */
public class Mat4x4 {

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
     * Matrix value in row 1 and column 4
     */
    public final double m14;

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
     * Matrix value in row 2 and column 4
     */
    public final double m24;

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
     * Matrix value in row 3 and column 4
     */
    public final double m34;

    /**
     * Matrix value in row 4 and column 1
     */
    public final double m41;

    /**
     * Matrix value in row 4 and column 2
     */
    public final double m42;

    /**
     * Matrix value in row 4 and column 3
     */
    public final double m43;

    /**
     * Matrix value in row 4 and column 4
     */
    public final double m44;

    /**
     * Constructor of Mat4x4 object
     * also calculates the determinant
     * @param m11 Matrix value in row 1 and column 1
     * @param m12 Matrix value in row 1 and column 2
     * @param m13 Matrix value in row 1 and column 3
     * @param m14 Matrix value in row 1 and column 4
     * @param m21 Matrix value in row 2 and column 1
     * @param m22 Matrix value in row 2 and column 2
     * @param m23 Matrix value in row 2 and column 3
     * @param m24 Matrix value in row 2 and column 4
     * @param m31 Matrix value in row 3 and column 1
     * @param m32 Matrix value in row 3 and column 2
     * @param m33 Matrix value in row 3 and column 3
     * @param m34 Matrix value in row 3 and column 4
     * @param m41 Matrix value in row 4 and column 1
     * @param m42 Matrix value in row 4 and column 2
     * @param m43 Matrix value in row 4 and column 3
     * @param m44 Matrix value in row 4 and column 4
     */
    public Mat4x4(final double m11, final double m12, final double m13, final double m14,
                  final double m21, final double m22, final double m23, final double m24,
                  final double m31, final double m32, final double m33, final double m34,
                  final double m41, final double m42, final double m43, final double m44) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m14 = m14;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m24 = m24;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.m34 = m34;
        this.m41 = m41;
        this.m42 = m42;
        this.m43 = m43;
        this.m44 = m44;
    }

    /**
     * multiplies Mat4x4 with Vector3
     * add value w = 0 to vector for multiplication with 4-dimensional matrix
     * If given Vector is null IllegalArgumentException will be thrown
     * @param v vector to be multiplied with
     * @return Vector3
     */
    public final Vector3 mul(final Vector3 v){
        if(v == null) throw new IllegalArgumentException();
        double vx = (v.x*m11)+(v.y*m12)+(v.z*m13);
        double vy = (v.x*m21)+(v.y*m22)+(v.z*m23);
        double vz = (v.x*m31)+(v.y*m32)+(v.z*m33);
        return new Vector3(vx, vy, vz);
    }

    /**
     * multiplies Mat4x4 with Point3
     * add value w = 0 to point for multiplication with 4-dimensional matrix
     * If given point is null IllegalArgumentException will be thrown
     * @param p point to be multiplied with
     * @return Point3
     */
    public final Point3 mul(final Point3 p){
        if(p == null) throw new IllegalArgumentException();
        // final double w = 0;
        double px = (p.x*m11)+(p.y*m12)+(p.z*m13)+(m14);
        double py = (p.x*m21)+(p.y*m22)+(p.z*m23)+(m24);
        double pz = (p.x*m31)+(p.y*m32)+(p.z*m33)+(m34);
        return new Point3(px, py, pz);
    }

    /**
     * multiplies Mat4x4 with Mat4x4
     * @param m matrix to be multiplied with
     * @return Mat4x4
     */
    public final Mat4x4 mul(final Mat4x4 m){

        double nm11 = (m11*m.m11)+(m12*m.m21)+(m13*m.m31)+(m14*m.m41);
        double nm12 = (m11*m.m12)+(m12*m.m22)+(m13*m.m32)+(m14*m.m42);
        double nm13 = (m11*m.m13)+(m12*m.m23)+(m13*m.m33)+(m14*m.m43);
        double nm14 = (m11*m.m14)+(m12*m.m24)+(m13*m.m34)+(m14*m.m44);

        double nm21 = (m21*m.m11)+(m22*m.m21)+(m23*m.m31)+(m24*m.m41);
        double nm22 = (m21*m.m12)+(m22*m.m22)+(m23*m.m32)+(m24*m.m42);
        double nm23 = (m21*m.m13)+(m22*m.m23)+(m23*m.m33)+(m24*m.m43);
        double nm24 = (m21*m.m14)+(m22*m.m24)+(m23*m.m34)+(m24*m.m44);


        double nm31 = (m31*m.m11)+(m32*m.m21)+(m33*m.m31)+(m34*m.m41);
        double nm32 = (m31*m.m12)+(m32*m.m22)+(m33*m.m32)+(m34*m.m42);
        double nm33 = (m31*m.m13)+(m32*m.m23)+(m33*m.m33)+(m34*m.m43);
        double nm34 = (m31*m.m14)+(m32*m.m24)+(m33*m.m34)+(m34*m.m44);

        double nm41 = (m41*m.m11)+(m42*m.m21)+(m43*m.m31)+(m44*m.m41);
        double nm42 = (m41*m.m12)+(m42*m.m22)+(m43*m.m32)+(m44*m.m42);
        double nm43 = (m41*m.m13)+(m42*m.m23)+(m43*m.m33)+(m44*m.m43);
        double nm44 = (m41*m.m14)+(m42*m.m24)+(m43*m.m34)+(m44*m.m44);

        return new Mat4x4(nm11,nm12,nm13,nm14,nm21,nm22,nm23,nm24,nm31,nm32,nm33,nm34,nm41,nm42,nm43,nm44);
    }

    /**
     * transposes given Matrix
     * @return transposed Mat4x
     */
    public final Mat4x4 transpose(){
        return new Mat4x4(this.m11, this.m21, this.m31, this.m41,
                          this.m12, this.m22, this.m32, this.m42,
                          this.m13, this.m23, this.m33, this.m43,
                          this.m14, this.m24, this.m34, this.m44);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mat4x4 mat4x4 = (Mat4x4) o;
        return Double.compare(mat4x4.m11, m11) == 0 &&
                Double.compare(mat4x4.m12, m12) == 0 &&
                Double.compare(mat4x4.m13, m13) == 0 &&
                Double.compare(mat4x4.m14, m14) == 0 &&
                Double.compare(mat4x4.m21, m21) == 0 &&
                Double.compare(mat4x4.m22, m22) == 0 &&
                Double.compare(mat4x4.m23, m23) == 0 &&
                Double.compare(mat4x4.m24, m24) == 0 &&
                Double.compare(mat4x4.m31, m31) == 0 &&
                Double.compare(mat4x4.m32, m32) == 0 &&
                Double.compare(mat4x4.m33, m33) == 0 &&
                Double.compare(mat4x4.m34, m34) == 0 &&
                Double.compare(mat4x4.m41, m41) == 0 &&
                Double.compare(mat4x4.m42, m42) == 0 &&
                Double.compare(mat4x4.m43, m43) == 0 &&
                Double.compare(mat4x4.m44, m44) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44);
    }

    @Override
    public String toString() {
        return "Mat4x4{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m14=" + m14 +
                ", m21=" + m21 +
                ", m22=" + m22 +
                ", m23=" + m23 +
                ", m24=" + m24 +
                ", m31=" + m31 +
                ", m32=" + m32 +
                ", m33=" + m33 +
                ", m34=" + m34 +
                ", m41=" + m41 +
                ", m42=" + m42 +
                ", m43=" + m43 +
                ", m44=" + m44 +
                '}';
    }
}
