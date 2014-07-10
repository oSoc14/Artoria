package be.artoria.belfortapp.mixare;

import android.content.Context;
import android.content.Intent;

import be.artoria.belfortapp.activities.MonumentDetailActivity;
import be.artoria.belfortapp.app.POI;

/**
 * Created by Dieter Beelaert on 10/07/2014.
 */
public class ArtoriaNavigationMarker extends NavigationMarker {
    public POI poi;

    public ArtoriaNavigationMarker(POI poi, int taskId, int colour){
        super(poi.id + "", poi.getName(), Double.parseDouble(poi.lat), Double.parseDouble(poi.lon),3, "", taskId,  colour);
        this.poi = poi;
    }

    public boolean onClick(float x, float y, Context ctx){
        if(super.isClickValid(x,y)){
            Intent i = MonumentDetailActivity.newIntent(ctx, poi.id, true);
            ctx.startActivity(i);
            return true;
        }
        return false;
    }
}
