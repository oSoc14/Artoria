package be.artoria.belfortapp.app;

import android.app.Application;

import be.artoria.belfortapp.sql.POIDAO;

/**
 * Created by Laurens on 03/07/2014.
 */
public class ArtoriaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.initialize(this);
    }

}
