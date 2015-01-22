package be.artoria.belfortapp.extension;

/**
 * Created by Laurens on 21/01/2015.
 * This class represents a circle on the panorama picture
 */
public class CircledPOI{
    // Used for responding to onclick events.
    public final int poi;
    // In percentage of screen, maybe? don't know yet.
    public final float radius;
    public final int x;
    public final int y;

    public CircledPOI(int poi, float radius, int x, int y) {
        this.poi = poi;
        this.radius = radius;
        this.x = x;
        this.y = y;

    }
}
