package bitmovers.elementaldimensions.blocks.altar;

import net.minecraft.util.math.Vec3d;

public class Particle {
    private Vec3d d;
    private double scale;
    private int alpha;
    private int age;

    public Particle(Vec3d d, double scale, int alpha, int age) {
        this.d = d;
        this.scale = scale;
        this.alpha = alpha;
        this.age = age;
    }

    public Vec3d getD() {
        return d;
    }

    public void setD(Vec3d d) {
        this.d = d;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void older() {
        age--;
    }
}
