package be.artoria.belfortapp.app;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import be.artoria.belfortapp.R;

/**
 * Created by dietn on 06/11/14.
 */
public class MuseumImageMapper {

    public static Drawable getDrawableForId(int id){
        Resources resources = PrefUtils.getContext().getResources();
        Drawable toReturn = null;
        switch(id){
            case 1: toReturn = resources.getDrawable(R.drawable.belfort);
                break;
            case 2: toReturn = resources.getDrawable(R.drawable.lakenhalle);
                break;
            case 3: toReturn = resources.getDrawable(R.drawable.mammelokker);
                break;
            case 4: toReturn = resources.getDrawable(R.drawable.unesco);
                break;
            case 5: toReturn = resources.getDrawable(R.drawable.secreet);
                    break;
            case 6: toReturn = resources.getDrawable(R.drawable.koffers);
                break;
            case 7: toReturn = resources.getDrawable(R.drawable.torenspits);
                break;
            case 8: toReturn = resources.getDrawable(R.drawable.draak);
                break;
            case 9: toReturn = resources.getDrawable(R.drawable.klokken);
                break;
            case 10: toReturn = resources.getDrawable(R.drawable.phemony);
                break;
            case 11: toReturn = resources.getDrawable(R.drawable.klokkenkamer);
                break;
            case 12: toReturn = resources.getDrawable(R.drawable.uurwerk);
                break;
            case 13: toReturn = resources.getDrawable(R.drawable.trommel);
                break;
            case 14: toReturn = resources.getDrawable(R.drawable.beiaard_bespelen);
                break;
        }
        return toReturn;
    }
}
