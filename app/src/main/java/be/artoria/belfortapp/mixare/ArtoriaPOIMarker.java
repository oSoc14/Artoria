package be.artoria.belfortapp.mixare;

import android.content.Context;
import android.content.Intent;

import be.artoria.belfortapp.activities.MonumentDetailActivity;
import be.artoria.belfortapp.app.POI;
import be.artoria.belfortapp.mixare.lib.MixContextInterface;
import be.artoria.belfortapp.mixare.lib.MixStateInterface;

/**
 * Created by Dieter Beelaert on 8/07/2014.
 */
public class ArtoriaPOIMarker extends POIMarker {
    public POI poi;


    public ArtoriaPOIMarker(POI poi, int type, int color){
        super( poi.id+"", poi.getName(), Double.parseDouble(poi.lat), Double.parseDouble(poi.lon),3, "", type,  color);
        this.poi = poi;
    }

    public boolean onClick(float x, float y, Context ctx){
      if(super.isClickValid(x,y)){
          Intent i = MonumentDetailActivity.newIntent(ctx,poi.id);
          ctx.startActivity(i);
          return true;
      }
      return false;
    }
}
