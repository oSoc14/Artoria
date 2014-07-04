package be.artoria.belfortapp.app;

import android.graphics.drawable.Drawable;

/**
 * Created by Dieter Beelaert on 4/07/2014.
 */
public class DescriptionRow {
    public Drawable imageDescription;
    public String description;

    public DescriptionRow(Drawable imageDescription, String description){
        this.imageDescription = imageDescription;
        this.description = description;
    }
}
