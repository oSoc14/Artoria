package be.artoria.belfortapp.app;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

/**
 * Created by Dieter Beelaert on 7/07/2014.
 */
public class ArtoriaOverlayItem extends OverlayItem {
    public POI poi;

    public ArtoriaOverlayItem(POI poi) {
        super(poi.getName(),poi.getDescription(), new GeoPoint(Double.parseDouble(poi.lat),Double.parseDouble(poi.lon)));
        this.poi = poi;
    }
}
