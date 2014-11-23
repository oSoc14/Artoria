package be.artoria.belfortapp.app;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by Laurens on 23/11/2014.
 */
public class FontManager {
    public static Typeface uniSansThin;
    public static Typeface uniSansHeavy;
    public static Typeface athelas;

    private static final FontManager INSTANCE = new FontManager();

    private FontManager(){
        final Context ctx = PrefUtils.getContext();
        final  AssetManager assets = ctx.getAssets();
        athelas     = Typeface.createFromAsset(assets, "fonts/Athelas.ttc");
        uniSansThin = Typeface.createFromAsset(assets, "fonts/Uni Sans Thin.ttf");
        uniSansHeavy = Typeface.createFromAsset(assets, "fonts/Uni Sans Heavy.ttf");
    }

}
