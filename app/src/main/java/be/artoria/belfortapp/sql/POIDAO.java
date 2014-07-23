package be.artoria.belfortapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.artoria.belfortapp.sql.POIContract.POIEntry;
import be.artoria.belfortapp.app.POI;

/**
 * Created by Laurens on 03/07/2014.
 */
public class POIDAO {

    // Database fields
    private SQLiteDatabase database;
    private final POIDbHelper dbHelper;
    private static final String[] allColumns = {
            POIEntry.COLUMN_NAME_ENTRY_ID ,
            POIEntry.COLUMN_NAME_NAME ,
            POIEntry.COLUMN_NAME_NAME_EN ,
            POIEntry.COLUMN_NAME_NAME_FR ,
            POIEntry.COLUMN_NAME_LAT ,
            POIEntry.COLUMN_NAME_LON ,
            POIEntry.COLUMN_NAME_HEIGHT ,
            POIEntry.COLUMN_NAME_DESCRIPTION ,
            POIEntry.COLUMN_NAME_DESCRIPTION_EN ,
            POIEntry.COLUMN_NAME_DESCRIPTION_FR ,
            POIEntry.COLUMN_NAME_IMAGE_URL,
            POIEntry.COLUMN_NAME_TYPE
    };

    public POIDAO(Context context) {
        dbHelper = new POIDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public POI savePOI(POI poi) {
        final ContentValues values = new ContentValues();

        values.put(POIEntry.COLUMN_NAME_ENTRY_ID, poi.id);
        values.put(POIEntry.COLUMN_NAME_NAME, poi.NL_name);
        values.put(POIEntry.COLUMN_NAME_NAME_EN, poi.ENG_name);
        values.put(POIEntry.COLUMN_NAME_NAME_FR, poi.FR_name);
        values.put(POIEntry.COLUMN_NAME_LAT, poi.lat);
        values.put(POIEntry.COLUMN_NAME_LON, poi.lon);
        values.put(POIEntry.COLUMN_NAME_HEIGHT, poi.height);
        values.put(POIEntry.COLUMN_NAME_DESCRIPTION, poi.NL_description);
        values.put(POIEntry.COLUMN_NAME_DESCRIPTION_EN, poi.ENG_description);
        values.put(POIEntry.COLUMN_NAME_DESCRIPTION_FR, poi.FR_description);
        values.put(POIEntry.COLUMN_NAME_IMAGE_URL, poi.image_link);
        values.put(POIEntry.COLUMN_NAME_TYPE,poi.type);

        database.insert(POIEntry.TABLE_NAME, null,
                values);

        return poi;
    }

    public void clearTable(){
        database.execSQL(POIDbHelper.SQL_DELETE_ENTRIES);
        database.execSQL(POIDbHelper.SQL_CREATE_ENTRIES);
    }

    public List<POI> getAllPOIs() {
        final List<POI> pois = new ArrayList<POI>();

        final Cursor cursor = database.query(POIEntry.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final POI poi = cursorToPOI(cursor);
            pois.add(poi);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return pois;
    }

    private POI cursorToPOI(Cursor cursor) {
        if(cursor == null ) return null;
        POI poi = new POI();
        poi.id              = cursor.getInt(cursor.getColumnIndex(POIEntry.COLUMN_NAME_ENTRY_ID));
        poi.NL_name         = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_NAME));
        poi.ENG_name        = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_NAME_EN));
        poi.FR_name         = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_NAME_FR));
        poi.lat             = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_LAT));
        poi.lon             = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_LON));
        poi.height          = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_HEIGHT));
        poi.NL_description  = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_DESCRIPTION));
        poi.ENG_description = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_DESCRIPTION_EN));
        poi.FR_description  = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_DESCRIPTION_FR));
        poi.image_link      = cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_IMAGE_URL));
        poi.type            = Integer.parseInt(cursor.getString(cursor.getColumnIndex(POIEntry.COLUMN_NAME_TYPE)));
        return poi;
    }
}
