package engine.core;

public class MathExtensions {
    /**
     * Linear interpolates between A and B by time T.
     *
     * @param a the start
     * @param b the end
     * @param t the "time" between the start and end
     * @return (1-t)*a + t*b
     */
    public static double lerp(double a, double b, double t) {
        return ((1.0 - t) * a) + (t * b);
    }
}
