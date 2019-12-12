import java.util.Objects;

public class PVector {

    private int x;
    private int y;
    private int z;

    public PVector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public  PVector(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public PVector add(PVector other){
        x += other.getX();
        y += other.getY();
        z += other.getZ();
        return this;
    }

    public PVector rotate(double degrees){
        double theta = Math.toRadians(degrees);
        double cs = Math.cos(theta);
        double sn = Math.sin(theta);

        double xCopy = x;
        double yCopy = y;

        x = (int)(xCopy * cs - yCopy * sn);
        y = (int)(xCopy * sn + yCopy * cs);

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PVector pVector = (PVector) o;
        return x == pVector.x && (y == pVector.y && z == pVector.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
