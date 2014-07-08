package be.artoria.belfortapp.mixare;

import be.artoria.belfortapp.app.POI;

/**
 * Created by Dieter Beelaert on 8/07/2014.
 */
public class ArtoriaPOIMarker extends POIMarker {
    public POI poi;


    public ArtoriaPOIMarker(POI poi, int type, int color){
        super( poi.id+"", poi.getName(), Double.parseDouble(poi.lat), Double.parseDouble(poi.lon),3, "", type,  color);
        this.poi = poi;
    }
}
