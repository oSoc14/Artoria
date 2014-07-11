package be.artoria.belfortapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Laurens on 01/07/2014.
 */
public class POIDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + POIContract.POIEntry.TABLE_NAME + " (" +
                    POIContract.POIEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_NAME_EN + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_NAME_FR + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_LAT + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_LON + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_HEIGHT + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_DESCRIPTION_EN + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_DESCRIPTION_FR + TEXT_TYPE + COMMA_SEP +
                    POIContract.POIEntry.COLUMN_NAME_IMAGE_URL + TEXT_TYPE +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + POIContract.POIEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Monument.db";

    public POIDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
